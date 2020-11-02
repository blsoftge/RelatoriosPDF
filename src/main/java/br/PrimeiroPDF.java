package br;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class PrimeiroPDF {

    public PrimeiroPDF(String fraseAImprimir) {

        Document documentPDF = new Document();

        try {
            PdfWriter.getInstance(documentPDF, new FileOutputStream("Relatorio1.pdf"));

            documentPDF.open();

            Paragraph paragrafoTeste = new Paragraph(fraseAImprimir);

            documentPDF.add(paragrafoTeste);

        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        documentPDF.close();

    }

}
