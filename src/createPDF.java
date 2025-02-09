
import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.Rectangle;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@SuppressWarnings("deprecation")
public class createPDF extends PdfWriter {
    Document document;
    File file;
    PdfContentByte contentByte;
    DefaultFontMapper mapper;
    int width = (int) PageSize.A4.getWidth(); //width of PDF document page
    int height = (int) (PageSize.A4.getHeight()); //height of PDF document page
    createPDF(Document document, File file) throws FileNotFoundException, DocumentException {
        //initialising attributes and creating output stream
        this.document = document;
        this.file = file;
        PdfWriter writer = PdfWriter.getInstance(this.document, new FileOutputStream(file.getAbsolutePath() + ".pdf"));
        this.document.open();
        contentByte = writer.getDirectContent();
        mapper = new DefaultFontMapper();
        FontFactory.registerDirectories();
    }
    public void pdfTable(JTable table, String header) throws DocumentException {
        PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
        ColumnText.showTextAligned(contentByte, Element.ALIGN_CENTER, new Phrase(header), 100, 810, 0);
        //adding table headers
        for (int columns = 0; columns < table.getColumnCount(); columns++) {
            pdfTable.addCell(table.getColumnName(columns));
        }
        //extracting data from the JTable and inserting it to PdfPTable
        for (int rows = 0; rows < table.getRowCount(); rows++) {
            for (int cols = 0; cols < table.getColumnCount(); cols++) {
                pdfTable.addCell(table.getModel().getValueAt(rows, cols).toString());

            }
        }
        document.add(pdfTable);
        document.newPage();
    }
    public void pdfGraph(JFreeChart chart, String header) {
        //drawing jfreechart object as a graphics 2d object
        ColumnText.showTextAligned(contentByte, Element.ALIGN_CENTER, new Phrase(header), 100, 810, 0);
        PdfTemplate pdT = contentByte.createTemplate(width, height);
        Graphics2D g2d = pdT.createGraphics(width, height, mapper);
        chart.draw(g2d, new Rectangle(width,height));
        contentByte.addTemplate(pdT, 0, 0);
        g2d.dispose();
        document.newPage();
    }
}
