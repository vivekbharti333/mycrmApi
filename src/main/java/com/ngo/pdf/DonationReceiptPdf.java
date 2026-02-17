package com.ngo.pdf;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.springframework.stereotype.Component;

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
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

@Component
public class DonationReceiptPdf {

    public byte[] generateInvoice() throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.setMargins(30, 30, 30, 30);

        // ================= LOAD ROBOTO FONTS =================
        PdfFont robotoRegular;
        PdfFont robotoBold;

        try (
            InputStream regularStream = getClass().getClassLoader()
                    .getResourceAsStream("fonts/roboto/Roboto-Regular.ttf");
            InputStream boldStream = getClass().getClassLoader()
                    .getResourceAsStream("fonts/roboto/Roboto-Bold.ttf");
        ) {
            if (regularStream == null || boldStream == null) {
                throw new RuntimeException("Roboto font files not found in classpath");
            }

            robotoRegular = PdfFontFactory.createFont(
                    regularStream.readAllBytes(),
                    PdfEncodings.IDENTITY_H,
                    true
            );

            robotoBold = PdfFontFactory.createFont(
                    boldStream.readAllBytes(),
                    PdfEncodings.IDENTITY_H,
                    true
            );
        }

        // ================= OUTER BORDER =================
        Table outerTable = new Table(1).useAllAvailableWidth();

        Cell outerCell = new Cell()
                .setBorder(new SolidBorder(ColorConstants.BLACK, 1))
                .setPadding(15);

        // ================= HEADER =================
        String logoPath = "C:\\Users\\HP\\Downloads\\download.png";
        ImageData imageData = ImageDataFactory.create(logoPath);
        Image logo = new Image(imageData).setWidth(90).setHeight(80);

        Table headerTable = new Table(UnitValue.createPercentArray(new float[]{2, 5, 3}))
                .useAllAvailableWidth();

