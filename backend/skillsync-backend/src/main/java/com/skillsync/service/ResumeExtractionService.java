
package com.skillsync.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

@Service
public class ResumeExtractionService {

    public String extractText(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please upload a resume file.");
        }

        String filename = file.getOriginalFilename();

        if (filename == null) {
            throw new IllegalArgumentException(
                    "The uploaded file must have a filename.");
        }

        String lowerFilename = filename.toLowerCase(Locale.ROOT);

        if (lowerFilename.endsWith(".pdf")) {

            try (PDDocument document =
                         Loader.loadPDF(file.getBytes())) {

                PDFTextStripper stripper = new PDFTextStripper();
                String text = stripper.getText(document);

                return validateExtractedText(text);
            }

        } else if (lowerFilename.endsWith(".docx")) {

            try (InputStream inputStream = file.getInputStream();
                 XWPFDocument document =
                         new XWPFDocument(inputStream);
                 XWPFWordExtractor extractor =
                         new XWPFWordExtractor(document)) {

                String text = extractor.getText();

                return validateExtractedText(text);
            }

        } else {
            throw new IllegalArgumentException(
                    "Unsupported file type. Upload a PDF or DOCX file.");
        }
    }

    private String validateExtractedText(String text) {

        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(
                    "No readable text found. Please upload a text-based PDF or DOCX resume.");
        }

        return text.trim();
    }
}
