package com.code.springbootlibrary.service;

import com.code.springbootlibrary.dao.HistoryRepository;
import com.code.springbootlibrary.entity.History;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDiv;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;

import java.net.URL;
import java.util.Base64;
import java.util.List;

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
        document.add(new Paragraph("\n\n"));



        for (History h : historyList) {


            // add image with url
            try {
                String base64Image = h.getImg().split(",")[1];
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                Image img = Image.getInstance(imageBytes);
                img.scaleAbsolute(200, 300);
                img.setAlignment(Element.ALIGN_CENTER);
                document.add(img);

            } catch (Exception e) {
                System.out.println("Error adding image: " + e.getMessage());
            }
            Paragraph BookTitle = new Paragraph(h.getTitle());
            BookTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(BookTitle);

            // add author
            Paragraph Author = new Paragraph(h.getAuthor());
            Author.setAlignment(Element.ALIGN_CENTER);
            document.add(Author);

            // add description
            Paragraph Description = new Paragraph(h.getDescription());
            Description.setAlignment(Element.ALIGN_LEFT);
            document.add(Description);

            // add date
            Paragraph Date = new Paragraph(h.getCheckoutDate());
            Date.setAlignment(Element.ALIGN_CENTER);
            document.add(Date);


           Paragraph DateReturn = new Paragraph(h.getReturnedDate());
            DateReturn.setAlignment(Element.ALIGN_CENTER);
            document.add(DateReturn);

            // 2 empty lines
            document.add(new Paragraph("\n\n"));


        }

        document.close();
        return out;
    }

}
