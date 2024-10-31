package com.doc.conversion;

import com.doc.conversion.fop.GeneratePdf;
import com.doc.conversion.xml.XMLUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Service
public class ApplicationRunnerService {
    private final GeneratePdf generatePdf;
    private final XMLUtil xmlUtil;

    public ApplicationRunnerService(GeneratePdf generatePdf, XMLUtil xmlUtil) {
        this.generatePdf = generatePdf;
        this.xmlUtil = xmlUtil;
    }

    public void convertXmlToPdf() throws IOException {
        final String outputPdfPath = "/Users/zeus/Downloads/temp/output.pdf";
        final String outputFoPath = "/Users/zeus/Downloads/temp/output.fo";
        final String outputXmlPath = "/Users/zeus/Downloads/temp/output.xml";

        // Create XML object and get InputStream
        System.out.println("about to create xml file");
        InputStream inputStream = xmlUtil.getInputStream(xmlUtil.createXmlObject());
        System.out.println("xml file created");

        // Generate FO and PDF from XML
        System.out.println("generating xml file in fo");
        OutputStream foOutputStream = generatePdf.generateFo(inputStream);
        System.out.println("generating xml file in pdf");
        OutputStream pdfOutputStream = generatePdf.generatePdf(inputStream);

        // Write outputs to files
        FileUtils.writeByteArrayToFile(new File(outputPdfPath), ((ByteArrayOutputStream) pdfOutputStream).toByteArray());
        FileUtils.writeByteArrayToFile(new File(outputFoPath), ((ByteArrayOutputStream) foOutputStream).toByteArray());
        FileUtils.writeByteArrayToFile(new File(outputXmlPath), IOUtils.toByteArray(inputStream));

        // Closing streams (if necessary, depending on your implementation)
        foOutputStream.close();
        pdfOutputStream.close();
        inputStream.close();
    }

    // Example method remains unchanged for converting FO examples to PDF
    private void convertFoExamplesToPdf() {
        final List<String> advanced = Arrays.asList(
                "advanced/barcode", "advanced/man", "advanced/man"
        );

        final List<String> basics = Arrays.asList(
                "basic/barcode", "basic/man", "basic/man"
        );

        final List<String> pagination = Arrays.asList(
                "pagination/barcode", "pagination/man", "pagination/man"
        );

        final List<String> tables = Arrays.asList(
                "tables/barcode", "tables/man", "tables/man"
        );

        advanced.forEach(generatePdf::createPdfByFoFile);
        basics.forEach(generatePdf::createPdfByFoFile);
        pagination.forEach(generatePdf::createPdfByFoFile);
        tables.forEach(generatePdf::createPdfByFoFile);
    }
}
