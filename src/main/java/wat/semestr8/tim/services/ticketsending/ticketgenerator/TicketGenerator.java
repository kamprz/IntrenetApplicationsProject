package wat.semestr8.tim.services.ticketsending.ticketgenerator;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import wat.semestr8.tim.entities.Purchase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import static wat.semestr8.tim.services.ticketsending.ticketgenerator.MapValues.*;


public class TicketGenerator {

    private Document document;
    private float bottom;
    private static final String FONT = "./font/FreeSans.ttf";
    private static final String FONTBOLD = "./font/FreeSansBold.ttf";
    private static final String LOGO = "./src/main/resources/logo.jpg";
    private static final String pdfPath = "./ticket.pdf";

    private PdfFont font;
    private PdfFont fontBold;

    public TicketGenerator(Purchase purchase) throws IOException
    {
        //Open PDF file
        PdfWriter pdfWriter = null;
        try {
            pdfWriter = new PdfWriter(pdfPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document = new Document(new PdfDocument(pdfWriter));

        //Set fonts
        try {
            fontBold = PdfFontFactory.createFont(FONTBOLD, PdfEncodings.IDENTITY_H);
            font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Set starting bottom value
        bottom = 550;
    }

    private void putImage() {
        // Creating an ImageData object
        ImageData data = null;
        try {
            data = ImageDataFactory.create(LOGO);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Creating an Image object
        Image image = new Image(data).setFixedPosition(100,650);

        // Adding image to the document
        document.add(image);
    }

    private void putString(String value) {
        // Creating Paragraphs
        Paragraph paragraph1 = new Paragraph(value);
        paragraph1.setFont(font).setFixedPosition(50, bottom, 500);

        document.add(paragraph1);

        bottom-=15;
    }
    
    private void putMap(Map<String, String> map) {
        if (map.get(CONCERT) != null) putString("Koncert: " + map.get(CONCERT));
        if (map.get(DATE) != null) putString("Data: " + map.get(DATE));
        putString("");
        if (map.get(ROW) != null) putString("Rząd: " + map.get(ROW));
        if (map.get(POSITION) != null) putString("Miejsce: " + map.get(POSITION));
        putString("");
        if (map.get(PRICE) != null) putString("Cena: " + map.get(PRICE));
        if (map.get(DISCOUNT) != null) putString("Zniżka: " + map.get(DISCOUNT));
    }

    private void close() {
        // Closing the document
        document.close();
    }

    private void putEnding() {
        // Creating text object
        Text text1 = new Text("Życzymy miłego wieczoru!");

        // Creating Paragraph
        Paragraph paragraph1 = new Paragraph()
                .setFont(fontBold)
                .setFontSize(20)
                .setBold()
                .setFixedPosition(150, 350, 500)
                .add(text1);

        // Adding paragraphs to the document
        document.add(paragraph1);
    }

    private void putBegging() {
        // Creating text object
        Text text1 = new Text("Bilet");

        // Creating Paragraph
        Paragraph paragraph1 = new Paragraph()
                .setFont(fontBold)
                .setFontSize(30)
                .setBold()
                .setFixedPosition(260, 600, 500)
                .add(text1);

        // Adding paragraphs to the document
        document.add(paragraph1);
    }
    private void putAreaBreak() {
        // Adding area break to the PDF
        document.add(new AreaBreak());

        //Set starting bottom value
        bottom = 550;
    }

    private void generateTicket (Map map) {
        putImage();
        putBegging();
        putMap(map);
        putEnding();
    }

    public void generateTickets(List<Map> maps) {
        for (int i = 0; i < maps.size(); i++) {
            generateTicket(maps.get(i));
            if (i < maps.size()-1) putAreaBreak();
            else close();
        }
    }

    public String getPdfPath() {
        return pdfPath;
    }
}
