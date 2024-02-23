package com.spring.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


@Component
public class PdfThankYouLatter {
	

    private static String FILE = "D:\\FirstPdf.pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    public void pdf() {
        try {
            Document document = new Document();
            document.setMargins(20, 20, 20, 20);
            document.setMarginMirroringTopBottom(true);
            document.setMarginMirroring(true);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            
            // Draw margin border
            PdfContentByte canvas = writer.getDirectContent();
            float marginLeft = document.leftMargin();
            float marginRight = document.rightMargin();
            float marginTop = document.topMargin();
            float marginBottom = document.bottomMargin();
            canvas.rectangle(
                marginLeft, 
                marginBottom, 
                document.getPageSize().getWidth() - marginLeft - marginRight, 
                document.getPageSize().getHeight() - marginTop - marginBottom
            );
            canvas.stroke();
            
            addTitlePage(document);
            addContent(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTitlePage(Document document)
            throws DocumentException, IOException {
        Paragraph preface = new Paragraph();
        

        
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.getDefaultCell().setBackgroundColor(BaseColor.YELLOW);

        // Set width for each cell
        float[] columnWidths = {1f, 1f, 1f}; // Adjust the values as needed
        table.setWidths(columnWidths);
        
        PdfPCell rightCell = new PdfPCell();
        rightCell.setBorder(PdfPCell.NO_BORDER);
        rightCell.setBackgroundColor(BaseColor.YELLOW);
        Image img = Image.getInstance("C:\\Users\\HP\\Pictures\\Screenshots\\123.png");
        img.setAlignment(Element.ALIGN_CENTER);
        img.scaleToFit(100, 100);
        rightCell.addElement(img);
        rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

     // Add the PdfPCell to the table
     table.addCell(rightCell);
        
     
//     PdfPCell centerCell = new PdfPCell();
//     centerCell.setBorder(PdfPCell.NO_BORDER);
//     centerCell.setBackgroundColor(BaseColor.YELLOW);
//
//     Phrase phrase = new Phrase();

     PdfPCell centerCell = new PdfPCell();
     centerCell.setBorder(PdfPCell.NO_BORDER);
     centerCell.setBackgroundColor(BaseColor.YELLOW);

     Phrase phrase = new Phrase();

     // Create a chunk with larger text
     Chunk chunkBig = new Chunk("This is left aligned", FontFactory.getFont(FontFactory.HELVETICA, 18));
     chunkBig.setUnderline(0.1f, -2f); // example: underlining the text
     phrase.add(chunkBig);

     phrase.add(Chunk.NEWLINE);

     // Insert smaller text
     Chunk chunkSmall = new Chunk("text ghjkg", FontFactory.getFont(FontFactory.HELVETICA, 10)); // Inserting spaces before the text
     phrase.add(chunkSmall);
     
     
     phrase.add(Chunk.NEWLINE);
     // Insert smaller text
     Chunk thirdLine = new Chunk("just for testing", FontFactory.getFont(FontFactory.HELVETICA, 10)); // Inserting spaces before the text
     phrase.add(thirdLine);

     // Add the Phrase to the PdfPCell
     centerCell.addElement(phrase);

     // Set alignment of the text within the cell
     centerCell.setHorizontalAlignment(Element.ALIGN_CENTER); // Horizontal alignment
     centerCell.setVerticalAlignment(Element.ALIGN_MIDDLE); // Vertical alignment

     // Set the fixed height of the cell
     centerCell.setFixedHeight(60); // Adjust the height as needed

     // Add the PdfPCell to the table
     table.addCell(centerCell);

     
       
        PdfPCell leftCell = new PdfPCell();
        Paragraph paragraph12 = new Paragraph("This is left aligned text this is");
        leftCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        leftCell.setBorder(PdfPCell.NO_BORDER);
        leftCell.setBackgroundColor(BaseColor.YELLOW);
        leftCell.addElement(paragraph12);
        table.addCell(leftCell);
        

        
        
        
        
        

        document.add(table);
        
        
        
        
        
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Title of the document", catFont));

        addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date
        preface.add(new Paragraph(
                "Report generated by: " + System.getProperty("user.name") + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallBold));
        addEmptyLine(preface, 3);
        preface.add(new Paragraph(
                "This document describes something which is very important ",
                smallBold));

        addEmptyLine(preface, 12);

        preface.add(new Paragraph(
                "This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).",
                redFont));

        document.add(preface);
        // Start a new page
        document.newPage();
    }

    private static void addContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("First Chapter", catFont);
        anchor.setName("First Chapter");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

        Paragraph subPara = new Paragraph("Subcategory 1", subFont);
        Section subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Hello"));

        subPara = new Paragraph("Subcategory 2", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Paragraph 1"));
        subCatPart.add(new Paragraph("Paragraph 2"));
        subCatPart.add(new Paragraph("Paragraph 3"));

        // add a list
        createList(subCatPart);
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 5);
        subCatPart.add(paragraph);

        // add a table
        createTable(subCatPart);

        // now add all this to the document
        document.add(catPart);

        // Next section
        anchor = new Anchor("Second Chapter", catFont);
        anchor.setName("Second Chapter");

        // Second parameter is the number of the chapter
        catPart = new Chapter(new Paragraph(anchor), 1);

        subPara = new Paragraph("Subcategory", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("This is a very important message"));

        // now add all this to the document
        document.add(catPart);

    }

    private static void createTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 2"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 3"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");

        subCatPart.add(table);

    }

    private static void createList(Section subCatPart) {
        List list = new List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
	
}
