package ARL.tesi.util;


import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PDFUtility {


    @Autowired
    protected ServletContext context;




    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font redFont =
            new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private Font calibriBold33;
    private Font calibriBold20;
    private Font calibriBold22;
    private Font calibriBold14;
    private Font calibriBold10;
    private Font calibriBold8;
    private int page = 1;
    private Map<Integer, String> pageIndex = new HashMap<Integer, String>();
    private float pointMMCoefficent;



    List<PagePDF> pages = new ArrayList<>();

    public List<PagePDF> getPages() {
        return pages;
    }

    public void setPages(List<PagePDF> pages) {
        this.pages = pages;
    }

    public ServletContext getContext() {
        return context;
    }

    public void setContext(ServletContext context) {
        this.context = context;
    }

    public static Font getCatFont() {
        return catFont;
    }

    public static void setCatFont(Font catFont) {
        PDFUtility.catFont = catFont;
    }

    public static Font getRedFont() {
        return redFont;
    }

    public static void setRedFont(Font redFont) {
        PDFUtility.redFont = redFont;
    }

    public static Font getSubFont() {
        return subFont;
    }

    public static void setSubFont(Font subFont) {
        PDFUtility.subFont = subFont;
    }

    public static Font getSmallBold() {
        return smallBold;
    }

    public static void setSmallBold(Font smallBold) {
        PDFUtility.smallBold = smallBold;
    }

    public Font getCalibriBold33() {
        return calibriBold33;
    }

    public void setCalibriBold33(Font calibriBold33) {
        this.calibriBold33 = calibriBold33;
    }

    public Font getCalibriBold20() {
        return calibriBold20;
    }

    public void setCalibriBold20(Font calibriBold20) {
        this.calibriBold20 = calibriBold20;
    }

    public Font getCalibriBold22() {
        return calibriBold22;
    }

    public void setCalibriBold22(Font calibriBold22) {
        this.calibriBold22 = calibriBold22;
    }

    public Font getCalibriBold14() {
        return calibriBold14;
    }

    public void setCalibriBold14(Font calibriBold14) {
        this.calibriBold14 = calibriBold14;
    }

    public Font getCalibriBold8() {
        return calibriBold8;
    }


    public Font getCalibriBold10() {
        return calibriBold10;
    }

    public void setCalibriBold10(Font calibriBold10) {
        this.calibriBold10 = calibriBold10;
    }

    public void setCalibriBold8(Font calibriBold8) {
        this.calibriBold8 = calibriBold8;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }


    public Map<Integer, String> getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Map<Integer, String> pageIndex) {
        this.pageIndex = pageIndex;
    }

    float getPointMMCoefficent() {
        return pointMMCoefficent;
    }

    void setPointMMCoefficent(float pointMMCoefficent) {
        this.pointMMCoefficent = pointMMCoefficent;
    }

    void setUpCalibriFont(BaseFont base) {
        calibriBold33 = new Font(base, 33f);
        calibriBold14 = new Font(base, 14f);
        calibriBold20 = new Font(base, 20f);
        calibriBold22 = new Font(base, 21f);
        calibriBold10 = new Font(base, 10f);
        calibriBold8 = new Font(base, 8f);
    }

    void setUpTimesFont() {
        calibriBold33 = new Font(Font.FontFamily.TIMES_ROMAN, 29, Font.BOLD);
        calibriBold14 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
        calibriBold20 = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
        calibriBold22 = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        calibriBold8 = new Font(Font.FontFamily.TIMES_ROMAN, 4, Font.BOLD);
        calibriBold10 = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.BOLD);
    }

    public class PdfHeader extends PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            /// top yellow band ///
            PdfContentByte canvasTop = writer.getDirectContentUnder();
            canvasTop.saveState();
            canvasTop.setColorFill(new BaseColor(246, 231, 0));
            canvasTop.rectangle(0, document.getPageSize().getHeight() - 20 * pointMMCoefficent,
                    document.getPageSize().getWidth(), 20 * pointMMCoefficent);
            canvasTop.fill();
            canvasTop.restoreState();
            ///////////////////////
            /// top title ///
            Rectangle rectTop = new Rectangle(75.3f * pointMMCoefficent, 277f * pointMMCoefficent,
                    197.3f * pointMMCoefficent, 287f * pointMMCoefficent);
            PdfContentByte cbTop = writer.getDirectContent();
            cbTop.setColorFill(new BaseColor(0, 0, 0));
            Phrase phraseTop = new Phrase("GFK Assembly-Kit-System", calibriBold33);
            ColumnText.showTextAligned(cbTop, Element.ALIGN_LEFT, phraseTop, rectTop.getLeft(),
                    rectTop.getBottom(), 0);
            cbTop.saveState();
            cbTop.rectangle(rectTop.getLeft(), rectTop.getBottom(), rectTop.getWidth(),
                    rectTop.getHeight());
            cbTop.restoreState();
        }
    }

    public File getPathFile(String path) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext();
        Resource resource = appContext.getResource("classpath:static/" + path);
        File file = null;
        try {
            file = resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((ConfigurableApplicationContext) appContext).close();
        return file;
    }

    File getContextPathFile(String path) {
        String filePath = context.getRealPath("/");
        if (filePath.endsWith("/"))
            return new File(filePath + path);
        else
            return new File(filePath + File.separator + path);
    }

    void addCoverPage(Document document, PdfWriter writer) {


//
//        rexLogoImg.scaleAbsolute((int) (46 * pointMMCoefficent), (int) (13.25 * pointMMCoefficent));
//        rexLogoImg.setAbsolutePosition((int) (152.3 * pointMMCoefficent),
//                (int) (258.75 * pointMMCoefficent));
//        swisscrossImg.scaleAbsolute((int) (55 * pointMMCoefficent), (int) (15.25 * pointMMCoefficent));
//        swisscrossImg.setAbsolutePosition((int) (75.3 * pointMMCoefficent),
//                (int) (257.75 * pointMMCoefficent));
//        workplaceImg.scaleAbsolute(document.getPageSize().getWidth(),
//                document.getPageSize().getHeight() - 148.5f * pointMMCoefficent);
//        workplaceImg.setAlignment(Element.ALIGN_CENTER);
//        workplaceImg.setAbsolutePosition(0, 0);
//        complete3DModelImg.scaleAbsolute(document.getPageSize().getWidth(),
//                document.getPageSize().getHeight() - 120f * pointMMCoefficent);
//        complete3DModelImg.setAlignment(Element.ALIGN_CENTER);
//        complete3DModelImg.setAbsolutePosition(25f * pointMMCoefficent, 90f * pointMMCoefficent);
        PdfContentByte canvas2 = writer.getDirectContentUnder();
        canvas2.saveState();
        PdfGState state = new PdfGState();
        state.setFillOpacity(0.5f);
        canvas2.setGState(state);
//        try {
//            canvas2.addImage(workplaceImg);
//        } catch (DocumentException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
        canvas2.restoreState();
        /// Work orderDescription ///
        // Rectangle rectTop = new Rectangle(75.3f * pointMMCoefficent, 277f * pointMMCoefficent, 197.3f
        /// * pointMMCoefficent, 287f * pointMMCoefficent);
        PdfContentByte cbDescription = writer.getDirectContent();
        cbDescription.setColorFill(new BaseColor(0, 0, 0));
        Phrase companyName = new Phrase("ciao", calibriBold20);
        Phrase projectDescription = new Phrase("ciao", calibriBold20);
        Phrase projectCode = new Phrase("ciao", calibriBold20);
        //edit by Roberto Bignasca getId() al posto di getNumber()
        Phrase projectDate = new Phrase("ciao",calibriBold14);
        ColumnText.showTextAligned(cbDescription, Element.ALIGN_LEFT, companyName,
                75.3f * pointMMCoefficent, 241.75f * pointMMCoefficent, 0);
        ColumnText.showTextAligned(cbDescription, Element.ALIGN_LEFT, projectDescription,
                75.3f * pointMMCoefficent, 241.75f * pointMMCoefficent - 24, 0);
        ColumnText.showTextAligned(cbDescription, Element.ALIGN_LEFT, projectCode,
                75.3f * pointMMCoefficent, 241.75f * pointMMCoefficent - 48, 0);
        ColumnText.showTextAligned(cbDescription, Element.ALIGN_LEFT, projectDate,
                75.3f * pointMMCoefficent, 241.75f * pointMMCoefficent - 72, 0);
        cbDescription.saveState();
        cbDescription.rectangle(75.3f * pointMMCoefficent, 241.75f * pointMMCoefficent,
                197.3f * pointMMCoefficent, 287f * pointMMCoefficent);
        cbDescription.restoreState();
        /////////////////
        /// bottom yellow band ///
        PdfContentByte canvas3 = writer.getDirectContentUnder();
        canvas3.saveState();
        canvas3.setColorFill(new BaseColor(246, 231, 0));
        canvas3.rectangle(0, 0, document.getPageSize().getWidth(), 30 * pointMMCoefficent);
        PdfGState state2 = new PdfGState();
        state2.setFillOpacity(0.5f);
        canvas3.setGState(state2);
        canvas3.fill();
        canvas3.restoreState();
        ////////////////////////////
        /// footer information title ///
        PdfContentByte cb2 = writer.getDirectContentUnder();
        cb2.setColorFill(new BaseColor(0, 0, 0));
        // Rectangle rect2 = new Rectangle(12.7f * pointMMCoefficent, 15f * pointMMCoefficent, 200f *
        // pointMMCoefficent, 21f * pointMMCoefficent);
        // rect2.setBackgroundColor(new BaseColor(0, 0, 0));
        Phrase phrase2 =
                new Phrase("Rex Articoli Tecnici SA | Via Catenazzi 1 | CH-6850 Mendrisio", calibriBold14);
        ColumnText.showTextAligned(cb2, Element.ALIGN_LEFT, phrase2, 12.7f * pointMMCoefficent,
                15f * pointMMCoefficent, 0);
        cb2.saveState();
        cb2.rectangle(12.7f * pointMMCoefficent, 15f * pointMMCoefficent, 200f * pointMMCoefficent,
                21f * pointMMCoefficent);
        cb2.newPath();
        cb2.restoreState();
        PdfContentByte cb3 = writer.getDirectContentUnder();
        cb3.setColorFill(new BaseColor(0, 0, 0));
        // Rectangle rect3 = new Rectangle(12.7f * pointMMCoefficent, 7f * pointMMCoefficent, 200f *
        // pointMMCoefficent, 18f * pointMMCoefficent);
        // rect3.setBackgroundColor(new BaseColor(0, 0, 0));
        Phrase phrase3 = new Phrase(
                "www.rex.ch | sales@rex.ch | t +41 91 640 50 50 | f +41 91 640 50 55", calibriBold14);
        ColumnText.showTextAligned(cb3, Element.ALIGN_LEFT, phrase3, 12.7f * pointMMCoefficent,
                7f * pointMMCoefficent, 0);
        cb3.saveState();
        cb3.rectangle(12.7f * pointMMCoefficent, 7f * pointMMCoefficent, 200f * pointMMCoefficent,
                18f * pointMMCoefficent);
        cb3.newPath();
        cb3.restoreState();
        //////////////////////////////////////////
//        try {
//            document.add(swisscrossImg);
//            document.add(rexLogoImg);
//            document.add(complete3DModelImg);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
        document.newPage(); // viene chiamato il metodo onEndPage di pdfHeader
    }

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }




    void emptyCell(PdfPTable table){

        int ROW_HEIGHT = (14);
        PdfPCell cell = new PdfPCell(new Phrase( "" ));
        cell.setFixedHeight(ROW_HEIGHT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
    }

    static void addMetaData(Document document) {
        document.addTitle("Rotazione ");
        document.addAuthor("ARL");
        document.addCreator("Admin");
    }

    class PdfStandardFooter extends PdfPageEventHelper {
        Font ffont = new Font(Font.FontFamily.HELVETICA, 9, Font.ITALIC);
        Font hfont = new Font(Font.FontFamily.HELVETICA, 11, Font.ITALIC);
        public void onEndPage(PdfWriter writer, Document document) {
            if (page != 1) {
                /// bottom yellow band ///
                PdfContentByte canvas3 = writer.getDirectContentUnder();
                canvas3.saveState();
                canvas3.setColorFill(new BaseColor(246, 231, 0));
                canvas3.rectangle(0, 0, document.getPageSize().getWidth(), 30 * pointMMCoefficent);
                PdfGState state2 = new PdfGState();
                state2.setFillOpacity(0.5f);
                canvas3.setGState(state2);
                canvas3.fill();
                canvas3.restoreState();
                ////////////////////////////
                /// footer information table /////////
                //////// first cell ///////
                Rectangle rect2 = new Rectangle(12.7f * pointMMCoefficent, 20f * pointMMCoefficent,
                        65f * pointMMCoefficent, 28f * pointMMCoefficent);
                PdfContentByte cb2 = writer.getDirectContentUnder();
                cb2.setColorFill(new BaseColor(0, 0, 0));
                Phrase articleNrLabel = new Phrase("Nr. Art.:", calibriBold8);
                Phrase articleNrVal = new Phrase("ciao", calibriBold8);
                ColumnText.showTextAligned(cb2, Element.ALIGN_LEFT, articleNrLabel,
                        13.7f * pointMMCoefficent, 24f * pointMMCoefficent, 0);
                ColumnText.showTextAligned(cb2, Element.ALIGN_LEFT, articleNrVal, 13.7f * pointMMCoefficent,
                        21f * pointMMCoefficent, 0);
                //////// page number //////
                Phrase pageNumber = new Phrase("pag. " + page, calibriBold14);
                ColumnText.showTextAligned(cb2, Element.ALIGN_LEFT, pageNumber, 180f * pointMMCoefficent,
                        35f * pointMMCoefficent, 0);
                cb2.saveState();
                cb2.rectangle(rect2.getLeft(), rect2.getBottom(), rect2.getWidth(), rect2.getHeight());
                cb2.setColorStroke(new BaseColor(0, 0, 0));
                cb2.stroke();
                cb2.restoreState();
                ////////////////////////////
                //////// second cell ///////
                Phrase NameNrModLabel = new Phrase("Name/Nr. Mod.:", calibriBold8);
                Phrase NameNrModVal = new Phrase("ciao", calibriBold8);
                ColumnText.showTextAligned(cb2, Element.ALIGN_LEFT, NameNrModLabel, 66f * pointMMCoefficent,
                        24f * pointMMCoefficent, 0);
                ColumnText.showTextAligned(cb2, Element.ALIGN_LEFT, NameNrModVal, 66f * pointMMCoefficent,
                        21f * pointMMCoefficent, 0);
                cb2.saveState();
                cb2.rectangle(65f * pointMMCoefficent, 20f * pointMMCoefficent, 30.76f * pointMMCoefficent,
                        8f * pointMMCoefficent);
                cb2.setColorStroke(new BaseColor(0, 0, 0));
                cb2.stroke();
                cb2.restoreState();
                ///////////////////////////////
                //////// third cell ///////////
                Phrase NrArtRexLabel = new Phrase("Nr. Art. Rex:", calibriBold8);
                Phrase NrArtRexVal = new Phrase("ciao", calibriBold8);
                ColumnText.showTextAligned(cb2, Element.ALIGN_LEFT, NrArtRexLabel,
                        96.76f * pointMMCoefficent, 24f * pointMMCoefficent, 0);
                ColumnText.showTextAligned(cb2, Element.ALIGN_LEFT, NrArtRexVal, 96.76f * pointMMCoefficent,
                        21f * pointMMCoefficent, 0);
                cb2.saveState();
                cb2.rectangle(95.76f * pointMMCoefficent, 20f * pointMMCoefficent,
                        30.76f * pointMMCoefficent, 8f * pointMMCoefficent);
                cb2.setColorStroke(new BaseColor(0, 0, 0));
                cb2.stroke();
                cb2.restoreState();
                ///////////////////////////////
                //////// fourth cell ///////////
                String nrSupply = String.valueOf(1111);
                Phrase NrSupplyLabel = new Phrase("Nr. Supply:", calibriBold8);
                Phrase NrSupplyVal = new Phrase(nrSupply, calibriBold8);
                ColumnText.showTextAligned(cb2, Element.ALIGN_LEFT, NrSupplyLabel,
                        127.52f * pointMMCoefficent, 24f * pointMMCoefficent, 0);
                ColumnText.showTextAligned(cb2, Element.ALIGN_LEFT, NrSupplyVal,
                        127.52f * pointMMCoefficent, 21f * pointMMCoefficent, 0);
                cb2.saveState();
                cb2.rectangle(126.52f * pointMMCoefficent, 20f * pointMMCoefficent, 40f * pointMMCoefficent,
                        8f * pointMMCoefficent);
                cb2.setColorStroke(new BaseColor(0, 0, 0));
                cb2.stroke();
                cb2.restoreState();
                ///////////////////////////////
                //////// fifth cell ///////////
                Phrase NrOrderPRojLabel = new Phrase("Nr. Order/Project:", calibriBold8);
                Phrase NrOrderPRojVal = new Phrase("ciao", calibriBold8);
                ColumnText.showTextAligned(cb2, Element.ALIGN_LEFT, NrOrderPRojLabel,
                        167.52f * pointMMCoefficent, 24f * pointMMCoefficent, 0);
                ColumnText.showTextAligned(cb2, Element.ALIGN_LEFT, NrOrderPRojVal,
                        167.52f * pointMMCoefficent, 21f * pointMMCoefficent, 0);
                cb2.saveState();
                cb2.rectangle(166.52f * pointMMCoefficent, 20f * pointMMCoefficent,
                        30.6f * pointMMCoefficent, 8f * pointMMCoefficent);
                cb2.setColorStroke(new BaseColor(0, 0, 0));
                cb2.stroke();
                cb2.restoreState();
                ///////////////////////////////
                //////// sixth cell ///////////
                Phrase ClientLabel = new Phrase("Client:", calibriBold8);
                Phrase ClientVal =
                        new Phrase("ciao", calibriBold8);
                ColumnText.showTextAligned(cb2, Element.ALIGN_LEFT, ClientLabel, 13.7f * pointMMCoefficent,
                        16f * pointMMCoefficent, 0);
                ColumnText.showTextAligned(cb2, Element.ALIGN_LEFT, ClientVal, 35f * pointMMCoefficent,
                        16f * pointMMCoefficent, 0);
                Phrase ProjectNameLabel = new Phrase("Project Name:", calibriBold8);
                Phrase ProjectNameVal = new Phrase("ciao", calibriBold8);
                ColumnText.showTextAligned(cb2, Element.ALIGN_LEFT, ProjectNameLabel,
                        13.7f * pointMMCoefficent, 13f * pointMMCoefficent, 0);
                ColumnText.showTextAligned(cb2, Element.ALIGN_LEFT, ProjectNameVal, 35f * pointMMCoefficent,
                        13f * pointMMCoefficent, 0);
                cb2.saveState();
                cb2.rectangle(12.7f * pointMMCoefficent, 12f * pointMMCoefficent,
                        document.getPageSize().getWidth() - 2 * 12.7f * pointMMCoefficent,
                        8f * pointMMCoefficent);
                cb2.setColorStroke(new BaseColor(0, 0, 0));
                cb2.stroke();
                cb2.restoreState();
                ///////////////////////////////
                //////// seventh cell ///////////
                Phrase rexDetails = new Phrase(
                        "REX ARTICOLI TECNICI SA | Via Catenazzi 1 | CH-Mendrisio | t +41 91 640 50 50 | f +41 91 640 50 55 | sales@rex.ch",
                        calibriBold8);
                ColumnText.showTextAligned(cb2, Element.ALIGN_LEFT, rexDetails, 13.7f * pointMMCoefficent,
                        8f * pointMMCoefficent, 0);
                cb2.saveState();
                cb2.rectangle(12.7f * pointMMCoefficent, 6f * pointMMCoefficent,
                        document.getPageSize().getWidth() - 2 * 12.7f * pointMMCoefficent,
                        6f * pointMMCoefficent);
                cb2.setColorStroke(new BaseColor(0, 0, 0));
                cb2.stroke();
                cb2.restoreState();
                ///////////////////////////////
            }
            if (page > 4) {
                float firstBlockTopPosition = 100;
                float secondBlockTopPosition = 185;
                /// middle yellow bands ///
                PdfContentByte canvasMiddle1 = writer.getDirectContentUnder();
                canvasMiddle1.saveState();
                canvasMiddle1.setColorFill(new BaseColor(246, 231, 0));
                canvasMiddle1.rectangle(0,
                        document.getPageSize().getHeight() - firstBlockTopPosition * pointMMCoefficent,
                        document.getPageSize().getWidth(), 1 * pointMMCoefficent);
                canvasMiddle1.fill();
                canvasMiddle1.restoreState();
                PdfContentByte canvasMiddle2 = writer.getDirectContentUnder();
                canvasMiddle2.saveState();
                canvasMiddle2.setColorFill(new BaseColor(246, 231, 0));
                canvasMiddle2.rectangle(0,
                        document.getPageSize().getHeight() - secondBlockTopPosition * pointMMCoefficent,
                        document.getPageSize().getWidth(), 1 * pointMMCoefficent);
                canvasMiddle2.fill();
                canvasMiddle2.restoreState();
                ///////////////////////
            }
            page++;
        }
    }
}
