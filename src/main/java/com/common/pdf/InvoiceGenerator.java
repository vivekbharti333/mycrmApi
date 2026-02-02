package com.common.pdf;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
//import com.itextpdf.layout.border.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component
public class InvoiceGenerator {

    public byte[] generateInvoice() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf);

            // Fonts
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont font_grey = PdfFontFactory.createFont(StandardFonts.HELVETICA);           
            
            Table logo = new Table(UnitValue.createPercentArray(new float[]{2, 1}));
            logo.setWidth(UnitValue.createPercentValue(100));
           
                             
            Image logoImage = new Image(ImageDataFactory.create("C:/Users/lenovo/OneDrive/Desktop/Untitled.png"))
                    .setWidth(150)   // set desired width
                    .setHeight(120)  // set desired height
                    .setAutoScale(false); // disable auto-scaling

            Cell logoCell = new Cell()
                    .add(logoImage)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setPaddingBottom(10)
                    .setBorder(Border.NO_BORDER); 

            
            Cell invoiceNo = new Cell()
            	    .add(new Paragraph("INVOICE")
            	            .setFont(bold)
            	            .setFontSize(20)
            	            .setTextAlignment(TextAlignment.RIGHT))
            	    .add(new Paragraph("# DEF-10/002")
            	            .setFont(font)
            	            .setFontSize(12)
            	            .setTextAlignment(TextAlignment.RIGHT))
            	    .setBorder(Border.NO_BORDER); //  removes all borders for this cell

           
            logo.addCell(logoCell);
            logo.addCell(invoiceNo);
           
            doc.add(logo);

            // ======= HEADER SECTION =======
            Table header = new Table(UnitValue.createPercentArray(new float[]{3, 2}));
            header.setWidth(UnitValue.createPercentValue(100));

            // Company Info (Left side)
            Cell companyInfo = new Cell()
                    .add(new Paragraph("Datfuslab Technologies Pvt. Ltd")
                            .setFont(bold)
                            .setFontSize(12))
                    .add(new Paragraph("GSTIN : 09AAKCD5557C1ZJ\n" +
                            "1507-B12, Sector-16B, Greater Noida\n" +
                            "Website : https://datfuslab.com\n" +
                            "Email : info@datfuslab.com\n" +
                            "Mobile : +91-7004063385")
                            .setFont(font)
                            .setFontSize(10))
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.LEFT);

            // Invoice Title + Number (Right side)
            Cell invoiceInfoHeading = new Cell()
                    .add(new Paragraph("Date :   17-Oct-2025")
                            .setFont(font)
                            .setFontSize(12)
                            .setTextAlignment(TextAlignment.RIGHT))
                    .add(new Paragraph("Due Date :   17-Oct-2025")
                            .setFont(font)
                            .setFontSize(12)
                            .setTextAlignment(TextAlignment.RIGHT))
                    .add(new Paragraph("Balance Due :   Rs 30000").setBackgroundColor(new DeviceRgb(240, 240, 240))
                            .setFont(bold)
                            .setFontSize(12)
                            .setPadding(6)) // adds inner spacing
                            .setMinHeight(25)
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setVerticalAlignment(com.itextpdf.layout.property.VerticalAlignment.MIDDLE);
           

            header.addCell(companyInfo);
            header.addCell(invoiceInfoHeading);

            doc.add(header);

            doc.add(new Paragraph("\n")); // Spacer

            // ======= BILL TO SECTION =======
            doc.add(new Paragraph("Bill To:")
                    .setFont(bold)
                    .setFontSize(12)
                    .setMarginBottom(4));
            doc.add(new Paragraph("Childrens Educare Foundation\n" +
                    "4WS8B, 4th floor West Block, Mani Casadona, Action area 2,\n" +
                    "New town, Rajarhat, Kolkata - 7000156")
                    .setFont(font)
                    .setFontSize(10)
                    .setMarginBottom(10));

            // ======= ITEM TABLE =======
