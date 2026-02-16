package com.ngo.pdf;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.invoice.pdf.InvoiceReceipt;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
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
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;


@Component
public class DonationReceiptPdf {

    public byte[] generateInvoice() throws Exception {



        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        
        
        // ===================== LOAD ROBOTO FROM CLASSPATH =====================
        BaseFont baseRegular;
        BaseFont baseMedium;
        BaseFont baseBold;

        try (
            InputStream regularStream =
                    InvoiceReceipt.class.getClassLoader()
                            .getResourceAsStream("fonts/roboto/Roboto-Regular.ttf");
            InputStream mediumStream =
                    InvoiceReceipt.class.getClassLoader()
                            .getResourceAsStream("fonts/roboto/Roboto-Medium.ttf");
            InputStream boldStream =
                    InvoiceReceipt.class.getClassLoader()
                            .getResourceAsStream("fonts/roboto/Roboto-Bold.ttf");
        ) {
            if (regularStream == null || boldStream == null) {
                throw new RuntimeException("Roboto font files not found in classpath");
            }

            baseRegular = BaseFont.createFont(
                    "Roboto-Regular.ttf",
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED,
                    true,
                    regularStream.readAllBytes(),
                    null
            );

            baseMedium  = BaseFont.createFont(
                    "Roboto-Bold.ttf",
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED,
                    true,
                    mediumStream.readAllBytes(),
                    null
            );
            
            baseBold = BaseFont.createFont(
                    "Roboto-Bold.ttf",
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED,
                    true,
                    boldStream.readAllBytes(),
                    null
            );
            
        }

        // ===================== FONTS =====================
        Font titleFont = new Font(baseBold, 16);
        Font bold = new Font(baseBold, 10);
        Font normal = new Font(baseRegular, 10);
        Font lightGraySmall = new Font(baseRegular, 9, Font.NORMAL, BaseColor.GRAY);
        Font headerWhite = new Font(baseBold, 10, Font.BOLD, BaseColor.WHITE);
        Font totalBold = new Font(baseMedium, 11, Font.BOLD);


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        // ================= OUTER BORDER =================
        Table outerTable = new Table(1)
                .useAllAvailableWidth();

        Cell outerCell = new Cell()
                .setBorder(new SolidBorder(ColorConstants.BLACK, 1))
                .setPadding(15);

        // ================= HEADER + RECEIPT =================

        String logoPath = "C:\\Users\\HP\\Downloads\\logo.png";
        ImageData imageData = ImageDataFactory.create(logoPath);
        Image logo = new Image(imageData)
                .setWidth(70)
                .setHeight(70);

        Table headerTable = new Table(UnitValue.createPercentArray(new float[]{1, 4, 2}))
                .useAllAvailableWidth();

        // Logo
        headerTable.addCell(
                new Cell().add(logo)
                        .setBorder(Border.NO_BORDER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
        );

        // NGO details
        headerTable.addCell(
                new Cell()
                        .add(new Paragraph("Aarine Foundation")
                                .setBold()
                                .setFontSize(16))
                        .add(new Paragraph(
                                "Registration No.: E-34254(M)\n" +
                                "PAN No.: AAHTA5687L\n" +
                                "Off. Add: 102 First floor Gaondevi Krupa Building,\n" +
                                "Opposite Ghansoli Post Office, Navi Mumbai - 400701\n" +
                                "Reg. Add: C-6/1, Transit Camp, Kokari Agar,\n" +
                                "Antop Hill Sion Mumbai - 400037")
                                .setFontSize(10))
                        .setBorder(Border.NO_BORDER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
        );

        // Receipt details
        headerTable.addCell(
                new Cell()
                        .add(new Paragraph().add(new Text("Receipt No: ").setBold())
                                .add("PZ/80G/E/022026/42739"))
                        .add(new Paragraph().add(new Text("Date: ").setBold())
                                .add("16/02/2026"))
                        .add(new Paragraph().add(new Text("80G Reg: ").setBold())
                                .add("E-34254(M)"))
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setBorder(Border.NO_BORDER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
        );

        outerCell.add(headerTable);

        // Horizontal line
        LineSeparator line = new LineSeparator(new SolidLine(1));
        line.setWidth(UnitValue.createPercentValue(100));
        outerCell.add(line);
        outerCell.add(new Paragraph("\n"));

        // ================= DONATION MESSAGE =================

        outerCell.add(new Paragraph(
                "Aarine Foundation gratefully acknowledges the generous contribution received from Mr. Aashtik Aakash Jadav.")
                .setBold());

        outerCell.add(new Paragraph(""));

        outerCell.add(new Paragraph("Donor Details:").setBold());
        outerCell.add(new Paragraph("Address: XXXX"));
        outerCell.add(new Paragraph("Email: XXXX"));
        outerCell.add(new Paragraph("Contact No.: 8421159692"));
        outerCell.add(new Paragraph("PAN No.: XXXX"));

        outerCell.add(new Paragraph(""));

        outerCell.add(new Paragraph(
                "We sincerely thank you for your kind donation of ₹100/- (Rupees One Hundred Only)."));

        outerCell.add(new Paragraph(
                "Your generous support will help us continue our efforts towards social welfare and community development. "
              + "We truly appreciate your commitment to making a positive difference in the lives of those in need."));

        outerCell.add(new Paragraph("\n\n"));

        outerCell.add(new Paragraph("Authorised Sign.")
                .setTextAlignment(TextAlignment.RIGHT));

        outerCell.add(new Paragraph("\n\n"));

        // ================= THANK YOU LETTER =================

        outerCell.add(new Paragraph("Thank You Letter")
                .setBold()
                .setFontSize(13)
                .setTextAlignment(TextAlignment.CENTER));

        outerCell.add(new Paragraph("\n"));

        outerCell.add(new Paragraph(
                "Aarine Foundation is a Government Registered organization working for the welfare of Women & Children since 2017. "
              + "We continuously support initiatives in Education, Health, Youth, Poverty Alleviation, Livelihood and Community Development.\n\n"
              + "Your donation will help us run Skill Development Centers, Community Development initiatives for the weaker sections of society."
        ));

        outerCell.add(new Paragraph("\n"));

        outerCell.add(new Paragraph(
                "Your donation is eligible for 50% tax benefit under section 80G of the Income Tax Act.")
                .setBold());

        outerCell.add(new Paragraph("\n"));

        outerCell.add(new Paragraph(
                "Mobile No.: +91 2262515119\n" +
                "Email: aarine.foundation@yahoo.com\n" +
                "Website: https://aarine.in\n\n" +
                "Date: 16/02/2026")
                .setFontSize(10));

        // ================= ADD OUTER BORDER =================
        outerTable.addCell(outerCell);
        document.add(outerTable);

        document.add(
                new Paragraph("Powered by Datfuslab Technologies Pvt. Ltd.")
                        .setFontSize(9)
                        .setFontColor(ColorConstants.GRAY)
                        .setTextAlignment(TextAlignment.LEFT)
        );

        document.close();
        return baos.toByteArray();
    }
}
