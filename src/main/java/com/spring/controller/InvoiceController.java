package com.spring.controller;

import com.spring.common.PdfInvoice;
import com.spring.constant.Constant;
import com.spring.entities.DonationDetails;
import com.spring.entities.InvoiceDetails;
import com.spring.entities.InvoiceHeaderDetails;
import com.spring.entities.InvoiceNumber;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.helper.DonationHelper;
import com.spring.helper.InvoiceHelper;
import com.spring.object.request.InvoiceRequestObject;
import com.spring.object.request.Request;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.services.InvoiceService;
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
public class InvoiceController {
   @Autowired
   private InvoiceService invoiceService;
   @Autowired
   private PdfInvoice pdfInvoice;
   @Autowired
   private DonationHelper donationHelper;
   @Autowired
   private InvoiceHelper invoiceHelper;

   @RequestMapping(
      path = {"addInvoiceHeader"},
      method = {RequestMethod.POST}
   )
   public Response<InvoiceRequestObject> addInvoiceHeader(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject, HttpServletRequest request) {
      GenricResponse responseObj = new GenricResponse();

      try {
         InvoiceRequestObject responce = this.invoiceService.addInvoiceHeader(invoiceRequestObject);
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
   public Response<InvoiceHeaderDetails> getInvoiceHeaderList(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject) {
      GenricResponse response = new GenricResponse();

      try {
         List<InvoiceHeaderDetails> invoiceDetails = this.invoiceService.getInvoiceHeaderList(invoiceRequestObject);
         return response.createListResponse(invoiceDetails, 200, String.valueOf(invoiceDetails.size()));
      } catch (Exception var4) {
         var4.printStackTrace();
         return response.createErrorResponse(500, var4.getMessage());
      }
   }

   @RequestMapping(
      path = {"generateInvoice"},
      method = {RequestMethod.POST}
   )
   public Response<InvoiceRequestObject> addCategory(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject, HttpServletRequest request) {
      GenricResponse responseObj = new GenricResponse();

      try {
         InvoiceRequestObject responce = this.invoiceService.generateInvoice1(invoiceRequestObject);
         return responseObj.createSuccessResponse(responce, 200);
      } catch (BizException var5) {
         return responseObj.createErrorResponse(400, var5.getMessage());
      } catch (Exception var6) {
         var6.printStackTrace();
         return responseObj.createErrorResponse(500, var6.getMessage());
      }
   }

   @RequestMapping(
      path = {"getInvoiceNumberList"},
      method = {RequestMethod.POST}
   )
   public Response<InvoiceNumber> getInvoiceNumber(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject) {
      GenricResponse response = new GenricResponse();

      try {
         List<InvoiceNumber> invoiceNumber = this.invoiceService.getInvoiceNumberList(invoiceRequestObject);
         return response.createListResponse(invoiceNumber, 200, String.valueOf(invoiceNumber.size()));
      } catch (Exception var4) {
         var4.printStackTrace();
         return response.createErrorResponse(500, var4.getMessage());
      }
   }

   @RequestMapping(
      path = {"getInvoiceDetailsList"},
      method = {RequestMethod.POST}
   )
   public Response<InvoiceDetails> getInvoiceDetailsList(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject) {
      GenricResponse response = new GenricResponse();

      try {
         List<InvoiceDetails> invoiceDetails = this.invoiceService.getInvoiceDetailsList(invoiceRequestObject);
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
            return new ResponseEntity(isr, headers, HttpStatus.OK);
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
    