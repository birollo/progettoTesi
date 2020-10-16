package ARL.tesi.util;


import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.repository.ShifftsRepository;
import ARL.tesi.service.ShifftService;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShifftsReader {





    public List<Shiffts> readExcell(MultipartFile file) throws IOException {

        List<Shiffts> shiffts = new ArrayList<>();

        InputStream fis = new BufferedInputStream(file.getInputStream());

        //finda the workbook instanc for XLSX file
        XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);

        //number of sheets from the XLSX workbook
        int sheets = myWorkBook.getNumberOfSheets();


        for (int i = 0; i < sheets; i++){
            XSSFSheet sheet = myWorkBook.getSheetAt(i);

            // shifft name is in A3
            CellReference cellReference = new CellReference("A2");
            Row row = sheet.getRow(cellReference.getRow());
            Cell cell = row.getCell(cellReference.getCol());
            int name =  (int) cell.getNumericCellValue();

            // sshifft school boolean is in A3
            cellReference = new CellReference("A4");
            row = sheet.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            String sc = cell.getStringCellValue();
            boolean school;

            if (sc.equals("SCUOLE APERTE")){
                school = true;
            }else {
                school = false;
            }

            //shifft type is in A3
            cellReference = new CellReference("A3");
            row = sheet.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            String type = cell.getStringCellValue();


            // shifft duration is in J7
            cellReference = new CellReference("J7");
            row = sheet.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            int duration = (int) cell.getNumericCellValue();

            // shifft value is in I8
            cellReference = new CellReference("I8");
            row = sheet.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            int value = (int) cell.getNumericCellValue();

            // shifft value is in J6
            cellReference = new CellReference("J6");
            row = sheet.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            int valueServizio = (int) cell.getNumericCellValue();

            // shifft startTime is in H6
            cellReference = new CellReference("H6");
            row = sheet.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            double time = Math.ceil((cell.getNumericCellValue() *24)* Math.pow(10,2))/ Math.pow(10,2);
            int hours = (int)time;
            int minutes = (int) ((time - hours) * 60);
            LocalTime startTime =  LocalTime.of(hours, minutes);

            // shifft endTime is in I6
            cellReference = new CellReference("I6");
            row = sheet.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            time = Math.ceil((cell.getNumericCellValue() *24)* Math.pow(10,2))/ Math.pow(10,2);
            hours = (int) time;
            minutes = (int) ((time - hours) * 60);
            LocalTime endTime =  LocalTime.of(hours, minutes);

            shiffts.add(new Shiffts(name, school, type, duration, value, valueServizio, 0, startTime, endTime));

        }
        return shiffts;
    }


}
