package com.school.pdf;

import java.io.InputStream;

import org.springframework.stereotype.Component;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

@Component
public class AdmissionFormPdf {

    // =====================================================
    // MAIN METHOD
    // =====================================================
    public void generate(Document document) {

        // ===== HEADER =====
        addSchoolHeader(document);

        document.add(new Paragraph("\n"));

        document.add(new Paragraph("ADMISSION FORM (2023 – 2024)")
                .setBold()
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER));

        // ===== STUDENT DETAILS =====
        document.add(sectionTitle("STUDENT DETAILS"));

        // --- Student details table (LEFT) ---
        Table studentTable = new Table(4).useAllAvailableWidth();

        addCell(studentTable, "Admission No");    addCell(studentTable, "2");
        addCell(studentTable, "Admission Date"); addCell(studentTable, "08-Feb-2024");

        addCell(studentTable, "Student Name");   addCell(studentTable, "PARI KUMARI");
        addCell(studentTable, "Roll No");        addCell(studentTable, "6");

        addCell(studentTable, "Class");          addCell(studentTable, "SECOND");
        addCell(studentTable, "Section");        addCell(studentTable, "A");

        addCell(studentTable, "Date of Birth");  addCell(studentTable, "01-Jan-2019");
        addCell(studentTable, "Gender");         addCell(studentTable, "GIRL");

        addCell(studentTable, "Category");       addCell(studentTable, "OBC");
        addCell(studentTable, "Religion");       addCell(studentTable, "HINDU");

        addCell(studentTable, "Nationality");    addCell(studentTable, "INDIAN");
        addCell(studentTable, "Blood Group");    addCell(studentTable, "NA");

        addCell(studentTable, "Aadhaar No");     addCell(studentTable, "111111111111");
        addCell(studentTable, "Birth Place");    addCell(studentTable, "-");

        // --- Wrapper table: details + photo ---
        Table studentSection = new Table(new float[]{75, 25})
                .useAllAvailableWidth();

        studentSection.addCell(new Cell()
                .add(studentTable)
                .setBorder(null));

        studentSection.addCell(studentPhotoCell());

        document.add(studentSection);

        // ===== PARENT DETAILS =====
        document.add(sectionTitle("PARENT / GUARDIAN DETAILS"));

        Table parentTable = new Table(2).useAllAvailableWidth();

        addCell(parentTable, "Father's Name");  addCell(parentTable, "NIRANJAN KUMAR");
        addCell(parentTable, "Mother's Name");  addCell(parentTable, "KHUSHBOO KUMAR");
        addCell(parentTable, "Mobile Number");  addCell(parentTable, "9507003539");
        addCell(parentTable, "Address");        addCell(parentTable, "AIGARI MATH");

        document.add(parentTable);

        // ===== DECLARATION =====
        document.add(sectionTitle("DECLARATION"));

        document.add(new Paragraph(
                "I hereby declare that the above information provided is true and correct. " +
                "I agree to abide by all rules and regulations of the school.")
                .setFontSize(9));

        // ===== SIGNATURES =====
        Table signTable = new Table(3)
                .useAllAvailableWidth()
                .setMarginTop(20);

        signTable.addCell(signCell("Parent / Guardian Signature"));
        signTable.addCell(signCell("Student Signature"));
        signTable.addCell(signCell("Principal Signature\nWith Seal"));

        document.add(signTable);
    }

    // =====================================================
    // HEADER (LOGO FROM CLASSPATH)
    // =====================================================
    private void addSchoolHeader(Document document) {

        Table headerTable = new Table(new float[]{15, 70, 15});
        headerTable.useAllAvailableWidth();
        headerTable.setBorder(new SolidBorder(1));

        // LOGO (LEFT)
        Cell logoCell = new Cell()
                .setBorder(null)
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);

        try {
            InputStream is = getClass()
                    .getClassLoader()
                    .getResourceAsStream("images/cia.png");

            if (is == null) {
                throw new RuntimeException("Logo not found: images/cia.png");
            }

            ImageData imageData = ImageDataFactory.create(is.readAllBytes());
            Image logo = new Image(imageData)
                    .setWidth(70)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER);

            logoCell.add(logo);
        } catch (Exception e) {
            logoCell.add(new Paragraph(""));
        }

        // CENTER TEXT
        Cell centerCell = new Cell()
                .setBorder(null)
                .setTextAlignment(TextAlignment.CENTER);

        centerCell.add(new Paragraph("CHAMPARAN INTERNATIONAL ACADEMY")
                .setBold()
                .setFontSize(16)
                .setFontColor(ColorConstants.RED));

        centerCell.add(new Paragraph(
                "Masjid Tola, Near Batakh Miya Mazar, Bajrang Chowk, Agari")
                .setFontSize(10)
                .setFontColor(ColorConstants.BLUE));

        centerCell.add(new Paragraph(
                "Contact: 7321978565 | Email: champaraninternationalacademy@gmail.com")
                .setFontSize(10)
                .setFontColor(ColorConstants.GREEN));

        // STUDENT COPY (RIGHT)
        Cell copyCell = new Cell()
                .setBorder(null)
                .setTextAlignment(TextAlignment.RIGHT)
                .setVerticalAlignment(VerticalAlignment.TOP);

//        copyCell.add(new Paragraph("STUDENT COPY")
//                .setBold()
//                .setFontSize(9));

        headerTable.addCell(logoCell);
        headerTable.addCell(centerCell);
        headerTable.addCell(copyCell);

        document.add(headerTable);
    }

    // =====================================================
    // PHOTO CELL (JUST BELOW STUDENT DETAILS)
    // =====================================================
    private Cell studentPhotoCell() {
        return new Cell()
                .add(new Paragraph("\n\nSTUDENT\nPHOTOGRAPH")
                        .setFontSize(9)
                        .setTextAlignment(TextAlignment.CENTER))
                .setHeight(80)
                .setBorder(new SolidBorder(0.8f))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
    }

    // =====================================================
    // HELPERS
    // =====================================================
    private Paragraph sectionTitle(String title) {
        return new Paragraph(title)
                .setBold()
                .setFontSize(11)
                .setMarginTop(15)
                .setMarginBottom(5);
    }

    private void addCell(Table table, String text) {
        table.addCell(new Cell()
                .add(new Paragraph(text).setFontSize(9))
                .setBorder(new SolidBorder(0.7f))
                .setPadding(5));
    }

    private Cell signCell(String text) {
        return new Cell()
                .add(new Paragraph("\n\n" + text)
                        .setFontSize(9)
                        .setTextAlignment(TextAlignment.CENTER))
                .setHeight(80)
                .setBorder(new SolidBorder(0.8f));
    }
}
