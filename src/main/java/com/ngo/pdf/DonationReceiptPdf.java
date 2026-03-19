package com.ngo.pdf;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import com.ngo.helper.DonationHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.common.entities.InvoiceHeaderDetails;
import com.common.utility.AmountToWordsConverter;
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import com.ngo.entities.DonationDetails;

@Component
public class DonationReceiptPdf {
	
	@Autowired
	private AmountToWordsConverter amountToWordsConverter;

    private final DonationHelper donationHelper;

    DonationReceiptPdf(DonationHelper donationHelper) {
        this.donationHelper = donationHelper;
    }

    public byte[] generateInvoice(DonationDetails donationDetails, InvoiceHeaderDetails invoiceHeader) throws Exception {
    	
    	String pattern = "dd/MM/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(donationDetails.getCreatedAt());
    	
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
        Document document = new Document(pdf);
        document.setMargins(30, 30, 30, 30);

        // ================= LOAD FONTS =================
        PdfFont regular;
        PdfFont bold;
        PdfFont italic;

        try (
                InputStream r = getClass().getClassLoader()
                        .getResourceAsStream("fonts/google/NotoSans-Regular.ttf");
                InputStream b = getClass().getClassLoader()
                        .getResourceAsStream("fonts/google/NotoSans-Bold.ttf");
        		InputStream c = getClass().getClassLoader()
                        .getResourceAsStream("fonts/google/NotoSans-Italic.ttf")
                       
        ) {
            regular = PdfFontFactory.createFont(r.readAllBytes(), PdfEncodings.IDENTITY_H, true);
            bold = PdfFontFactory.createFont(b.readAllBytes(), PdfEncodings.IDENTITY_H, true);
            italic = PdfFontFactory.createFont(c.readAllBytes(), PdfEncodings.IDENTITY_H, true);
        }

        // ================= OUTER BORDER ONLY =================
        Table outerTable = new Table(1).useAllAvailableWidth();
        Cell outerCell = new Cell()
                .setBorder(new SolidBorder(ColorConstants.BLACK, 1))
                .setPadding(0);

        // =====================================================
        // ================= HEADER (WHITE) ====================
        // =====================================================
        Cell headerCell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setBackgroundColor(ColorConstants.WHITE)
                .setPadding(15);

        // Logo
        Image logo = new Image(ImageDataFactory.create(
                "C:\\Users\\HP\\Downloads\\logo.png"))
                .setWidth(90).setHeight(80);

        Table headerTable = new Table(UnitValue.createPercentArray(new float[]{2, 5, 2}))
                .useAllAvailableWidth();

        // Logo Cell
        headerTable.addCell(new Cell().add(logo)
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE));

        // ================= HEADER CONTENT =================
        Cell centerCell = new Cell().setBorder(Border.NO_BORDER);

        // NGO Name
        centerCell.add(
            new Paragraph()
                .add(new Text(invoiceHeader.getCompanyFirstName() + " ")
                        .setFont(bold).setFontSize(16))
                .add(new Text(invoiceHeader.getCompanyLastName())
                        .setFont(bold).setFontSize(16))
                .setMarginTop(0)
                .setMarginBottom(4)
                .setFixedLeading(12)
                .setTextAlignment(TextAlignment.CENTER)
        );

        // Registration No (GST)
        String gst = invoiceHeader.getGstNumber();
        if (gst != null && !gst.isBlank() && !"null".equalsIgnoreCase(gst)) {
            centerCell.add(
                new Paragraph("Registration No.: " + gst)
                    .setFont(regular).setFontSize(9)
                    .setFontColor(ColorConstants.DARK_GRAY)
                    .setMargin(0)
                    .setFixedLeading(11.8f)
                    .setTextAlignment(TextAlignment.CENTER)
            );
        }

        // PAN
        String pan = invoiceHeader.getPanNumber();
        if (pan != null && !pan.isBlank() && !"null".equalsIgnoreCase(pan)) {
            centerCell.add(
                new Paragraph("PAN No.: " + pan)
                    .setFont(regular).setFontSize(9)
                    .setFontColor(ColorConstants.DARK_GRAY)
                    .setMargin(0)
                    .setFixedLeading(11.8f)
                    .setTextAlignment(TextAlignment.CENTER)
            );
        }

