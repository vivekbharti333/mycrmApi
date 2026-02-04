package com.common.pdf;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;

class InvoiceFooterHandler implements IEventHandler {

    private PdfFont font;

    public InvoiceFooterHandler(PdfFont font) {
        this.font = font;
    }

    @Override
    public void handleEvent(Event event) {

        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdf = docEvent.getDocument();
        int page = pdf.getNumberOfPages();

        Document document = new Document(pdf);

        DeviceRgb grey = new DeviceRgb(140,140,140);

        // Left footer
//        Paragraph left = new Paragraph("Powered by Datfuslab Technologies")
//                .setFont(font)
//                .setFontSize(8)
//                .setFontColor(grey);
        
        Image footerLogo;
		try {
			
			InputStream is = getClass().getClassLoader().getResourceAsStream("images/dfllogo.png");
			footerLogo = new Image(ImageDataFactory.create(is.readAllBytes()));


			footerLogo.setWidth(110);

			// move logo UP a little to match text baseline
			footerLogo.setRelativePosition(0, 2, 0, 0);   // ← key line

			footerLogo.setMarginLeft(6);
			footerLogo.setMarginRight(6);

			Paragraph left = new Paragraph()
			        .add("POWERED BY ")
			        .add(footerLogo)
			        .setFont(font)
			        .setFontSize(8)
			        .setFontColor(grey);

        left.setFixedPosition(page, 36, 20, 300);
        document.add(left);

        // Right footer (page number)
        Paragraph right = new Paragraph("Page " + page)
                .setFont(font)
                .setFontSize(8)
                .setFontColor(grey)
                .setTextAlignment(TextAlignment.RIGHT);

        right.setFixedPosition(page, 400, 20, 150);
        document.add(right);
        
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     // space between logo and text
 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