        // Logo
        headerTable.addCell(
                new Cell().add(logo)
                        .setBorder(Border.NO_BORDER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
        );

        headerTable.addCell(
                new Cell()

                        // NGO Name: ABC (pink) + DEF (black)
                        .add(
                                new Paragraph()
                                        .add(new Text("Aarine ")
                                                .setFont(robotoBold)
                                                .setFontSize(20)
                                                .setFontColor(new DeviceRgb(236, 38, 143)))
                                        .add(new Text("Foundation")
                                                .setFont(robotoBold)
                                                .setFontSize(20)
                                                .setFontColor(ColorConstants.BLACK))
                                        .setMarginBottom(2)
                        )

                        // Registration No
                        .add(new Paragraph("Registration No.: E-34254(M)")
                                .setFont(robotoRegular)
                                .setFontSize(10)
                                .setFontColor(ColorConstants.DARK_GRAY)
                                .setMargin(0)
                                .setFixedLeading(11))

                        // PAN No
                        .add(new Paragraph("PAN No.: AAHTA5687L")
                                .setFont(robotoRegular)
                                .setFontSize(10)
                                .setFontColor(ColorConstants.DARK_GRAY)
                                .setMargin(0)
                                .setFixedLeading(11))

                        // Office Address
                        .add(new Paragraph("Off. Add: 102 First floor Gaondevi Krupa Building,")
                                .setFont(robotoRegular)
                                .setFontSize(10)
                                .setFontColor(ColorConstants.DARK_GRAY)
                                .setMargin(0)
                                .setFixedLeading(11))

                        .add(new Paragraph("Opposite Ghansoli Post Office, Navi Mumbai - 400701")
                                .setFont(robotoRegular)
                                .setFontSize(10)
                                .setFontColor(ColorConstants.DARK_GRAY)
                                .setMargin(0)
                                .setFixedLeading(11))

                        // Registered Address
                        .add(new Paragraph("Reg. Add: C-6/1, Transit Camp, Kokari Agar,")
                                .setFont(robotoRegular)
                                .setFontSize(10)
                                .setFontColor(ColorConstants.DARK_GRAY)
                                .setMargin(0)
                                .setFixedLeading(11))

                        .add(new Paragraph("Antop Hill Sion Mumbai - 400037")
                                .setFont(robotoRegular)
                                .setFontSize(10)
                                .setFontColor(ColorConstants.DARK_GRAY)
                                .setMargin(0)
                                .setFixedLeading(11))

                        .setBorder(Border.NO_BORDER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        
                        .add(new Paragraph("\n"))
        );



        // Receipt details
        headerTable.addCell(
                new Cell()

                        // Receipt No
		                .add(new Paragraph("RECEIPT")
		                        .setFont(robotoBold)
		                        .setFontSize(20)
		                        .setFontColor(ColorConstants.BLACK)
		                        .setMarginBottom(0))
		
		                // Registration No
		                .add(new Paragraph("1545/yuuu")
		                        .setFont(robotoRegular)
		                        .setFontSize(12)
		                        .setFontColor(ColorConstants.DARK_GRAY)
		                        .setMarginTop(0)
		                        .setMarginBottom(0)
		                        .setFixedLeading(11))

                        // Date
                        .add(new Paragraph()
                                .add(new Text("Date: ")
                                        .setFont(robotoBold)
                                        .setFontSize(10)
                                        .setFontColor(ColorConstants.BLACK))
                                .add(new Text("16/02/2026")
                                        .setFont(robotoRegular)
                                        .setFontSize(10)
                                        .setFontColor(ColorConstants.DARK_GRAY))
                                .setMarginTop(0)
                                .setMarginBottom(0)
                                .setFixedLeading(11)
                        )
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setBorder(Border.NO_BORDER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
        );

        outerCell.add(headerTable);

        // Divider
        LineSeparator line = new LineSeparator(new SolidLine(1));
        outerCell.add(line);
        outerCell.add(new Paragraph("\n"));

        // ================= DONATION MESSAGE =================
        outerCell.add(new Paragraph()
                .add(new Text("Aarine Foundation ")
                        .setFont(robotoBold)
                        .setFontSize(10))
                
                .add(new Text("gratefully acknowledges the generous contribution received from -")
                        .setFont(robotoRegular)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK)));


        outerCell.add(new Paragraph(""));

        outerCell.add(new Paragraph("Mr. Aashtik Aakash Jadav.").setFont(robotoBold).setFontSize(10));
        outerCell.add(new Paragraph("Address: XXXX").setFont(robotoRegular).setFontSize(10));
        outerCell.add(new Paragraph("Email: XXXX").setFont(robotoRegular).setFontSize(10));
        outerCell.add(new Paragraph("Contact No.: 8421159692").setFont(robotoRegular).setFontSize(10));
        outerCell.add(new Paragraph("PAN No.: XXXX").setFont(robotoRegular).setFontSize(10));

        outerCell.add(new Paragraph(""));

        outerCell.add(
                new Paragraph()
                        .add(new Text("We sincerely thank you for your kind donation of ")
                                .setFont(robotoRegular)
                                .setFontSize(10))
                        
                        .add(new Text("₹100")
                                .setFont(robotoBold)
                                .setFontSize(12)   // 👈 bigger
                                .setFontColor(ColorConstants.BLACK))
                        
                        .add(new Text("/- (Rupees One Hundred Only).")
                                .setFont(robotoRegular)
                                .setFontSize(10))
        );


        outerCell.add(
                new Paragraph(
                        "Your generous support will help us continue our efforts towards social welfare and community development. "
                      + "We truly appreciate your commitment to making a positive difference in the lives of those in need.")
                        .setFont(robotoRegular)
                        .setFontSize(10)
        );

        outerCell.add(new Paragraph("\n\n\n"));

        outerCell.add(
                new Paragraph("Authorised Sign.")
                        .setFont(robotoRegular)
                        .setTextAlignment(TextAlignment.RIGHT)
        );
        outerCell.add(
                new Paragraph(
                        "This is a system-generated receipt and does not require a physical signature.")
                        .setFont(robotoRegular)
                        .setFontSize(8)
                        .setFontColor(ColorConstants.DARK_GRAY)
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setMarginTop(2)
        );

        
     // ================= QR CODE (BOTTOM RIGHT) =================

     // Example verification URL (make dynamic later)
     String qrData = "https://aarine.in/verify-receipt/1545/yuuu";

     // Create QR code
     BarcodeQRCode qrCode = new BarcodeQRCode(qrData);
     Image qrImage = new Image(qrCode.createFormXObject(ColorConstants.BLACK, pdf));

     // Size of QR
     qrImage.setWidth(80);
     qrImage.setHeight(80);

     // Align QR to right
     qrImage.setHorizontalAlignment(com.itextpdf.layout.property.HorizontalAlignment.RIGHT);

     // Add some space before QR
     outerCell.add(new Paragraph("\n"));

     // Optional label above QR
//     outerCell.add(
//             new Paragraph("Scan to verify receipt")
//                     .setFont(robotoRegular)
//                     .setFontSize(8)
//                     .setFontColor(ColorConstants.DARK_GRAY)
//                     .setTextAlignment(TextAlignment.RIGHT)
//     );

     // Add QR to cell
     outerCell.add(qrImage);
     outerCell.add(new Paragraph("\n\n\n"));
     
  // Divider
     LineSeparator thankYouLine = new LineSeparator(new SolidLine(1));
     outerCell.add(thankYouLine);

     

     outerCell.add(
             new Paragraph("Thank You Letter")
                     .setFont(robotoBold)
                     .setFontColor(ColorConstants.DARK_GRAY)
                     .setFontSize(12)
                     .setTextAlignment(TextAlignment.CENTER)
     );

     outerCell.add(
    	        new Paragraph(
    	                "Aarine Foundation is a Government Registered organization working for the welfare of Women & Children since 2017. "
    	              + "We are continuously supporting initiatives in Education, Health, Youth, Poverty, Livelihood and "
    	              + "Community Development.\n"
    	              + "We are enclosing a receipt against your donation along with this letter. "
    	              + "We look forward to a long-term relationship and your continued support.\n"
    	              + "Thank you for your generosity and trust.")
    	                .setFont(robotoRegular)
    	                .setFontColor(ColorConstants.DARK_GRAY)
    	                .setFontSize(8)
    	                .setMarginTop(0)
    	                .setMarginBottom(0)
    	                .setMultipliedLeading(1.1f)   // tight line spacing
    	);


     outerCell.add(new Paragraph("\n"));

     outerCell.add(
             new Paragraph(
                     "Your donation is eligible for 50% tax benefit under section 80G of the Income Tax Act.")
                     .setFont(robotoBold).setTextAlignment(TextAlignment.CENTER)
                     .setFontSize(10)
                     .setMarginBottom(0)
                     .setFixedLeading(11)
     );

     

        // ================= ADD TO DOCUMENT =================
        outerTable.addCell(outerCell);
        document.add(outerTable);

        // Footer outside border
        document.add(
                new Paragraph("Powered by Datfuslab Technologies Pvt. Ltd.")
                        .setFont(robotoRegular)
                        .setFontSize(9)
                        .setFontColor(ColorConstants.GRAY)
                        .setTextAlignment(TextAlignment.LEFT)
        );

        document.close();
        return baos.toByteArray();
    }
}
