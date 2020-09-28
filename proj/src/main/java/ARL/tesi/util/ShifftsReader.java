package ARL.tesi.util;


import ARL.tesi.modelobject.Turno;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShifftsReader {



    public List<Turno> readExcell(MultipartFile file) throws IOException {

        List<Turno> shiffts = new ArrayList<>();

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


            shiffts.add(new Turno(name, school, type, duration, value, valueServizio));


        }
        return shiffts;
    }


}
