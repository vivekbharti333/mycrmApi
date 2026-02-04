package com.common.pdf;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.common.entities.InvoiceHeaderDetails;
import com.common.utility.AmountToWordsConverter;
import com.invoice.entities.InvoiceNumber;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
//import com.itextpdf.layout.border.*;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;

@Component
public class InvoiceGenerator {
	
	@Autowired
	private AmountToWordsConverter amountToWordsConverter;

    public byte[] generateInvoice(InvoiceHeaderDetails invoiceHeaderDetails, InvoiceNumber invoiceNumber) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

        	// Fonts
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont font_grey = PdfFontFactory.createFont(StandardFonts.HELVETICA);  
            Border lightGreyLine = new SolidBorder(new DeviceRgb(220, 220, 220), 0.6f);
            DeviceRgb greyText = new DeviceRgb(140, 140, 140);       // text grey
        	
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new InvoiceFooterHandler(font));
            Document doc = new Document(pdf);

            Table logo = new Table(UnitValue.createPercentArray(new float[]{2, 1}));
            logo.setWidth(UnitValue.createPercentValue(100));
           
//          Image logoImage = new Image(ImageDataFactory.create("C:\\Users\\HP\\Documents\\logo.png"))        
            InputStream is = getClass().getClassLoader().getResourceAsStream("images/dfllogo.png");
            Image logoImage = new Image(ImageDataFactory.create(is.readAllBytes()));
			
            logoImage.setWidth(260);   // set desired width