        // Office Address
        String office = invoiceHeader.getOfficeAddress();
        if (office != null && !office.isBlank() && !"null".equalsIgnoreCase(office)) {
            centerCell.add(
                new Paragraph("Off. Add: " + office)
                    .setFont(regular).setFontSize(10)
                    .setFontColor(ColorConstants.DARK_GRAY)
                    .setMargin(0)
                    .setFixedLeading(11.8f)
                    .setTextAlignment(TextAlignment.CENTER)
            );
        }

        // Registered Address
        String reg = invoiceHeader.getRegAddress();
        if (reg != null && !reg.isBlank() && !"null".equalsIgnoreCase(reg)) {
            centerCell.add(
                new Paragraph("Reg. Add: " + reg)
                    .setFont(regular).setFontSize(9)
                    .setFontColor(ColorConstants.DARK_GRAY)
                    .setMargin(0)
                    .setFixedLeading(11.8f)
                    .setTextAlignment(TextAlignment.CENTER)
            );
        }

        // Mobile
        String mobile = invoiceHeader.getMobileNo();
        String altMobile = invoiceHeader.getAlternateMobile();

        String mobileText = "";
        if (mobile != null && !mobile.isBlank()) {
            mobileText += mobile;
        }
        if (altMobile != null && !altMobile.isBlank()) {
            if (!mobileText.isEmpty()) mobileText += ", ";
            mobileText += altMobile;
        }

        if (!mobileText.isEmpty()) {
            centerCell.add(
                new Paragraph("Mobile No : " + mobileText)
                    .setFont(regular).setFontSize(9)
                    .setFontColor(ColorConstants.DARK_GRAY)
                    .setMargin(0)
                    .setFixedLeading(11.8f)
                    .setTextAlignment(TextAlignment.CENTER)
            );
        }

        // Email + Website
        String email = invoiceHeader.getEmailId();
        String website = invoiceHeader.getWebsite();

        String contact = "";
        if (email != null && !email.isBlank()) {
            contact += email;
        }
        if (website != null && !website.isBlank()) {
            if (!contact.isEmpty()) contact += " | ";
            contact += website;
        }

        if (!contact.isEmpty()) {
            centerCell.add(
                new Paragraph(contact)
                    .setFont(regular).setFontSize(9)
                    .setFontColor(ColorConstants.DARK_GRAY)
                    .setMargin(0)
                    .setFixedLeading(11.8f)
                    .setTextAlignment(TextAlignment.CENTER)
            );
        }

        centerCell.add(new Paragraph("\n"));
        headerTable.addCell(centerCell);

        // ================= RIGHT SIDE =================
        headerTable.addCell(
            new Cell()
                .add(new Paragraph("RECEIPT")
                        .setFont(bold).setFontSize(12))
                .add(new Paragraph(donationDetails.getInvoiceNumber())
                        .setFont(regular).setFontSize(9))
                .add(new Paragraph()
                        .add(new Text("Date: ").setFont(regular).setFontSize(10))
                        .add(new Text(date).setFont(regular).setFontSize(10)))
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER)
        );

        // ================= FINAL =================
        headerCell.add(headerTable);
        headerCell.add(new LineSeparator(new SolidLine(1)));

        outerCell.add(headerCell);
        
        
        // =====================================================
        // ================= BODY (LIGHT PINK) =================
        // =====================================================
        Cell bodyCell = new Cell()
                .setBorder(Border.NO_BORDER)
