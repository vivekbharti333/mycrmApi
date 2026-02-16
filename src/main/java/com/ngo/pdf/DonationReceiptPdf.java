package com.ngo.pdf;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Component;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;

@Component
public class DonationReceiptPdf {

    public byte[] generateInvoice() throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // ------------------ HEADER ------------------
        document.add(new Paragraph("Aarine Foundation")
                .setBold()
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph(
                "Registration No.: E-34254(M), PAN NO.: AAHTA5687L\n" +
                "Off. Add: 102 First floor Gaondevi Krupa Building Opposite Ghansoli Post Office,\n" +
                "Ghansoli Gaon Navi Mumbai - 400701\n" +
                "Reg. Add: C-6/1, Transit Camp, Kokari Agar, Antop Hill Sion Mumbai-400037")
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("\n"));

        // ------------------ TITLE ------------------
        document.add(new Paragraph("Donation Receipt")
                .setBold()
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("\n"));

        // ------------------ RECEIPT DETAILS ------------------
        document.add(new Paragraph()
                .add(new Text("Receipt No: ").setBold())
                .add("PZ/80G/E/022026/42739"));

        document.add(new Paragraph("\n"));

        document.add(new Paragraph(
                "Aarine Foundation is thankful to Aashtik Aakash Jadav\n" +
                "Address: XXXX\n" +
                "Email: XXXX\n" +
                "Contact No: 8421159692\n" +
                "PAN No: XXXX\n\n" +
                "for kind donation of Rs: 100/- (One Hundred Only in INR) for Donation.")
        );

        document.add(new Paragraph("\n\n"));

        document.add(new Paragraph("Authorised Sign.")
                .setTextAlignment(TextAlignment.RIGHT));

        document.add(new Paragraph("\n\n"));

        // ------------------ THANK YOU LETTER ------------------
        document.add(new Paragraph("Thank You Letter")
                .setBold()
                .setFontSize(13)
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("\n"));

        document.add(new Paragraph(
                "Aarine Foundation is Govt. Registered organization working for Welfare of Women & Children since 2017. "
              + "We are continuously supporting in the field of Education, Health, Youth, Poverty, Livelihood and "
              + "Community Development. Our aim is to make every individual in the Society self-dependent and raise "
              + "the quality of life in every aspect.\n\n"
              + "Your Donation would help us to run the Skill Development Center, Digital Education Center and "
              + "Community Development Center for weaker sections of our society.\n\n"
              + "Your donation will go a long way to inspire people to donate to the NGO who are putting in their best "
              + "efforts to help the less fortunate people. Donors like you have harnessed the potential of our young "
              + "staff and encouraged us to work with sincerity and commitment.\n\n"
              + "We are enclosing a receipt against your donation, along with this letter. We wish to have a long term "
              + "relationship and good trust with you to serve society.\n\n"
              + "Thank you for your support. Keep supporting us."
        ));

        document.add(new Paragraph("\n"));

        document.add(new Paragraph(
                "Your Donation is eligible for 50% tax benefit under section 80G of Income Tax Act.")
                .setBold());

        document.add(new Paragraph("\n"));

        document.add(new Paragraph(
                "Mobile No.: +91 2262515119\n" +
                "Email: aarine.foundation@yahoo.com\n" +
                "Website: https://aarine.in\n\n" +
                "Date: 16/02/2026")
                .setFontSize(10));

        document.close();

        return baos.toByteArray();
    }
}
