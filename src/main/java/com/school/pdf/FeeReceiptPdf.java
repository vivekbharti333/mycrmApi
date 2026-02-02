package com.school.pdf;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class FeeReceiptPdf {

    private static final float HALF_A4_HEIGHT = 410f; // safe half height

    public void generate(Document document) throws Exception {

        document.setMargins(10, 10, 10, 10);

        // ===== STUDENT COPY =====
        document.add(createReceipt("STUDENT COPY"));

        // ===== CUT LINE =====
        document.add(new Paragraph(
                "----------------------------- CUT HERE -----------------------------")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(9)
                .setMarginTop(4)
                .setMarginBottom(4));

        // ===== OFFICE COPY =====
        document.add(createReceipt("OFFICE COPY"));
    }

    // ==================================================
    // ONE RECEIPT (HALF A4)
    // ==================================================
    private Table createReceipt(String copyLabel) throws Exception {

        Table outer = new Table(1);
        outer.setWidth(UnitValue.createPercentValue(100));
        outer.setMinHeight(HALF_A4_HEIGHT);
        outer.setBorder(new SolidBorder(1.5f));

        Cell outerCell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setPadding(6);

        // ===== COPY LABEL =====
        outerCell.add(new Paragraph(copyLabel)
                .setFontSize(9)
                .setTextAlignment(TextAlignment.RIGHT));

        // ===== HEADER =====
        Table header = new Table(new float[]{1, 4});
        header.setWidth(UnitValue.createPercentValue(100));
        header.setBorderBottom(new SolidBorder(1));

        InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("images/cia.png");

        if (is == null) {
            throw new RuntimeException("Logo not found: images/cia.png");
        }

        ImageData imageData = ImageDataFactory.create(is.readAllBytes());
        Image logo = new Image(imageData).scaleToFit(55, 55);

        header.addCell(new Cell().add(logo).setBorder(Border.NO_BORDER));

        Cell school = new Cell()
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.CENTER);

        school.add(new Paragraph("CHAMPARAN INTERNATIONAL ACADEMY")
                .setBold().setFontSize(16).setFontColor(ColorConstants.RED));

        school.add(new Paragraph(
                "Masjid Tola, Near Batakh Miya Mazar, Bajrang Chowk, Agari")
                .setFontSize(10).setFontColor(ColorConstants.BLUE));

        school.add(new Paragraph(
                "Contact: 7321978565 | Email: champaraninternationalacademy@gmail.com")
                .setFontSize(10).setFontColor(ColorConstants.GREEN));

        header.addCell(school);
        outerCell.add(header);

        // ===== TITLE =====
        outerCell.add(new Paragraph("FEE RECEIPT")
                .setBold()
                .setFontSize(11)
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(new SolidBorder(1))
                .setMarginTop(6)
                .setMarginBottom(6)
                .setPadding(3));

        // ===== DETAILS =====
        Table details = new Table(3)
                .setWidth(UnitValue.createPercentValue(100));

        details.addCell(nb("BILL NO: B/2526-00145"));
        details.addCell(nb("MONTH: JULY, AUG, SEP, OCT"));
        details.addCell(nb("DATE: 29-12-2025"));

        details.addCell(nb("NAME: RISHU KUMAR"));
        details.addCell(nb("CLASS: FOURTH (A)"));
        details.addCell(nb("ADM/ROLL NO: 43 / 3"));

        details.addCell(nb("FATHER: NIRAJ KUMAR"));
        details.addCell(nb("MOTHER: GEETA DEVI"));
        details.addCell(nb("CONTACT NO: 6202014654"));

        outerCell.add(details);

        // ===== FEE TABLE =====
        Table feeTable = new Table(new float[]{5, 2, 2, 2})
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginTop(6);

        feeTable.addHeaderCell(hc("Fee Head"));
        feeTable.addHeaderCell(hc("Amount"));
        feeTable.addHeaderCell(hc("Concession"));
        feeTable.addHeaderCell(hc("Total"));

        row(feeTable, "Old Balance", "100", "0", "100");
        row(feeTable, "Exam Fee", "150", "0", "150");
        row(feeTable, "Late Fine", "200", "0", "200");
        row(feeTable, "Monthly Fee", "2000", "0", "2000");

        feeTable.addCell(new Cell().add(new Paragraph("Total").setBold())
                .setBorder(Border.NO_BORDER).setFontSize(10));
        feeTable.addCell(rb("2450"));
        feeTable.addCell(rb("0"));
        feeTable.addCell(rb("2450"));

        outerCell.add(feeTable);

        // ===== FOOTER =====
        outerCell.add(new Paragraph(
                "Paid: 2450    Discount: 0    Dues: 0    Mode: Cash")
                .setFontSize(10)
                .setBorderTop(new SolidBorder(1))
                .setMarginTop(6)
                .setPaddingTop(4));

        // ===== SIGNATURE =====
        outerCell.add(new Paragraph("\n(Authorized Signature)")
                .setFontSize(10)
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginTop(14));

        outer.addCell(outerCell);
        return outer;
    }

    // ================= HELPERS =================
    private static Cell nb(String text) {
        return new Cell()
                .add(new Paragraph(text).setFontSize(10))
                .setBorder(Border.NO_BORDER);
    }

    private static Cell rb(String text) {
        return new Cell()
                .add(new Paragraph(text).setBold().setFontSize(10))
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.RIGHT);
    }

    private static Cell hc(String text) {
        return new Cell()
                .add(new Paragraph(text).setBold().setFontSize(11))
                .setBackgroundColor(ColorConstants.BLACK)
                .setFontColor(ColorConstants.WHITE)
                .setTextAlignment(TextAlignment.CENTER);
    }

    private static void row(Table t, String a, String b, String c, String d) {
        t.addCell(nb(a));
        t.addCell(nb(b).setTextAlignment(TextAlignment.RIGHT));
        t.addCell(nb(c).setTextAlignment(TextAlignment.RIGHT));
        t.addCell(nb(d).setTextAlignment(TextAlignment.RIGHT));
    }
}