//                .setBackgroundColor(new DeviceRgb(255, 240, 246))
                .setPadding(15);     

        // Header line
        bodyCell.add(new Paragraph()
                .add(new Text(invoiceHeader.getCompanyFirstName() + " " + invoiceHeader.getCompanyLastName())
                        .setFont(bold).setFontSize(9))
                .add(new Text(" gratefully acknowledges the generous contribution received from: ")
                        .setFont(regular).setFontSize(9))
        );

        // Donor Name
        bodyCell.add(new Paragraph("Mr./Miss " + safeValue(donationDetails.getDonorName()))
                .setFont(regular).setFontSize(9));

        // Address
        bodyCell.add(new Paragraph("Address.: " + safeValue(donationDetails.getAddress()))
                .setFont(regular).setFontSize(9));

        // Contact No
        bodyCell.add(new Paragraph("Contact No.: " + safeValue(donationDetails.getMobileNumber()))
                .setFont(regular).setFontSize(9));

        // Email
        bodyCell.add(new Paragraph("Email Id.: " + safeValue(donationDetails.getEmailId()))
                .setFont(regular).setFontSize(9));

        // PAN
        bodyCell.add(new Paragraph("PAN.: " + safeValue(donationDetails.getPanNumber()))
                .setFont(regular).setFontSize(9));

        // Amount line
        bodyCell.add(new Paragraph()
                .add(new Text("We sincerely thank you for your kind donation of ")
                        .setFont(regular).setFontSize(9))
                .add(new Text("₹" + donationDetails.getAmount())
                        .setFont(bold).setFontSize(9))
                .add(new Text("/- (" +
                        amountToWordsConverter.amountInWords(donationDetails.getAmount().longValue()) +
                        " Rupees Only/-).")
                        .setFont(regular).setFontSize(9))
        );
        bodyCell.add(new Paragraph(
                "Your generous support will help us continue our efforts towards social welfare and community development."
               + "We truly appreciate your commitment to making a positive difference in the lives of those in need.")
                .setFont(regular).setFontSize(9));

        bodyCell.add(new Paragraph("\n\n"));
        

       
        BarcodeQRCode qr = new BarcodeQRCode("https://datfuslab.com/verify-receipt/coming-soon");
        Image qrImg = new Image(qr.createFormXObject(ColorConstants.BLACK, pdf))
                .setWidth(80).setHeight(80)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT);
        bodyCell.add(qrImg);
        
        bodyCell.add(new Paragraph(
                "This is a system-generated receipt and does not require a physical signature.")
                .setFont(regular).setFontSize(7)
                .setFontColor(ColorConstants.DARK_GRAY)
                .setTextAlignment(TextAlignment.RIGHT));

      bodyCell.add(new Paragraph("\n"));

        bodyCell.add(new LineSeparator(new SolidLine(1)));
          bodyCell.add(new Paragraph("Acknowledgment")
                .setFont(bold).setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER));
        
//        bodyCell.add(
//    	        new Paragraph(donationDetails.getNotes())
//    	                .setFont(regular)
//    	                .setFontColor(ColorConstants.DARK_GRAY)
//    	                .setFontSize(8)
//    	                .setMarginTop(0)
//    	                .setMarginBottom(0)
//    	                .setMultipliedLeading(1.1f)   // tight line spacing
//    	);
          
       // remove HTML tags
       // replace strong with markers
          String notes = invoiceHeader.getThankYouNote().replace("<strong>", "##BOLD_START##")
                       .replace("</strong>", "##BOLD_END##");

          // remove other HTML
          notes = notes.replaceAll("<[^>]+>", "");

          Paragraph paragraph = new Paragraph().setFontSize(9);

          String[] parts = notes.split("##BOLD_START##");

          for (String part : parts) {
              if (part.contains("##BOLD_END##")) {
                  String[] boldParts = part.split("##BOLD_END##");

                  paragraph.add(new Text(boldParts[0]).setBold());
                  if (boldParts.length > 1) {
                      paragraph.add(new Text(boldParts[1]));
                  }
              } else {
                  paragraph.add(new Text(part));
              }
          }

          bodyCell.add(paragraph.setFont(regular).setFontColor(ColorConstants.DARK_GRAY));
          
        
        bodyCell.add(new Paragraph("\n"));
        bodyCell.add(new LineSeparator(new SolidLine(1)));
        bodyCell.add(new Paragraph("\n"))
        
        		.add(new Paragraph(invoiceHeader.getFooter())
	                      .setFont(bold).setTextAlignment(TextAlignment.CENTER)
	                      .setFontSize(10)
	                      .setMarginBottom(0)
	                      .setFixedLeading(11)
	      );
        
        outerCell.add(bodyCell);

        // ================= ADD TO DOCUMENT =================
        outerTable.addCell(outerCell);
        document.add(outerTable);

        document.add(new Paragraph("Powered by Datfuslab Technologies Pvt. Ltd.")
                .setFont(italic).setFontSize(8)
                .setFontColor(ColorConstants.GRAY));

        document.close();
        return baos.toByteArray();
    }
    
    
    private String safeValue(String value) {
        if (value == null || value.isBlank() || "null".equalsIgnoreCase(value)) {
            return "XXXX";
        }
        return value;
    }
}












































