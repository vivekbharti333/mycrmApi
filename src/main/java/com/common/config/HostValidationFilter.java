package com.common.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
//public class HostValidationFilter {
public class HostValidationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String host = req.getHeader("Host");

        if (host != null) {
            host = host.split(":")[0]; // remove port if present
        }

        boolean allowed =
                host != null &&
                (
            		host.equalsIgnoreCase("localhost") ||
                    host.equalsIgnoreCase("datfuslab.in") ||
                    host.equalsIgnoreCase("www.datfuslab.in") ||
                    host.equalsIgnoreCase("donexia.in") ||
                    host.equalsIgnoreCase("www.donexia.in") ||
                    host.equalsIgnoreCase("mydonation.in") ||
                    host.equalsIgnoreCase("www.mydonation.in") ||
                    host.endsWith(".donexia.in")
                );

        if (!allowed) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid Host");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // No cleanup requiredac
    }
}
