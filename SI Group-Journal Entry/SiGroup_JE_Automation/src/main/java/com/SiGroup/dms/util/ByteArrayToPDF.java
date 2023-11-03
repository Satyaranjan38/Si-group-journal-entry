package com.SiGroup.dms.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class ByteArrayToPDF {

	
	 public static void convertToPDF(byte[] byteArray, String outputFilePath) {
	        try {
	            Document document = new Document();
	            PdfWriter.getInstance(document, new FileOutputStream(outputFilePath));
	            document.open();
	            document.add(new Paragraph(new String(byteArray)));
	            document.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	   
}
