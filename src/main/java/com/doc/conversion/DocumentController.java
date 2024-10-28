package com.doc.conversion;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/convert/")
public class DocumentController {
    private final DocumentService documentService;
    @GetMapping
    public void convertDocuments() throws IOException {
        String inputPath = "/Users/zeus/Downloads/MOCK_HELLO_FREESH.docx";
        CompletableFuture<String> result = documentService.convertWordToPDF(inputPath);

        result.thenAccept(path -> {
            if (path.contains("Conversion failed")) {
                System.err.println("Conversion error: " + path);
            } else {
                System.out.println("PDF saved to: " + path);
            }
        });
    }
}
