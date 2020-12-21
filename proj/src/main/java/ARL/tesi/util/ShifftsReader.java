package ARL.tesi.util;


import ARL.tesi.exception.FileStorageException;
import ARL.tesi.modelobject.DBFile;
import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.repository.DBFileRepository;
import ARL.tesi.repository.ShifftsRepository;
import ARL.tesi.service.DBFileStorageService;
import ARL.tesi.service.ShifftService;
import com.github.pjfanning.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.*;

@Service
public class ShifftsReader {

    @Autowired
    ShifftService shifftService;

    @Autowired
    ShifftsRepository shifftsRepository;

    @Autowired
    Excel2pdf excel2pdf;

    @Autowired
    DBFileRepository dbFileRepository;


    public List<Shiffts> readExcell(MultipartFile file) throws IOException, FileStorageException {

        List<Shiffts> shiffts = new ArrayList<>();
//        InputStream fis = new FileInputStream(convert(file));
        InputStream fis = file.getInputStream();
        Workbook workbook = StreamingReader.builder()
                .rowCacheSize(10)    // number of rows to keep in memory (defaults to 10)
                .bufferSize(1024)     // buffer size to use when reading InputStream to file (defaults to 1024)
                .open(fis);
//        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());


        //number of sheets from the XLSX workbook
        int sheets = workbook.getNumberOfSheets();
        for (int i = 0; i < sheets; i++){

            Sheet sheet = workbook.getSheetAt(i);
//            XSSFSheet sheet = workbook.getSheetAt(i);
            String name = "";
            boolean school = false;
            int duration=0;
            int value=0;
            int hours=0;
            int minutes=0;
            String days = "";
            LocalTime startTime = null;
            LocalTime endTime= null;
            double time = 0;

            if(sheet.getSheetName().contains("Foglio")){

            }else {
                for (Row r : sheet){
                    for (Cell c : r){
                        int rowIndex = c.getRowIndex();
                        int colIndex = c.getColumnIndex();
                        if (rowIndex == 1 &&  colIndex == 0) {
                            if (c.getCellType()==CellType.NUMERIC){
                                name =String.valueOf((int) c.getNumericCellValue());
                            }else if (c.getCellType()==CellType.STRING){
                                name = c.getStringCellValue();
                            }
                            if (name.equals("")){
                                throw new FileStorageException("nome non presente nel forglio numero " + i+1);
                            }
                        }else if (rowIndex == 2 && colIndex == 0){
                            days = c.getStringCellValue();
                        } else if (rowIndex == 6 && colIndex == 9) {
                            duration = (int) c.getNumericCellValue();
                        }else if (rowIndex == 7 && colIndex == 8) {
                            value = (int) c.getNumericCellValue();
                        }else if (rowIndex == 5 &&  colIndex== 7) {
                            time = Math.ceil((c.getNumericCellValue() *24)* Math.pow(10,2))/ Math.pow(10,2);
                            hours = (int)time;
                            minutes = (int) ((time - hours) * 60);
                            if (hours == 24){
                                hours= 0;
                            }
                            startTime =  LocalTime.of(hours, minutes);
                        }else if (rowIndex == 5 && colIndex == 8) {
                            time = Math.ceil((c.getNumericCellValue() *24)* Math.pow(10,2))/ Math.pow(10,2);
                            hours = (int) time;
                            minutes = (int) ((time - hours) * 60);
                            if (hours == 24){
                                hours= 0;
                            }
                            endTime =  LocalTime.of(hours, minutes);
                        }

                    }
                }
            }



            if (name.contains("SC")){
                school = false;
            }else {
                school = true;
            }

            String type;
            if (startTime.isAfter(LocalTime.of(4,0)) && startTime.isBefore(LocalTime.of(6,0))){
                type="Mattina";
            }else if (startTime.isAfter(LocalTime.of(6,0)) && endTime.isBefore(LocalTime.of(20,0))){
                type="Meridiano";
            }else if (endTime.isAfter(LocalTime.of(20,0)) && endTime.isBefore(LocalTime.of(23,59))){
                type="Serale";
            }else if(endTime.isAfter(LocalTime.of(0,0)) && endTime.isBefore(LocalTime.of(4,0))){
                type="Notturno";
            }else {
                type="Riposo";
            }

            //replace sulla stringa per togliere
            days = days.replace(" ", "");
            switch (days){
                case "LU-VE":
                    days = "MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY";
                    break;
                case "LU/GIO":
                case "LU-GIO":
                    days = "MONDAY,TUESDAY,WEDNESDAY,THURSDAY";
                    break;
                case "LU & GIO":
                    days = "MONDAY, THURSDAY";
                    break;
                case "VE":
                    days = "FRIDAY";
                    break;
                case "MA":
                    days = "TUESDAY";
                    break;
                case "MER":
                    days = "WEDNESDAY";
                    break;
                case "MA-VE":
                case "MAR-VEN":
                    days = "TUESDAY,WEDNESDAY,THURSDAY,FRIDAY";
                    break;
                case "SABATO":
                    days = "SATURDAY";
                    break;
                    //TODO: COME GESTISCO I FESTIVI
//                case "VE-SA FESTIVO":
//                    days = "SATURDAY,SAF";
//                    break;
                case "DOMENICA":
                    days = "SUNDAY";
                    break;
            }

            if (name.equals("") || type.equals("") || duration==0 || value==0 || days.equals("") ){

                //errore nel foglio non salvo nulla
            }else {
                Shiffts t = new Shiffts(name, school, type,days, duration, value, 0, startTime, endTime);
                shiffts.add(t);
            }

//             if (shifftService.getByName(t.getName()).size() > 0){
//                t.setVersion(shifftService.getLastByName(t.getName()).getVersion()+1);
//                shifftService.save(t);
//            }else{
//                shifftService.save(t);
//            }

            //todo:separare con funzione apposita
//
//            byte[] bytes = convert(sheet);
//            File filePDF = new File("demo.xlsx");
//            try {
//                OutputStream os = new FileOutputStream(filePDF);
//                os.write(bytes);
//                os.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                MultipartFile multipartFile = convert(filePDF,bytes, name);
//                DBFile dbFile = new DBFile(name, multipartFile.getContentType(), multipartFile.getBytes(), new Date());
//                dbFileRepository.save(dbFile);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            System.out.println("aggiunto turno" + name);
        }
        workbook.close();
        return shiffts;
    }


    public File convert(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public byte[] convert(Sheet sheet) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            sheet.getWorkbook().write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public MultipartFile convert(File f, byte[] bytes, String name){
        String originalFileName = f.getName();
        String contentType = "text/plain";
        byte[] content = bytes;

        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);

        return result;
    }



}
