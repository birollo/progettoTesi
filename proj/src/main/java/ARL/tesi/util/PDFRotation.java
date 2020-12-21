//package ARL.tesi.util;
//
//
//import ARL.tesi.modelobject.User;
//import ARL.tesi.service.DBFileStorageService;
//import com.itextpdf.text.*;
//import com.itextpdf.text.html.WebColors;
//import com.itextpdf.text.pdf.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.ServletContext;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Service
//public class PDFRotation extends PDFUtility{
//
//
//    @Autowired
//    DBFileStorageService dbFileStorageService;
//
//    @Autowired
//    ShifftsReader shifftsReader;
//
//
//    public PDFRotation(){
//        BaseFont base = null;
//        try {
//            base = BaseFont.createFont(getPathFile("fonts/calibrib.ttf").getPath(), BaseFont.WINANSI,
//                    BaseFont.EMBEDDED);
//        } catch (DocumentException | IOException e1) {
//            e1.printStackTrace();
//        }
//        if (base != null)
//            setUpCalibriFont(base);
//        else
//            setUpTimesFont();
//
//    }
//
//
//    public String createRotationPDF(ServletContext context, Date date, List<User> users){
//        String pathFile;
//        //edit by Roberto Bignasca
//        setPage(1);
//
//        //estrapolo mese e anno dalla data
//        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        int month = localDate.getMonthValue();
//        int year = localDate.getYear();
//
//        this.context = context;
//
//        String nameFile = month + "-" + year;
////        String nameFile = "porcocane" + "/";
//        File filepath = getPathFile("");
//
//
//        try {
//            Document document = new Document(PageSize.A4, 20, 20, 20, 20);
//            setPointMMCoefficent(PageSize.A4.getHeight() / 297f);
//            document.setMargins(12.7f * getPointMMCoefficent(), 12.7f * getPointMMCoefficent(), 29, 20);
//            File file = new File(filepath + "/" +"Rotazione" + nameFile + ".pdf");
//            file.createNewFile();
//
//            pathFile = file.getAbsolutePath();
////            p.setQuotationPath(file.getAbsolutePath());
//            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
//
//            PdfStandardFooter footerEvent = new PdfStandardFooter();
//
//            PdfHeader headerEvent = new PdfHeader();
//
////            footerEvent.prototype = p;
//            writer.setPageEvent(footerEvent);
//            writer.setPageEvent(headerEvent);
//
//            //todo: porcata l open c era gia
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            PdfWriter.getInstance(document, byteArrayOutputStream);
//
//            document.open();
//
//            document.add(new Chunk(""));
//
//            addMetaData(document);
//            addCoverPage(document, writer);
//            pdfSupportTable(document, writer);
//
//
//
//
//            document.close();
//            //todo: crearefile da Document https://stackoverflow.com/questions/11897290/how-to-convert-itextpdf-document-to-byte-array
//            byte[] pdfBytes = byteArrayOutputStream.toByteArray();
////            byte[] fileContent = Files.readAllBytes(file.toPath());
//            MultipartFile multipartFile = shifftsReader.convert(file, pdfBytes, filepath + "/" +"Rotazione" + nameFile + ".pdf" );
//            dbFileStorageService.storeFile(multipartFile);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            pathFile = "";
//        }
//
//
//        return pathFile;
//    }
//
//
//    private void pdfSupportTable(Document document, PdfWriter writer){
//
//        ////////////////////////////////////////////////////////////////////////////////////////////
//        //preparo le tabelle
//
//        PdfContentByte contentByte = writer.getDirectContent();
//
//
//        //titolo
//        Phrase phrase = new Phrase("ROTAZIONE", getCalibriBold22());
//        ColumnText.showTextAligned(contentByte, Element.ALIGN_LEFT, phrase, 80 * getPointMMCoefficent(),
//                document.getPageSize().getHeight() - 35 * getPointMMCoefficent(), 0);
//
//        //sottotiltolo supporti
//        phrase = new Phrase("Material list", getCalibriBold20());
//        ColumnText.showTextAligned(contentByte, Element.ALIGN_LEFT, phrase, 12 * getPointMMCoefficent(),
//                document.getPageSize().getHeight() - 45 * getPointMMCoefficent(), 0);
//
//
//
//
//        contentByte.saveState();
//        contentByte.restoreState();
//
//        //Creating all table
//        PdfPTable suppTable = new PdfPTable(8);
//
//
//        //dimensione tabelle
//        int ROW_HEIGHT = 14;
//        float EMPTY_ROW_HEIGHT = 12;
//
//        try {
//            //tabella supporti
//            suppTable.setWidthPercentage(100);
//            suppTable.setTotalWidth(new float[]{10, 20, 10,7, 13, 10,20, 10});
//
//        } catch (DocumentException e1) {
//            e1.printStackTrace();
//        }
//        suppTable.setLockedWidth(true);
//
//
//
//
//        //first row titoli colonne
//        //Etichette colonne
//        labelCell("Article REX", suppTable);
//        labelCell("Description", suppTable);
//        labelCell("Quantity", suppTable);
//        emptyCell(suppTable);
//        emptyCell(suppTable);
//        labelCell("Price unitary", suppTable);
//        labelCell("Price per meter", suppTable);
//        labelCell("Total", suppTable);
//
//        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
////        generateSupportCell(suppAList, suppTable);
////        generateSupportBCell(suppBList, suppTable, bLenght);
////        generateSpecializationCell(bSpecialization , suppTable);
////        generateSupportCell(suppCList, suppTable);
////        generateSupportCell(suppDList, suppTable);
////        generateSupportBCell(suppEList, suppTable, eLenght);
////        generateSpecializationCell(eSpecialization , suppTable);
////        generateSupportCell(suppGList, suppTable);
//
//        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        //totale supporti
//
//        BaseColor myColor = WebColors.getRGBColor("#CBCBCB");
//
//        PdfPCell cell = new PdfPCell(new Phrase("Total price of supports" , getCalibriBold10()));
//        cell.setFixedHeight(ROW_HEIGHT);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        cell.setBorder(Rectangle.BOTTOM);
//        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        cell.setNoWrap(false);
//        cell.setBackgroundColor(myColor);
//
//
////        cell = new PdfPCell(new Phrase(decimalFormat(supportPrice) + " CHF",getCalibriBold10()));
//        cell.setFixedHeight(ROW_HEIGHT);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        cell.setBorder(Rectangle.BOTTOM);
//        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        cell.setBackgroundColor(myColor);
//
//
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//
//
//        final int FIRST_ROW = 0;
//        final int LAST_ROW = -1;
//
//
//        //posizione tabelle
//        suppTable.setTotalWidth((document.right()-document.left())*suppTable.getWidthPercentage()/100f);
//        suppTable.writeSelectedRows(FIRST_ROW, LAST_ROW, document.left(), document.bottom()+suppTable.getTotalHeight()+520,writer.getDirectContent());
//
////        tableposition = suppTable.getTotalHeight() + suppTotTable.getTotalHeight()-90;
////        document.newPage();
//
//    }
//
//    private void labelCell(String s, PdfPTable table){
//
//        PdfPCell cell = new PdfPCell(new Phrase(s, getCalibriBold10()));
//        cell.setFixedHeight(14);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        cell.setBorder(Rectangle.BOTTOM);
//        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//        table.addCell(cell);
//    }
//}
