package ARL.tesi.controller;



import ARL.tesi.modelobject.DBFile;
import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.modelobject.User;
import ARL.tesi.repository.ShifftsRepository;
import ARL.tesi.service.DBFileStorageService;
import ARL.tesi.service.PersonService;
import ARL.tesi.service.ShifftService;
import ARL.tesi.util.*;
import com.itextpdf.text.DocumentException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class FileController {


    @Autowired
    private DBFileStorageService dbFileStorageService;

    @Autowired
    protected ServletContext context;

    @Autowired
    private ShifftService shifftService;

    @Autowired
    private PDFRotation pdfRotation;

    @Autowired
    private PersonService personService;
//    @Autowired
//    private PDFRotation pdfRotation;

    @Autowired
    private ExcelToPDF excelToPDF;


    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        DBFile dbFile = dbFileStorageService.storeFile(file);
        if (Objects.requireNonNull(file.getOriginalFilename()).contains("xlsx")){
            ShifftsReader shifftsReader = new ShifftsReader();


            try {
                excelToPDF.convert(file);
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }


            try {
                for (Shiffts t : shifftsReader.readExcell(file)){
                    if (shifftService.getByName(t.getName()).size() > 0){
                        t.setVersion(shifftService.getLastByName(t.getName()).getVersion()+1);
                        shifftService.save(t);
                    }else{
                        shifftService.save(t);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }


    @GetMapping("/download")
    public ResponseEntity<Resource> download(@PathVariable String fileId) {
        //Load file from database
        DBFile dbFile = dbFileStorageService.getFileByName("provaScript2.xlsx");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
//        DBFile dbFile = dbFileStorageService.getFileByName("provaScript2.xlsx");
            DBFile dbFile = dbFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    @GetMapping("/rotation/download")
    public ResponseEntity<InputStreamResource> downloadPdf()
    {
        Date deteChose = new Date();
        List<User> users = personService.getUsersByRole("Autista di linea");
        String pathfile = pdfRotation.createRotationPDF(context, deteChose, users);
        try
        {
            File file = new File(pathfile);

            //salvataggio in DB
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), new FileInputStream(file));
            dbFileStorageService.storeFile(multipartFile);

            HttpHeaders respHeaders = new HttpHeaders();
            MediaType mediaType = MediaType.parseMediaType("application/pdf");
            respHeaders.setContentType(mediaType);
            respHeaders.setContentLength(file.length());
            respHeaders.setContentDispositionFormData("attachment", file.getName());
            InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
            return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
        }
        catch (Exception e)
        {
            String message = "Errore nel download del file;";

            return new ResponseEntity<InputStreamResource>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping(value="/rotation/download")
//    public ResponseEntity<Resource> downloadRotationPDF(){
//        String sDate = "01/01/2020";
//        try {
//            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
//            pdfRotation.createRotationPDF(date, context);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        DBFile dbFile = dbFileStorageService.getFileByName("Rotazione1-2020.pdf");
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
//                .body(new ByteArrayResource(dbFile.getData()));
//    }
}