//package com.ngo.pdf;
//
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//
//import org.springframework.stereotype.Component;
//
//import com.itextpdf.barcodes.BarcodeQRCode;
//import com.itextpdf.io.font.PdfEncodings;
//import com.itextpdf.io.image.ImageData;
//import com.itextpdf.io.image.ImageDataFactory;
//import com.itextpdf.kernel.colors.ColorConstants;
//import com.itextpdf.kernel.colors.DeviceRgb;
//import com.itextpdf.kernel.font.PdfFont;
//import com.itextpdf.kernel.font.PdfFontFactory;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.borders.Border;
//import com.itextpdf.layout.borders.SolidBorder;
//import com.itextpdf.layout.element.Cell;
//import com.itextpdf.layout.element.Image;
//import com.itextpdf.layout.element.LineSeparator;
//import com.itextpdf.layout.element.Paragraph;
//import com.itextpdf.layout.element.Table;
//import com.itextpdf.layout.element.Text;
//import com.itextpdf.layout.property.TextAlignment;
//import com.itextpdf.layout.property.UnitValue;
//import com.itextpdf.layout.property.VerticalAlignment;
//
//@Component
//public class DonationReceiptPdf {
//
//    public byte[] generateInvoice() throws Exception {
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//        PdfWriter writer = new PdfWriter(baos);
//        PdfDocument pdf = new PdfDocument(writer);
//        Document document = new Document(pdf);
//
//        document.setMargins(30, 30, 30, 30);
//
//        // ================= LOAD ROBOTO FONTS =================
//        PdfFont googleRegular;
//        PdfFont googleBold;
//
//        try (
//        		InputStream regularStream = getClass().getClassLoader().getResourceAsStream("fonts/google/NotoSans-Regular.ttf");
//        		InputStream boldStream = getClass().getClassLoader().getResourceAsStream("fonts/google/NotoSans-Bold.ttf");
//        ) {
//            if (regularStream == null || boldStream == null) {
//                throw new RuntimeException("google font files not found in classpath");
//            }
//
//            googleRegular = PdfFontFactory.createFont(
//                    regularStream.readAllBytes(),
//                    PdfEncodings.IDENTITY_H,
//                    true
//            );
//
//            googleBold = PdfFontFactory.createFont(
//                    boldStream.readAllBytes(),
//                    PdfEncodings.IDENTITY_H,
//                    true
//            );
//        }
//
//        // ================= OUTER BORDER =================
//        Table outerTable = new Table(1).useAllAvailableWidth();
//
//        Cell outerCell = new Cell()
//                .setBorder(new SolidBorder(ColorConstants.BLACK, 1))
//                .setBackgroundColor(new DeviceRgb(255, 240, 246)) // 🌸 light pink
//                .setPadding(15);
//
//        // ================= HEADER =================
//        String logoPath = "C:\\Users\\HP\\Downloads\\download.png";
//        ImageData imageData = ImageDataFactory.create(logoPath);
//        Image logo = new Image(imageData).setWidth(90).setHeight(80);
//
//        Table headerTable = new Table(UnitValue.createPercentArray(new float[]{2, 5, 3}))
//                .useAllAvailableWidth();
//
//        // Logo
//        headerTable.addCell(
//                new Cell().add(logo)
//                        .setBorder(Border.NO_BORDER)
//                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
//        );
//
//        headerTable.addCell(
//                new Cell()
//
//                        // NGO Name: ABC (pink) + DEF (black)
//                        .add(
//                                new Paragraph()
//                                        .add(new Text("Aarine ")
//                                                .setFont(googleBold)
//                                                .setFontSize(20)
//                                                .setFontColor(new DeviceRgb(236, 38, 143)))
//                                        .add(new Text("Foundation")
//                                                .setFont(googleBold)
//                                                .setFontSize(20)
//                                                .setFontColor(ColorConstants.BLACK))
//                                        .setMarginBottom(0)
//                        )
//
//                        // Registration No
//                        .add(new Paragraph("Registration No.: E-34254(M)")
//                                .setFont(googleRegular)
//                                .setFontSize(10)
//                                .setFontColor(ColorConstants.DARK_GRAY)
//                                .setMargin(0)
//                                .setFixedLeading(11))
//
//                        // PAN No
//                        .add(new Paragraph("PAN No.: AAHTA5687L")
//                                .setFont(googleRegular)
//                                .setFontSize(10)
//                                .setFontColor(ColorConstants.DARK_GRAY)
//                                .setMargin(0)
//                                .setFixedLeading(11))
//
//                        // Office Address
//                        .add(new Paragraph("Off. Add: 102 First floor Gaondevi Krupa Building,")
//                                .setFont(googleRegular)
//                                .setFontSize(10)
//                                .setFontColor(ColorConstants.DARK_GRAY)
//                                .setMargin(0)
//                                .setFixedLeading(11))
//
//                        .add(new Paragraph("Opposite Ghansoli Post Office, Navi Mumbai - 400701")
//                                .setFont(googleRegular)
//                                .setFontSize(10)
//                                .setFontColor(ColorConstants.DARK_GRAY)
//                                .setMargin(0)
//                                .setFixedLeading(11))
//
//                        // Registered Address
//                        .add(new Paragraph("Reg. Add: C-6/1, Transit Camp, Kokari Agar,")
//                                .setFont(googleRegular)
//                                .setFontSize(10)
//                                .setFontColor(ColorConstants.DARK_GRAY)
//                                .setMargin(0)
//                                .setFixedLeading(11))
//
//                        .add(new Paragraph("Antop Hill Sion Mumbai - 400037")
//                                .setFont(googleRegular)
//                                .setFontSize(10)
//                                .setFontColor(ColorConstants.DARK_GRAY)
//                                .setMargin(0)
//                                .setFixedLeading(11))
//
//                        .setBorder(Border.NO_BORDER)
//                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
//                        
//                        .add(new Paragraph("\n"))
//        );
//
//
//
//        // Receipt details
//        headerTable.addCell(
//                new Cell()
//
//                        // Receipt No
//		                .add(new Paragraph("RECEIPT")
//		                        .setFont(googleBold)
//		                        .setFontSize(20)
//		                        .setMarginTop(0)
//		                        .setFontColor(ColorConstants.BLACK)
////		                        .setMarginBottom(0)
//		                        )
//		
//		                // Registration No
//		                .add(new Paragraph("PZ/80G/E/0226/4")
//		                        .setFont(googleRegular)
//		                        .setFontSize(12)
//		                        .setFontColor(ColorConstants.DARK_GRAY)
//		                        .setMarginTop(0)
////		                        .setMarginBottom(1)
//		                        .setFixedLeading(11))
//
//                        // Date
//                        .add(new Paragraph()
//                                .add(new Text("Date: ")
//                                        .setFont(googleBold)
//                                        .setFontSize(10)
//                                        .setFontColor(ColorConstants.BLACK))
//                                .add(new Text("16/02/2026")
//                                        .setFont(googleRegular)
//                                        .setFontSize(10)
//                                        .setFontColor(ColorConstants.DARK_GRAY))
////                                .setMarginTop(0)
////                                .setMarginBottom(0)
////                                .setFixedLeading(11)
//                        )
//                        .setTextAlignment(TextAlignment.RIGHT)
//                        .setBorder(Border.NO_BORDER)
//                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
//        );
//
//        outerCell.add(headerTable);
//
//        // Divider
//        LineSeparator line = new LineSeparator(new SolidLine(1));
//        outerCell.add(line);
//        outerCell.add(new Paragraph("\n"));
//
//        // ================= DONATION MESSAGE =================
//        outerCell.add(new Paragraph()
//                .add(new Text("Aarine Foundation ")
//                        .setFont(googleBold)
//                        .setFontSize(10))
//                
//                .add(new Text("gratefully acknowledges the generous contribution received from -")
//                        .setFont(googleRegular)
//                        .setFontSize(10)
//                        .setFontColor(ColorConstants.BLACK)));
//
//
//        outerCell.add(new Paragraph(""));
//
//        outerCell.add(new Paragraph("Mr. Aashtik Aakash Jadav.").setFont(googleBold).setFontSize(10));
//        outerCell.add(new Paragraph("Address: XXXX").setFont(googleRegular).setFontSize(10));
//        outerCell.add(new Paragraph("Email: XXXX").setFont(googleRegular).setFontSize(10));
//        outerCell.add(new Paragraph("Contact No.: 8421159692").setFont(googleRegular).setFontSize(10));
//        outerCell.add(new Paragraph("PAN No.: XXXX").setFont(googleRegular).setFontSize(10));
//
//        outerCell.add(new Paragraph(""));
//
//        outerCell.add(
//                new Paragraph()
//                        .add(new Text("We sincerely thank you for your kind donation of ")
//                                .setFont(googleRegular)
//                                .setFontSize(10))
//                        
//                        .add(new Text("₹100")
//                                .setFont(googleBold)
//                                .setFontSize(12)   // 👈 bigger
//                                .setFontColor(ColorConstants.BLACK))
//                        
//                        .add(new Text("/- (Rupees One Hundred Only).")
//                                .setFont(googleRegular)
//                                .setFontSize(10))
//        );
//
//
//        outerCell.add(
//                new Paragraph(
//                        "Your generous support will help us continue our efforts towards social welfare and community development. "
//                      + "We truly appreciate your commitment to making a positive difference in the lives of those in need.")
//                        .setFont(googleRegular)
//                        .setFontSize(10)
//        );
//
//        outerCell.add(new Paragraph("\n\n\n"));
//
//        outerCell.add(
//                new Paragraph("Authorised Sign.")
//                        .setFont(googleRegular)
//                        .setTextAlignment(TextAlignment.RIGHT)
//        );
//        outerCell.add(
//                new Paragraph(
//                        "This is a system-generated receipt and does not require a physical signature.")
//                        .setFont(googleRegular)
//                        .setFontSize(8)
//                        .setFontColor(ColorConstants.DARK_GRAY)
//                        .setTextAlignment(TextAlignment.RIGHT)
//                        .setMarginTop(2)
//        );
//
//        
//     // ================= QR CODE (BOTTOM RIGHT) =================
//
//     // Example verification URL (make dynamic later)
//     String qrData = "https://aarine.in/verify-receipt/1545/yuuu";
//
//     // Create QR code
//     BarcodeQRCode qrCode = new BarcodeQRCode(qrData);
//     Image qrImage = new Image(qrCode.createFormXObject(ColorConstants.BLACK, pdf));
//
//     // Size of QR
//     qrImage.setWidth(80);
//     qrImage.setHeight(80);
//
//     // Align QR to right
//     qrImage.setHorizontalAlignment(com.itextpdf.layout.property.HorizontalAlignment.RIGHT);
//
//     // Add some space before QR
//     outerCell.add(new Paragraph("\n"));
//
//     // Optional label above QR
////     outerCell.add(
////             new Paragraph("Scan to verify receipt")
////                     .setFont(googleRegular)
////                     .setFontSize(8)
////                     .setFontColor(ColorConstants.DARK_GRAY)
////                     .setTextAlignment(TextAlignment.RIGHT)
////     );
//
//     // Add QR to cell
//     outerCell.add(qrImage);
//     outerCell.add(new Paragraph("\n\n"));
//     
//  // Divider
//     LineSeparator thankYouLine = new LineSeparator(new SolidLine(1));
//     outerCell.add(thankYouLine);
//
//     
//
//     outerCell.add(
//             new Paragraph("Thank You Letter")
//                     .setFont(googleBold)
//                     .setFontColor(ColorConstants.DARK_GRAY)
//                     .setFontSize(12)
//                     .setTextAlignment(TextAlignment.CENTER)
//     );
//
//     outerCell.add(
//    	        new Paragraph(
//    	                "Aarine Foundation is a Government Registered organization working for the welfare of Women & Children since 2017. "
//    	              + "We are continuously supporting initiatives in Education, Health, Youth, Poverty, Livelihood and "
//    	              + "Community Development.\n"
//    	              + "We are enclosing a receipt against your donation along with this letter. "
//    	              + "We look forward to a long-term relationship and your continued support.\n"
//    	              + "Thank you for your generosity and trust.")
//    	                .setFont(googleRegular)
//    	                .setFontColor(ColorConstants.DARK_GRAY)
//    	                .setFontSize(8)
//    	                .setMarginTop(0)
//    	                .setMarginBottom(0)
//    	                .setMultipliedLeading(1.1f)   // tight line spacing
//    	);
//
//
//     outerCell.add(new Paragraph("\n"));
//
//     outerCell.add(
//             new Paragraph(
//                     "Your donation is eligible for 50% tax benefit under section 80G of the Income Tax Act.")
//                     .setFont(googleBold).setTextAlignment(TextAlignment.CENTER)
//                     .setFontSize(10)
//                     .setMarginBottom(0)
//                     .setFixedLeading(11)
//     );
//
//     
//
//        // ================= ADD TO DOCUMENT =================
//        outerTable.addCell(outerCell);
//        document.add(outerTable);
//
//        // Footer outside border
//        document.add(
//                new Paragraph("Powered by Datfuslab Technologies Pvt. Ltd.")
//                        .setFont(googleRegular)
//                        .setFontSize(9)
//                        .setFontColor(ColorConstants.GRAY)
//                        .setTextAlignment(TextAlignment.LEFT)
//        );
//
//        document.close();
//        return baos.toByteArray();
//    }
//}