//                    .setHeight(70)  // set desired height
//                    .setAutoScale(false); // disable auto-scaling

            Cell logoCell = new Cell()
                    .add(logoImage)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setPaddingBottom(10)
                    .setBorder(Border.NO_BORDER); 

            
            Cell invoiceNo = new Cell()
            	    .add(new Paragraph("INVOICE")
            	            .setFont(bold)
            	            .setFontSize(15)
            	            .setTextAlignment(TextAlignment.RIGHT))
            	    .add(new Paragraph("# DEF-10/002")
            	            .setFont(font)
            	            .setFontSize(10)
            	            .setTextAlignment(TextAlignment.RIGHT))
            	    .setBorder(Border.NO_BORDER); 

           
            logo.addCell(logoCell);
            logo.addCell(invoiceNo);
           
            doc.add(logo);

            // ======= HEADER SECTION =======
            Table header = new Table(UnitValue.createPercentArray(new float[]{3, 2}));
            header.setWidth(UnitValue.createPercentValue(100));

            // Company Info (Left side)
            Cell companyInfo = new Cell()
                    // Company Name
                    .add(new Paragraph("Datfuslab Technologies Pvt. Ltd")
                            .setFont(bold)
                            .setFontSize(9))
                    .add(new Paragraph("GSTIN : 09AAKCD5557C1ZJ")
                            .setFont(font)
                            .setFontSize(9))
                    .add(new Paragraph("1507-B12, Sector-16B, Greater Noida")
                            .setFont(font)
                            .setFontSize(9))
                    .add(new Paragraph("Website : https://datfuslab.com")
                            .setFont(font)
                            .setFontSize(9))
                    .add(new Paragraph("Email : info@datfuslab.com")
                            .setFont(font)
                            .setFontSize(9))
                    .add(new Paragraph("Mobile : +91-7004063385")
                            .setFont(font)
                            .setFontSize(9))
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.LEFT);


            // Invoice Title + Number (Right side)
            Cell invoiceInfoHeading = new Cell()
                    .add(new Paragraph("Date :   17-Oct-2025")
                            .setFont(font)
                            .setFontSize(10)
                            .setTextAlignment(TextAlignment.RIGHT))
                    .add(new Paragraph("Due Date :   17-Oct-2025")
                            .setFont(font)
                            .setFontSize(10)
                            .setTextAlignment(TextAlignment.RIGHT))
                    .add(new Paragraph("Balance Due :   Rs 30000").setBackgroundColor(new DeviceRgb(240, 240, 240))
                            .setFont(bold)
                            .setFontSize(10)
                            .setPadding(6)) // adds inner spacing
                            .setMinHeight(25)
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setVerticalAlignment(com.itextpdf.layout.property.VerticalAlignment.MIDDLE);
           

            header.addCell(companyInfo);
            header.addCell(invoiceInfoHeading);

            doc.add(header);

         // Spacer
            doc.add(new Paragraph("\n"));

            // Create 2-column table (50% / 50%)
            float[] columnWidths = {50, 50};
            Table addressTable = new Table(UnitValue.createPercentArray(columnWidths));
            addressTable.setWidth(UnitValue.createPercentValue(100));

            // Bill To
            Cell billToCell = new Cell().setBorder(Border.NO_BORDER);
            billToCell.add(new Paragraph("Bill To:")
            		.setFont(font).setFontColor(greyText)
                    .setFontSize(9));

            // Client Name (slightly bigger / bold if you want)
            billToCell.add(new Paragraph("Childrens Educare Foundation")
                    .setFont(bold)
                    .setFontSize(9));

            // Client Address (smaller)
            billToCell.add(new Paragraph("4WS8B, 4th floor West Block, Mani Casadona, Action area 2 New town, Rajarhat")
                    .setFont(font)
                    .setFontSize(9));
            billToCell.add(new Paragraph("Kolkata - 7000156")
                    .setFont(font)
                    .setFontSize(9));


            addressTable.addCell(billToCell);

            // Delivery Address
            Cell deliveryCell = new Cell().setBorder(Border.NO_BORDER);
            deliveryCell.add(new Paragraph("Delivery Address:")
                    .setFont(font).setFontColor(greyText)
                    .setFontSize(9));

            // Name (slightly bigger / bold if you like)
            deliveryCell.add(new Paragraph("Delivery Name")
                    .setFont(bold)
                    .setFontSize(9));

            // Address (smaller)
            deliveryCell.add(new Paragraph( "Delivery Street Address City, State - Pincode")
                    .setFont(font)
                    .setFontSize(9));

            addressTable.addCell(deliveryCell);

            doc.add(addressTable);



            // ======= ITEM TABLE =======            
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 8, 2, 2, 2}));
            table.setWidth(UnitValue.createPercentValue(100));
            table.setMarginTop(15);

            // Header row (black background, white text, no border)
            String[] headers = {"#", "Item", "Quantity", "Rate", "Amount"};
            for (String h : headers) {
                table.addHeaderCell(new Cell()
                        .add(new Paragraph(h)
                                .setFont(bold)
                                .setFontSize(9)
                                .setFontColor(ColorConstants.WHITE))
                        .setBackgroundColor(ColorConstants.BLACK)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER)); // ✅ remove border
            }
           
            // Row 1
            table.addCell(new Cell().add(new Paragraph("1").setFont(font_grey)).setFontSize(10).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).setBorderBottom(lightGreyLine)); 
            table.addCell(new Cell().add(new Paragraph("Annual subscription for My Donation App\n" +
                    "(includes hosting, support, updates, and maintenance)").setFont(font_grey)).setFontSize(9).setBorder(Border.NO_BORDER).setBorderBottom(lightGreyLine));
            table.addCell(new Cell().add(new Paragraph("1").setFont(font_grey).setFontSize(10).setTextAlignment(TextAlignment.CENTER))
                    .setBorder(Border.NO_BORDER).setBorderBottom(lightGreyLine));
            table.addCell(new Cell().add(new Paragraph("25,000.00").setFont(font_grey).setFontSize(9).setTextAlignment(TextAlignment.RIGHT))
                    .setBorder(Border.NO_BORDER).setBorderBottom(lightGreyLine));
            table.addCell(new Cell().add(new Paragraph("25,000.00").setFont(font_grey).setFontSize(9).setTextAlignment(TextAlignment.RIGHT))
                    .setBorder(Border.NO_BORDER).setBorderBottom(lightGreyLine));
            
            // Row 2
         // Row 2 (no border bottom)
            table.addCell(new Cell().add(new Paragraph("2").setFont(font_grey)).setFontSize(10).setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER).setBorderBottom(lightGreyLine));

            table.addCell(new Cell().add(new Paragraph("Annual subscription for My Donation App\n" +
                    "(includes hosting, support, updates, and maintenance)").setFont(font_grey)).setFontSize(9)
                    .setBorder(Border.NO_BORDER).setBorderBottom(lightGreyLine));

            table.addCell(new Cell().add(new Paragraph("1").setFont(font_grey))
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER).setBorderBottom(lightGreyLine));

            table.addCell(new Cell().add(new Paragraph("25,000.00").setFont(font_grey))
                    .setFontSize(9)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER).setBorderBottom(lightGreyLine));

            table.addCell(new Cell().add(new Paragraph("25,000.00").setFont(font_grey))
                    .setFontSize(9)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER).setBorderBottom(lightGreyLine));

            doc.add(table);


            // ======= TOTALS =======
            doc.add(new Paragraph("\nSubtotal:    Rs. 33,000.00")
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFont(font).setFontSize(9));
            doc.add(new Paragraph("Tax (18%):    Rs. 5,940.00")
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFont(font).setFontSize(9));
            doc.add(new Paragraph("Total:    Rs. 38,940.00")
                    .setFont(bold).setFontSize(9)
                    .setTextAlignment(TextAlignment.RIGHT));
            
            doc.add(new Paragraph()
                    .add(new Text("Amount in Words: ").setFont(font).setFontSize(9).setFontColor(greyText))
                    .add(new Text("Indian Rupees " + amountToWordsConverter.amountInWords(38940) + " Only.")
                            .setFont(bold).setFontSize(9))
                    .setTextAlignment(TextAlignment.RIGHT));


            // ======= NOTES =======
            doc.add(new Paragraph("\n\n\nNotes:")
                    .setFont(bold).setMarginBottom(0).setFontSize(9)
                    .setMarginTop(7));
            doc.add(new Paragraph("Thank you for your business. Please review all details carefully. " +
                    "For any discrepancies, contact us.")
                    .setFont(font).setMarginTop(0).setFontSize(8));

            // ======= TERMS =======
            doc.add(new Paragraph("Terms:")
                    .setFont(bold)
                    .setFontSize(9).setMarginBottom(0));
            doc.add(new Paragraph("Please visit our website for Terms & Conditions: " +
                    "https://datfuslab.com/terms.html")
                    .setFont(font).setMarginTop(0).setFontSize(8));
            
            
         // QR content (put whatever you want here)
            String qrText = "Invoice No: DEF-10/002 | Amount: 38940";

            // Create QR
            BarcodeQRCode qrCode = new BarcodeQRCode(qrText);
            PdfFormXObject qrObject = qrCode.createFormXObject(ColorConstants.BLACK, pdf);

            // Convert to Image
            Image qrImage = new Image(qrObject)
                    .setWidth(70)   // QR size
                    .setHeight(70);

            // Bottom-right position
            int lastPage = pdf.getNumberOfPages();

            qrImage.setFixedPosition(
                    lastPage,   // page number
                    500,        // X (adjust if needed)
                    40          // Y from bottom
            );

            // Add to document
            doc.add(qrImage);

         
            
//            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new InvoiceFooterHandler(font));

            doc.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error creating PDF", e);
        }
    }
}
