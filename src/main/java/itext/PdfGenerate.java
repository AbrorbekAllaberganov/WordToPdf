package itext;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PdfGenerate {
    public void pdfGenerate(String text,String uploadPdf){
        Document document = new Document();
        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(uploadPdf));
            document.open();
            document.add(new Paragraph(text));
            document.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
