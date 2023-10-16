package com.spring.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.*;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.spring.constant.Constant;
import com.spring.entities.DonationDetails;
import com.spring.entities.InvoiceHeaderDetails;
import com.spring.helper.DonationHelper;
import com.spring.helper.InvoiceHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletResponse;

@Component
public class FilePath {
	
	public String getPathToUploadFile(String Type) { // Use
		String pathtoUploads;
		if (Type.equalsIgnoreCase(Constant.invoiceImage)) {
			pathtoUploads = Constant.docLocation + Constant.invoiceImage;
		} else if (Type.equalsIgnoreCase(Constant.receipt)) {
			pathtoUploads = Constant.docLocation + Constant.receipt;
		} else {
			pathtoUploads = Constant.docLocation + Constant.defaultPath;
		}

		if (!new File(pathtoUploads).exists()) {
			File dir = new File(pathtoUploads);
			dir.mkdirs();
		}
		Path path = Paths.get(pathtoUploads);
		return path.toString();
	}
}
