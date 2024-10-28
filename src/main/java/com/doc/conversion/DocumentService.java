package com.doc.conversion;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;


@Service
@Slf4j
public class DocumentService {

    @Async("conversionExecutor")
    public CompletableFuture<String> convertWordToPDF(String inputFilePath) throws IOException {
        Path inputFile = Path.of(inputFilePath);
        File outputDir = new File("/Users/zeus/Downloads/temp");

        if (!outputDir.canWrite()) {
            return CompletableFuture.completedFuture("Output directory is not writable.");
        }

        if (!Files.exists(inputFile) || Files.size(inputFile) == 0) {
            return CompletableFuture.completedFuture("File not found or empty.");
        }

        String outputFilePath = outputDir + "/conversion_" + UUID.randomUUID() + ".pdf";
        Path outputFile = Path.of(outputFilePath);

        try (InputStream docFile = Files.newInputStream(inputFile);
             XWPFDocument doc = new XWPFDocument(docFile);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            PdfOptions pdfOptions = PdfOptions.create();
            log.debug("Starting conversion to PDF");
            PdfConverter.getInstance().convert(doc, outputStream, pdfOptions);
            log.debug("PDF conversion step complete");

           // Files.write(outputFile, outputStream.toByteArray());
            System.out.println("converted successfully and writing into file");
            addPasswordProtection(outputFilePath, "password");
            System.out.println("adding password protection success");

            log.info("PDF conversion completed for {}", inputFilePath);
            System.out.println("PDF conversion completed for " + inputFilePath);
            return CompletableFuture.completedFuture(outputFilePath);
        } catch (Exception e) {
            log.error("An error occurred during PDF conversion: {}", e.getMessage(), e);
            return CompletableFuture.completedFuture("Conversion failed.");
        }
    }

    private void addPasswordProtection(String pdfFilePath, String password) throws IOException {
        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
            AccessPermission accessPermission = new AccessPermission();
            StandardProtectionPolicy policy = new StandardProtectionPolicy(password, password, accessPermission);

            policy.setEncryptionKeyLength(128);
            policy.setPermissions(accessPermission);
            document.protect(policy);

            document.save(pdfFilePath);
        }
    }
}
