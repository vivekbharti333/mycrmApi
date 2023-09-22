package com.spring.common;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class PageBackgroundAndMarginsEvent extends PdfPageEventHelper{
	@Override
    public void onEndPage(PdfWriter writer, Document document) {
        try {
			// Set the page background color to pink
			PdfContentByte canvas = writer.getDirectContentUnder();
			Rectangle rect = document.getPageSize();
			canvas.setColorFill(BaseColor.PINK);
			canvas.rectangle(rect.getLeft(), rect.getBottom(), rect.getWidth(), rect.getHeight());
			canvas.fill();

			// Set equal margin border lines (10 points) from all four sides
			PdfContentByte content = writer.getDirectContent();
			content.setColorStroke(BaseColor.BLACK);
			float margin = 1; // 10 points
			content.setLineWidth(margin);
			content.rectangle(
			        document.leftMargin() - margin,  // left
			        document.bottomMargin() - margin, // bottom
			        document.right() - document.leftMargin() + 2 * margin, // width
			        document.top() - document.bottom() + 2 * margin // height
			);
			content.stroke();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}



