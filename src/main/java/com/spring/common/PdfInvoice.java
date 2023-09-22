package com.spring.common;

import org.opencv.objdetect.CascadeClassifier;
import org.springframework.stereotype.Component;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Scalar;

import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

@Component
public class PdfInvoice extends PdfPageEventHelper{
	

	public void pdfDonationInvoice() {

		String fileName = "D://itext.pdf";

		try {
			Document document = new Document(PageSize.A4);

			// Create a PdfWriter instance to write to the PDF file
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));

			// Create a custom PdfPageEvent to set the background color and margins
			PageBackgroundAndMarginsEvent event = new PageBackgroundAndMarginsEvent();
			writer.setPageEvent(event);

			// Open the document for writing
			document.open();

			// Create content for the PDF
			Paragraph content = new Paragraph("This is the content of the PDF.");

			document.add(content);

			// Close the document
			document.close();

			System.out.println("PDF with pink background color and equal margin border lines created successfully in " + fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
 


}
