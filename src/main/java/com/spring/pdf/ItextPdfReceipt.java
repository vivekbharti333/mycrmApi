package com.spring.pdf;

import org.springframework.stereotype.Component;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.geom.PageSize;

import java.io.File;

@Component
public class ItextPdfReceipt {

	    public void createReceipt() {
	        String dest = "D:/pdf_with_border.pdf";

	        try {
	            // Create PdfWriter and PdfDocument
	            PdfWriter writer = new PdfWriter(new File(dest));
	            PdfDocument pdfDoc = new PdfDocument(writer);
	            Document document = new Document(pdfDoc, PageSize.A4);

	            // Add a page
	            pdfDoc.addNewPage();

	            // Get the canvas to draw the border
	            PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());

	            // Set margin for border
	            float margin = 20f;
	            float width = pdfDoc.getDefaultPageSize().getWidth();
	            float height = pdfDoc.getDefaultPageSize().getHeight();

	            // Draw rectangle (border)
	            canvas.setLineWidth(2f);
	            canvas.rectangle(margin, margin, width - 2 * margin, height - 2 * margin);
	            canvas.stroke();

	            // Add some content inside the document
	            document.setMargins(40, 40, 40, 40);
	            document.add(new Paragraph("This PDF has a border on all four sides."));

	            document.close();
	            System.out.println("PDF created: " + dest);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }


}