//            Table table = new Table(UnitValue.createPercentArray(new float[]{5, 2, 2, 2}));
//            table.setWidth(UnitValue.createPercentValue(100));
//
//            String[] headers = {"Item", "Quantity", "Rate", "Amount"};
//            for (String h : headers) {
//                table.addHeaderCell(new Cell()
//                        .add(new Paragraph(h).setFont(bold).setFontColor(ColorConstants.WHITE))
//                        .setBackgroundColor(ColorConstants.BLACK)
//                        .setTextAlignment(TextAlignment.CENTER));
//            }
//
//            // Row 1
//            table.addCell(new Paragraph("1. Transactional Email").setFont(font));
//            table.addCell(new Paragraph("50000").setFont(font).setTextAlignment(TextAlignment.CENTER));
//            table.addCell(new Paragraph("Rs. 0.16").setFont(font).setTextAlignment(TextAlignment.RIGHT));
//            table.addCell(new Paragraph("Rs. 8,000.00").setFont(font).setTextAlignment(TextAlignment.RIGHT));
//
//            // Row 2
//            table.addCell(new Paragraph("2. Annual subscription for My Donation App\n" +
//                    "(includes hosting, support, updates, and maintenance)").setFont(font));
//            table.addCell(new Paragraph("1").setFont(font).setTextAlignment(TextAlignment.CENTER));
//            table.addCell(new Paragraph("Rs. 25,000.00").setFont(font).setTextAlignment(TextAlignment.RIGHT));
//            table.addCell(new Paragraph("Rs. 25,000.00").setFont(font).setTextAlignment(TextAlignment.RIGHT));
//
//            doc.add(table);
            
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 5, 2, 2, 2}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Header row (black background, white text, no border)
            String[] headers = {"Sr no.", "Item", "Quantity", "Rate", "Amount"};
            for (String h : headers) {
                table.addHeaderCell(new Cell()
                        .add(new Paragraph(h)
                                .setFont(bold)
                                .setFontColor(ColorConstants.WHITE))
                        .setBackgroundColor(ColorConstants.BLACK)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER)); // ✅ remove border
            }

            // Row 1
            table.addCell(new Cell().add(new Paragraph("1").setFont(font).setFontColor(ColorConstants.GRAY))
                    .setBorder(Border.NO_BORDER)); // ✅
            table.addCell(new Cell().add(new Paragraph("Transactional Email").setFont(font).setFontColor(ColorConstants.GRAY))
                    .setBorder(Border.NO_BORDER)); // ✅
            table.addCell(new Cell().add(new Paragraph("50000").setFont(font).setFontColor(ColorConstants.GRAY).setTextAlignment(TextAlignment.CENTER))
                    .setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("Rs. 0.16").setFont(font).setFontColor(ColorConstants.GRAY).setTextAlignment(TextAlignment.RIGHT))
                    .setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("Rs. 8,000.00").setFont(font).setFontColor(ColorConstants.GRAY).setTextAlignment(TextAlignment.RIGHT))
                    .setBorder(Border.NO_BORDER));

            // Row 2
            table.addCell(new Cell().add(new Paragraph("1").setFont(font_grey))
                    .setBorder(Border.NO_BORDER)); // ✅
            table.addCell(new Cell().add(new Paragraph("Annual subscription for My Donation App\n" +
                    "(includes hosting, support, updates, and maintenance)").setFont(font_grey))
                    .setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("1").setFont(font_grey).setTextAlignment(TextAlignment.CENTER))
                    .setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("Rs. 25,000.00").setFont(font_grey).setTextAlignment(TextAlignment.RIGHT))
                    .setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("Rs. 25,000.00").setFont(font_grey).setTextAlignment(TextAlignment.RIGHT))
                    .setBorder(Border.NO_BORDER));

            doc.add(table);


            // ======= TOTALS =======
            doc.add(new Paragraph("\nSubtotal:    Rs. 33,000.00")
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFont(font));
            doc.add(new Paragraph("Tax (18%):    Rs. 5,940.00")
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFont(font));
            doc.add(new Paragraph("Total:    Rs. 38,940.00")
                    .setFont(bold)
                    .setTextAlignment(TextAlignment.RIGHT));

            // ======= NOTES =======
            doc.add(new Paragraph("\nNotes:")
                    .setFont(bold)
                    .setMarginTop(10));
            doc.add(new Paragraph("Thank you for your business. Please review all details carefully. " +
                    "For any discrepancies, contact us.")
                    .setFont(font));

            // ======= TERMS =======
            doc.add(new Paragraph("\nTerms:")
                    .setFont(bold));
            doc.add(new Paragraph("Please visit our website for Terms & Conditions: " +
                    "https://datfuslab.com/terms.html")
                    .setFont(font));

            doc.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error creating PDF", e);
        }
    }
}
