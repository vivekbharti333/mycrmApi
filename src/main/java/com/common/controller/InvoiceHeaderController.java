package com.common.controller;

import com.common.constant.Constant;
import com.common.entities.InvoiceHeaderDetails;
import com.common.enums.Status;
import com.common.exceptions.BizException;
import com.common.helper.InvoiceHeaderHelper;
import com.ngo.entities.DonationDetails;
import com.ngo.helper.DonationHelper;
import com.ngo.object.request.InvoiceHeaderRequestObject;
import com.spring.common.PdfInvoice;
import com.common.object.request.Request;
import com.common.object.response.GenricResponse;
import com.common.object.response.Response;
import com.common.services.InvoiceHeaderService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin(
   origins = {"*"}
)
@RestController
public class InvoiceHeaderController {
	
   @Autowired
   private InvoiceHeaderService invoiceHeaderService;
   
   @Autowired
   private PdfInvoice pdfInvoice;
   
   @Autowired
   private DonationHelper donationHelper;
   
   @Autowired
   private InvoiceHeaderHelper invoiceHelper;

   @RequestMapping(path = {"addInvoiceHeader"},method = {RequestMethod.POST})
   public Response<InvoiceHeaderRequestObject> addInvoiceHeader(@RequestBody Request<InvoiceHeaderRequestObject> invoiceRequestObject, HttpServletRequest request) {
      GenricResponse<InvoiceHeaderRequestObject> responseObj = new GenricResponse<InvoiceHeaderRequestObject>();

      try {
         InvoiceHeaderRequestObject responce = invoiceHeaderService.addInvoiceHeader(invoiceRequestObject);
         return responseObj.createSuccessResponse(responce, 200);
      } catch (BizException var5) {
         return responseObj.createErrorResponse(400, var5.getMessage());
      } catch (Exception var6) {
         var6.printStackTrace();
         return responseObj.createErrorResponse(500, var6.getMessage());
      }
   }

   @RequestMapping(
      path = {"getInvoiceHeaderList"},
      method = {RequestMethod.POST}
   )
   public Response<InvoiceHeaderDetails> getInvoiceHeaderList(@RequestBody Request<InvoiceHeaderRequestObject> invoiceRequestObject) {
      GenricResponse<InvoiceHeaderDetails> response = new GenricResponse<InvoiceHeaderDetails>();

      try {
         List<InvoiceHeaderDetails> invoiceDetails = invoiceHeaderService.getInvoiceHeaderList(invoiceRequestObject);
         return response.createListResponse(invoiceDetails, 200, String.valueOf(invoiceDetails.size()));
      } catch (Exception var4) {
         var4.printStackTrace();
         return response.createErrorResponse(500, var4.getMessage());
      }
   }

   
   @RequestMapping(
      value = {"/donationinvoice/{reffNo}"},
      method = {RequestMethod.GET}
   )
   public Object donationinvoice(@PathVariable String reffNo) throws IOException {
      DonationDetails donationDetails = this.donationHelper.getDonationDetailsByReferenceNo(reffNo);
      ModelAndView modelAndView;
      if (donationDetails != null) {
         if (!donationDetails.getStatus().equalsIgnoreCase(Status.INACTIVE.name())) {
            InvoiceHeaderDetails invoiceHeader = this.invoiceHelper.getInvoiceHeaderById(donationDetails.getInvoiceHeaderDetailsId());
            ByteArrayOutputStream pdfStream = this.pdfInvoice.generatePdfInvoice(donationDetails, invoiceHeader);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.setContentLength((long)pdfStream.size());
            String fileName = invoiceHeader.getCompanyFirstName() + "-invoice.pdf";
            headers.setContentDispositionFormData("attachment", fileName);
            InputStreamResource isr = new InputStreamResource(new ByteArrayInputStream(pdfStream.toByteArray()));
//            return new ResponseEntity(isr, headers, HttpStatus.OK);
            return new ResponseEntity<InputStreamResource>(isr, headers, HttpStatus.OK);
         } else {
            modelAndView = new ModelAndView("message");
            modelAndView.addObject("message", "Cancelled request. Please contact admin for details.");
            modelAndView.setViewName("message");
            return modelAndView;
         }
      } else {
         modelAndView = new ModelAndView("message");
         modelAndView.addObject("message", "Invalid request. Please contact admin for details.");
         modelAndView.setViewName("message");
         return modelAndView;
      }
   }

   @RequestMapping({"/getreceipt"})
   public ResponseEntity<?> getPdf(@RequestParam String fileName) {
      try {
         Path filePath = Paths.get("/opt/apache-tomcat-9.0/webapps/DonationDocument/Receipt/", fileName);
         File file = filePath.toFile();
         if (file.exists() && file.isFile() && file.getName().endsWith(".pdf")) {
            byte[] pdfBytes = Files.readAllBytes(filePath);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
            headers.add("Content-Type", "application/pdf");
            return ((BodyBuilder)ResponseEntity.ok().headers(headers)).body(pdfBytes);
         } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found or not a valid PDF.");
         }
      } catch (Exception var6) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving PDF: " + var6.getMessage());
      }
   }

   @RequestMapping({"/getPdfreceipt/{fileName:.+}"})
   public ResponseEntity<?> getPdfreceipt(@PathVariable String fileName) {
      try {
//         Path filePath = Paths.get("/opt/apache-tomcat-9.0/webapps/DonationDocument/Receipt/", fileName);
         Path filePath = Paths.get(Constant.baseDocLocation+Constant.receipt, fileName);
         File file = filePath.toFile();
         if (file.exists() && file.isFile() && file.getName().endsWith(".pdf")) {
            byte[] pdfBytes = Files.readAllBytes(filePath);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
            headers.add("Content-Type", "application/pdf");
            return ((BodyBuilder)ResponseEntity.ok().headers(headers)).body(pdfBytes);
         } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found or not a valid PDF.");
         }
      } catch (Exception var6) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving PDF: " + var6.getMessage());
      }
   }
}
    