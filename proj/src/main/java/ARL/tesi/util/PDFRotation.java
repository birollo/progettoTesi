package ARL.tesi.util;


import ARL.tesi.modelobject.User;
import com.itextpdf.text.*;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PDFRotation extends PDFUtility{



    public PDFRotation(){
        BaseFont base = null;
        try {
            base = BaseFont.createFont(getPathFile("fonts/calibrib.ttf").getPath(), BaseFont.WINANSI,
                    BaseFont.EMBEDDED);
        } catch (DocumentException | IOException e1) {
            e1.printStackTrace();
        }
        if (base != null)
            setUpCalibriFont(base);
        else
            setUpTimesFont();

    }


    public String createRotationPDF(ServletContext context, Date date, List<User> users){
        String pathFile;
        //edit by Roberto Bignasca
        setPage(1);

        //estrapolo mese e anno dalla data
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();

        this.context = context;

        String nameFile = month + "-" + year;
//        String nameFile = "porcocane" + "/";
        File filepath = getPathFile("");


        try {
            Document document = new Document(PageSize.A4, 20, 20, 20, 20);
            setPointMMCoefficent(PageSize.A4.getHeight() / 297f);
            document.setMargins(12.7f * getPointMMCoefficent(), 12.7f * getPointMMCoefficent(), 29, 20);
            File file = new File(filepath + "/" +"Rotazione" + nameFile + ".pdf");
            file.createNewFile();

            pathFile = file.getAbsolutePath();

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

            PdfStandardFooter footerEvent = new PdfStandardFooter();

            PdfHeader headerEvent = new PdfHeader();

//            footerEvent.prototype = p;
            writer.setPageEvent(footerEvent);
            writer.setPageEvent(headerEvent);
            document.open();

            addMetaData(document);
            addCoverPage(document, writer);
            pdfSupportTable(document, writer);


            document.close();
            //todo: crearefile da Document https://stackoverflow.com/questions/11897290/how-to-convert-itextpdf-document-to-byte-array


        } catch (Exception e) {
            e.printStackTrace();
            pathFile = "";
        }
        return pathFile;
    }


    private void pdfSupportTable(Document document, PdfWriter writer){



//        List<Grids> grids = new ArrayList<>();
//        List<Supports> suppAList = new ArrayList<>();
//        List<SuppB> suppBList = new ArrayList<>();
//        List<Supports> suppCList = new ArrayList<>();
//        List<Supports> suppDList = new ArrayList<>();
//        List<SuppE> suppEList = new ArrayList<>();
//        List<Supports> suppGList = new ArrayList<>();



//        for (int g = 0; g < grids.size(); g++){
//            suppAList.addAll(grids.get(g).getMaterialList().getSuppAList());
//            suppBList.addAll(grids.get(g).getMaterialList().getSuppBList());
//            suppCList.addAll(grids.get(g).getMaterialList().getSuppCList());
//            suppDList.addAll(grids.get(g).getMaterialList().getSuppDList());
//            suppEList.addAll(grids.get(g).getMaterialList().getSuppEList());
//            suppGList.addAll(grids.get(g).getMaterialList().getSuppGList());
//
//        }
//
//
//        List<List<SuppB>> bSpecialization = new ArrayList<>();
//        List<List<SuppE>> eSpecialization = new ArrayList<>();
//
//        if (suppBList.size() > 0){
//            for (Grids g : grids){
//                List<SuppB> b = new ArrayList<>();
//                for (int i = 0; i < g.getMaterialList().getSuppBList().size(); i++){
//                    b.add(g.getMaterialList().getSuppBList().get(i));
//                }
//                bSpecialization.add(b);
//
//            }
//        }
//
//        if (suppEList.size() > 0){
//            for (Grids g : grids){
//                List<SuppE> e = new ArrayList<>();
//                for (int i = 0; i < g.getMaterialList().getSuppEList().size(); i++){
//                    e.addAll(g.getMaterialList().getSuppEList());
//                }
//                eSpecialization.add(e);
//
//            }
//        }
//
//
//        double supportPrice = 0;
//        double bLenght = 0;
//        double eLenght = 0;
//        double gridPrice = 0;
//        int lenght = 0;

        //calcolo prezzo supporti senza B e E
//        supportPrice += suppAList.size() * suppAList.get(0).getPurchasePrice()
//                + suppCList.size() * suppCList.get(0).getPurchasePrice()
//                + suppDList.size() * suppDList.get(0).getPurchasePrice()
//                + suppGList.size() * suppGList.get(0).getPurchasePrice();
//
//
//        //sommare lunghezze di tutti i B o E
//        if (bSpecialization.size() > 0){
//            for (List<SuppB> b : bSpecialization){
//                bLenght += b.get(0).getLenght() * b.size();
//                lenght += b.get(0).getLenght() * b.size();
//            }
//            supportPrice += lenght * suppBList.get(0).getPurchasePrice() /100;
//        } else if (eSpecialization.size() > 0){
//            for (List<SuppE> e : eSpecialization){
//                eLenght += e.get(0).getLenght() * e.size();
//                lenght += e.get(0).getLenght() * e.size();
//            }
//            supportPrice += lenght * suppEList.get(0).getPurchasePrice() / 100;
//        }
//
//
//        for (Grids g : grids) {
//            gridPrice += g.getPurchasePrice() * g.getArea();
//        }
//
//        totalSupportPrice = supportPrice;


        ////////////////////////////////////////////////////////////////////////////////////////////
        //preparo le tabelle

        PdfContentByte contentByte = writer.getDirectContent();


        //titolo
        Phrase phrase = new Phrase("ROTAZIONE", getCalibriBold22());
        ColumnText.showTextAligned(contentByte, Element.ALIGN_LEFT, phrase, 80 * getPointMMCoefficent(),
                document.getPageSize().getHeight() - 35 * getPointMMCoefficent(), 0);

        //sottotiltolo supporti
        phrase = new Phrase("Material list", getCalibriBold20());
        ColumnText.showTextAligned(contentByte, Element.ALIGN_LEFT, phrase, 12 * getPointMMCoefficent(),
                document.getPageSize().getHeight() - 45 * getPointMMCoefficent(), 0);




        contentByte.saveState();
        contentByte.restoreState();

        //Creating all table
        PdfPTable suppTable = new PdfPTable(8);
        PdfPTable suppTotTable = new PdfPTable(2);



        //dimensione tabelle
        int ROW_HEIGHT = 14;
        float EMPTY_ROW_HEIGHT = 12;

        try {
            //tabella supporti
            suppTable.setWidthPercentage(100);
            suppTable.setTotalWidth(new float[]{10, 20, 10,7, 13, 10,20, 10});

            //tabella totali solo supporti
            suppTotTable.setWidthPercentage(100);
            suppTotTable.setTotalWidth(new float[]{85,10});




        } catch (DocumentException e1) {
            e1.printStackTrace();
        }
        suppTable.setLockedWidth(true);
        suppTotTable.setLockedWidth(true);



        //first row titoli colonne
        //Etichette colonne
//        labelCell("Article REX", suppTable);
//        labelCell("Description", suppTable);
//        labelCell("Quantity", suppTable);
//        emptyCell(suppTable);
//        emptyCell(suppTable);
//        labelCell("Price unitary", suppTable);
//        labelCell("Price per meter", suppTable);
//        labelCell("Total", suppTable);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

//        generateSupportCell(suppAList, suppTable);
//        generateSupportBCell(suppBList, suppTable, bLenght);
//        generateSpecializationCell(bSpecialization , suppTable);
//        generateSupportCell(suppCList, suppTable);
//        generateSupportCell(suppDList, suppTable);
//        generateSupportBCell(suppEList, suppTable, eLenght);
//        generateSpecializationCell(eSpecialization , suppTable);
//        generateSupportCell(suppGList, suppTable);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //totale supporti

        BaseColor myColor = WebColors.getRGBColor("#CBCBCB");

        PdfPCell cell = new PdfPCell(new Phrase("Total price of supports" , getCalibriBold10()));
        cell.setFixedHeight(ROW_HEIGHT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setNoWrap(false);
        cell.setBackgroundColor(myColor);
        suppTotTable.addCell(cell);

//        cell = new PdfPCell(new Phrase(decimalFormat(supportPrice) + " CHF",getCalibriBold10()));
        cell.setFixedHeight(ROW_HEIGHT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBackgroundColor(myColor);
        suppTotTable.addCell(cell);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



        final int FIRST_ROW = 0;
        final int LAST_ROW = -1;


        //posizione tabelle
        suppTable.setTotalWidth((document.right()-document.left())*suppTable.getWidthPercentage()/100f);
        suppTable.writeSelectedRows(FIRST_ROW, LAST_ROW, document.left(), document.bottom()+suppTable.getTotalHeight()+520,writer.getDirectContent());

        suppTotTable.setTotalWidth((document.right()-document.left())*suppTotTable.getWidthPercentage()/100f);
        suppTotTable.writeSelectedRows(FIRST_ROW, LAST_ROW, document.left(), document.bottom()+suppTotTable.getTotalHeight()+506,writer.getDirectContent());

//        tableposition = suppTable.getTotalHeight() + suppTotTable.getTotalHeight()-90;
//        document.newPage();

    }
}
