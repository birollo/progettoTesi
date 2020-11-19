package ARL.tesi.util;

import ARL.tesi.service.DBFileStorageService;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

//base
//https://blog.aspose.com/2020/08/12/convert-excel-to-pdf-using-java/

//rimuovere Scritta
//https://docs.aspose.com/pdf/java/replace-text-in-a-pdf-document/


@Service
public class Excel2pdf {

    @Autowired
    DBFileStorageService dbFileStorageService;
    public void convert(MultipartFile file) {
        try {
            FileInputStream inputStream = (FileInputStream) file.getInputStream();
            Workbook workbook = new Workbook(inputStream);
            // Save the document in PDF format
            workbook.save("src/main/resources/files/Excel-to-PDF.pdf", SaveFormat.PDF);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



