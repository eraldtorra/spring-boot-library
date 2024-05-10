package com.code.springbootlibrary.controller;

import com.code.springbootlibrary.service.PdfService;
import com.code.springbootlibrary.utils.ExtractJWT;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@CrossOrigin("https://localhost:3000")
public class PdfController {

    private PdfService pdfService;

    @Autowired
    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }






    @GetMapping(value = "/generatePdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public void generatePdf(HttpServletResponse response,
                            @RequestHeader(value = "Authorization") String token
                            ) throws DocumentException, IOException {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        ByteArrayOutputStream out = pdfService.writeHistoryToPdf(userEmail);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=history.pdf");
        response.getOutputStream().write(out.toByteArray());
    }


//    create table forum
//            (
//                    id           bigint auto_increment
//                    primary key,
//                    content      varchar(255) null,
//    posted_by    varchar(255) null,
//    posting_date datetime     null,
//    posted_in    int          null
//            );
//create table threads
//            (
//                    id            bigint auto_increment
//                    primary key,
//                    title         varchar(255) null,
//    created_by    varchar(255) null,
//    creation_date datetime     null
//            );

}
