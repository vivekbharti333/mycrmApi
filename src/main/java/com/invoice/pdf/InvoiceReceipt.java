package com.invoice.pdf;

import com.common.entities.InvoiceHeaderDetails;
import com.invoice.object.request.InvoiceItemRequest;
import com.invoice.object.request.InvoiceRequestObject;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

@Component
public class InvoiceReceipt {

    public static void generateInvoicePdf(
            InvoiceRequestObject invoice,
            String filePath, InvoiceHeaderDetails invoiceHeaderDetails ) throws Exception {

        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        
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


        // ===================== TITLE =====================
        Paragraph title = new Paragraph("INVOICE", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph invNo = new Paragraph("# " + invoice.getInvoiceNumber(), bold);
        invNo.setAlignment(Element.ALIGN_CENTER);
        document.add(invNo);

        document.add(Chunk.NEWLINE);

        // ===================== COMPANY + LOGO + DATE SUMMARY =====================
        PdfPTable companyTable = new PdfPTable(2);
        companyTable.setWidthPercentage(100);
        companyTable.setWidths(new int[]{60, 40});
        companyTable.setSpacingAfter(10);

        PdfPCell companyCell = new PdfPCell();
        companyCell.setBorder(Rectangle.NO_BORDER);

        Image logo = Image.getInstance("C://invoices/logo.png");
        logo.scaleToFit(120, 60);
        companyCell.addElement(logo);
        companyCell.addElement(new Paragraph(" "));

        Paragraph company = new Paragraph();

        company.add(new Chunk(invoiceHeaderDetails.getCompanyFirstName() + " " + invoiceHeaderDetails.getCompanyLastName() + "\n", bold));

        company.add(new Chunk("GSTIN : 09AAKCD5557C1ZJ\n", normal));
        company.add(new Chunk("1507-B12, Sector-16B, Greater Noida\n", normal));
        company.add(new Chunk("Website : https://datfuslab.com\n", normal));
        company.add(new Chunk("Email : info@datfuslab.com\n", normal));
        company.add(new Chunk("Mobile : +91-7004063385\n", normal));

        companyCell.addElement(company);

        PdfPCell summaryCell = new PdfPCell();
        summaryCell.setBorder(Rectangle.NO_BORDER);

        PdfPTable summaryTable = new PdfPTable(2);
        summaryTable.setWidthPercentage(100);

        addSummaryRow(summaryTable, "Date", sdf.format(invoice.getInvoiceDate()), bold, normal);
        addSummaryRow(summaryTable, "Due Date", sdf.format(invoice.getDueDate()), bold, normal);
        addSummaryRow(summaryTable, "Balance Due", "₹ " + invoice.getTotalAmount(), bold, bold);

        summaryCell.addElement(summaryTable);

        companyTable.addCell(companyCell);
        companyTable.addCell(summaryCell);
        document.add(companyTable);

        // ===================== BILL TO + DELIVERY =====================
        PdfPTable addressTable = new PdfPTable(2);
        addressTable.setWidthPercentage(100);

        PdfPCell billToCell = new PdfPCell();
        billToCell.setBorder(Rectangle.NO_BORDER);

        Paragraph billTo = new Paragraph("Bill To:\n", bold);
        billTo.add("MYRAAN RENTALS AND ADVENTURES GOA (OPC)\n");
        billTo.add("PRIVATE LIMITED\n");
        billTo.add("HOUSE NO. 71/195/A\n");
        billTo.add("North Goa, Goa - 403506\n");
        billTo.add("GSTIN : 30AASCM2597H1Z\n");
        billToCell.addElement(billTo);

        PdfPCell deliveryCell = new PdfPCell();
        deliveryCell.setBorder(Rectangle.NO_BORDER);

        Paragraph delivery = new Paragraph("Delivery Address:\n", bold);
        delivery.add("MYRAAN RENTALS AND ADVENTURES GOA (OPC)\n");
        delivery.add("PRIVATE LIMITED\n");
        delivery.add("HOUSE NO. 71/195/A\n");
        delivery.add("North Goa, Goa - 403506\n");
        delivery.add("GSTIN : 30AASCM2597H1Z\n");
        deliveryCell.addElement(delivery);

        addressTable.addCell(billToCell);
        addressTable.addCell(deliveryCell);
        document.add(addressTable);

        document.add(Chunk.NEWLINE);

        // ===================== ITEMS TABLE =====================
        PdfPTable items = new PdfPTable(5);
        items.setWidthPercentage(100);
        items.setWidths(new int[]{10, 50, 10, 20, 15, 15});

        // ===== BLACK HEADER ROW =====
        addBlackHeader(items, "S. No.", headerWhite);
        addBlackHeader(items, "Item", headerWhite);
        addBlackHeader(items, "Qty", headerWhite);
        addBlackHeader(items, "Rate", headerWhite);
        addBlackHeader(items, "Tax", headerWhite);
        addBlackHeader(items, "Amount", headerWhite);


        BigDecimal subtotal = BigDecimal.ZERO;
        int serialNo = 10;

        for (InvoiceItemRequest item : invoice.getItems()) {

            PdfPCell snCell = new PdfPCell(new Phrase(String.valueOf(serialNo++), normal));
            snCell.setBorder(Rectangle.NO_BORDER);
            snCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            snCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            snCell.setPaddingTop(6f);
            items.addCell(snCell);

            Paragraph itemPara = new Paragraph();
            itemPara.add(new Chunk(item.getProductName() + "\n", normal));
            if (item.getDescription() != null) {
                itemPara.add(new Chunk(item.getDescription(), lightGraySmall));
            }

            PdfPCell itemCell = new PdfPCell();
            itemCell.setBorder(Rectangle.NO_BORDER);
            itemCell.addElement(itemPara);
            items.addCell(itemCell);

            items.addCell(noBorderCell(String.valueOf(item.getQuantity()), Element.ALIGN_CENTER, normal));
            items.addCell(noBorderCell("₹ " + item.getRate(), Element.ALIGN_CENTER, normal));
            items.addCell(noBorderCell("₹ " + item.getRate(), Element.ALIGN_CENTER, normal));
            items.addCell(noBorderCell("₹ " + item.getAmount(), Element.ALIGN_CENTER, normal));

            subtotal = subtotal.add(item.getAmount());
        }

        document.add(items);
        document.add(Chunk.NEWLINE);

        // ===================== BOLD HORIZONTAL LINE =====================        
        LineSeparator line = new LineSeparator();
        line.setLineWidth(2f);          // bold line
        line.setPercentage(100);        // ✅ FULL WIDTH
        line.setAlignment(Element.ALIGN_CENTER);
        document.add(line);
        document.add(Chunk.NEWLINE);


        // ===================== TOTALS =====================
        BigDecimal tax = subtotal.multiply(new BigDecimal("0.18"));

        PdfPTable totals = new PdfPTable(2);
        totals.setWidthPercentage(40);
        totals.setHorizontalAlignment(Element.ALIGN_RIGHT);

        addSummaryRow(totals, "Subtotal", "₹ " + subtotal, bold, normal);
        addSummaryRow(totals, "CGST (9%)", "₹ " + tax, bold, normal);
        addSummaryRow(totals, "SGST (9%)", "₹ " + tax, bold, normal);

        // 🔥 EXTRA BOLD TOTAL
        addTotalRow(totals, "Total Amount", "₹ " + invoice.getTotalAmount(), totalBold);


        document.add(totals);
        document.add(Chunk.NEWLINE);

        document.add(new Paragraph(
                "Notes:\nThank you for your business. Please review all details carefully.", normal));
        document.close();
    }

    // ===================== HELPERS =====================

    private static PdfPCell noBorderCell(String text, int align, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(align);
        return cell;
    }

    
 // 🔥 BLACK HEADER CELL
    private static void addBlackHeader(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(BaseColor.BLACK);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(8);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
    }

    private static void addSummaryRow(
            PdfPTable table,
            String label,
            String value,
            Font labelFont,
            Font valueFont) {

        PdfPCell c1 = new PdfPCell(new Phrase(label, labelFont));
        PdfPCell c2 = new PdfPCell(new Phrase(value, valueFont));

        c1.setBorder(Rectangle.NO_BORDER);
        c2.setBorder(Rectangle.NO_BORDER);
        c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        c1.setPaddingTop(4f);     // ✅ vertical spacing
        c1.setPaddingBottom(4f);

        c2.setPaddingTop(4f);     // ✅ vertical spacing
        c2.setPaddingBottom(4f);

        table.addCell(c1);
        table.addCell(c2);
    }
    
    private static void addTotalRow(
            PdfPTable table,
            String label,
            String value,
            Font font) {

        PdfPCell c1 = new PdfPCell(new Phrase(label, font));
        PdfPCell c2 = new PdfPCell(new Phrase(value, font));

        c1.setBorder(Rectangle.TOP);
        c2.setBorder(Rectangle.TOP);

        c1.setBorderWidthTop(1.5f);   // bold top line
        c2.setBorderWidthTop(1.5f);

        c1.setPaddingTop(8f);        // extra spacing
        c1.setPaddingBottom(8f);
        c2.setPaddingTop(8f);
        c2.setPaddingBottom(8f);

        c2.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(c1);
        table.addCell(c2);
    }

    
  

}
