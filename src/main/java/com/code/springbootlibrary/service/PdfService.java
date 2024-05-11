package com.code.springbootlibrary.service;

import com.code.springbootlibrary.dao.HistoryRepository;
import com.code.springbootlibrary.entity.History;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDiv;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.text.pdf.PdfPCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;

import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.Phaser;

@Service
@Transactional
public class PdfService {

    private HistoryRepository historyRepository;

    @Autowired
    public PdfService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }


    public ByteArrayOutputStream writeHistoryToPdf(String userEmail) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        List<History> historyList = historyRepository.findByUserEmail(userEmail);

        PdfWriter.getInstance(document, out);
        document.open();

        // add title to pdf set to center
        Font font = new Font(Font.FontFamily.HELVETICA, 20);
        Paragraph title = new Paragraph("History of books", font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // 2 empty lines
        document.add(new Paragraph("\n"));



        for (History h : historyList) {

            // Create a table with 2 columns
            PdfPTable mainTable = new PdfPTable(2);


            try {
                String base64Image = h.getImg().split(",")[1];
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                Image img = Image.getInstance(imageBytes);
                img.scaleAbsolute(190, 290);
                img.setAlignment(Element.ALIGN_CENTER);
                PdfPCell imageCell = new PdfPCell(img);
                imageCell.setBorder(Rectangle.NO_BORDER);
                mainTable.addCell(imageCell);
            } catch (Exception e) {
                System.out.println("Error adding image: " + e.getMessage());
            }

            PdfPTable infoTable = new PdfPTable(1);

            Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Phrase titlePhrase = new Phrase(h.getTitle(), boldFont);
            PdfPCell titleCell = new PdfPCell(titlePhrase);
            titleCell.setBorder(Rectangle.NO_BORDER);
            infoTable.addCell(titleCell);


            Font autherFont = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.ITALIC);
            Phrase autherPhrase = new Phrase(h.getAuthor(),autherFont);
            PdfPCell authorCell = new PdfPCell(autherPhrase);
            authorCell.setBorder(Rectangle.NO_BORDER);
            infoTable.addCell(authorCell);

            Font DecFont = new Font(Font.FontFamily.TIMES_ROMAN,10);
            Phrase DecPhrase = new Phrase(h.getDescription(),DecFont);
            PdfPCell descriptionCell = new PdfPCell(DecPhrase);
            descriptionCell.setBorder(Rectangle.NO_BORDER);
            infoTable.addCell(descriptionCell);

            Font ChecDateFont = new Font(Font.FontFamily.TIMES_ROMAN,11);
            Phrase CheckDatePhrase = new Phrase(h.getCheckoutDate(),ChecDateFont);
            PdfPCell checkoutDateCell = new PdfPCell(CheckDatePhrase);
            checkoutDateCell.setBorder(Rectangle.NO_BORDER);
            infoTable.addCell(checkoutDateCell);


            Phrase DatePhrase = new Phrase(h.getReturnedDate(),ChecDateFont);
            PdfPCell returnDateCell = new PdfPCell(DatePhrase);
            returnDateCell.setBorder(Rectangle.NO_BORDER);
            infoTable.addCell(returnDateCell);


            PdfPCell infoCell = new PdfPCell(infoTable);
            infoCell.setBorder(Rectangle.NO_BORDER);
            mainTable.addCell(infoCell);

            document.add(mainTable);
            document.add(new Paragraph("\n\n"));


        }

        document.close();
        return out;
    }

}
