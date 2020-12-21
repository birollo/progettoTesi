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
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

//    @Autowired
//    private PDFRotation pdfRotation;

    @Autowired
    private PersonService personService;
//    @Autowired
//    private PDFRotation pdfRotation;

//    @Autowired
//    private ExcelToPDF excelToPDF;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        DBFile dbFile = dbFileStorageService.storeFile(file);


        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }


//    @PostMapping("/uploadMultipleFiles")
//    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//        return Arrays.asList(files)
//                .stream()
//                .map(file -> uploadFile(file))
//                .collect(Collectors.toList());
//    }


    @GetMapping("/download")
    public ResponseEntity<Resource> download(@PathVariable String fileId) {
        //Load file from database
        //todo: filename
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

//    @RequestMapping(value="/rotation/download", method=RequestMethod.GET)
//    public ResponseEntity<Resource> downloadPDFRotation( HttpServletResponse response) throws IOException, ParseException {
//
//        String sDate = "01/01/2020";
//        Date dateChose = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
//        List<User> users = personService.getUsersByRole("Autista di linea");
//        String pathfile = pdfRotation.createRotationPDF(context, dateChose, users);
//
//        DBFile dbFile = dbFileStorageService.getFileByName("Rotazione1-2020.pdf");
//
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
//                .body(new ByteArrayResource(dbFile.getData()));
//    }

//    @RequestMapping(value="/rotation/download", method = RequestMethod.GET)
//    public ResponseEntity<Resource> getFile() throws ParseException {
//        String sDate = "01/01/2020";
//        Date dateChose = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
//        List<User> users = personService.getUsersByRole("Autista di linea");
//        String pathfile = pdfRotation.createRotationPDF(context, dateChose, users);
//
//        DBFile dbFile = dbFileStorageService.getFileByName("Rotazione1-2020.pdf");
//
//        return ResponseEntity.ok()
////                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
//                .contentType(MediaType.APPLICATION_PDF)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
//                .body(new ByteArrayResource(dbFile.getData()));
//
//    }

//    @RequestMapping(value = "/rotation/download")
//    public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response) throws ParseException {
//        //If user is not authorized - he should be thrown out from here itself
//        String sDate = "01/01/2020";
//        Date dateChose = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
//        List<User> users = personService.getUsersByRole("Autista di linea");
//        String pathfile = pdfRotation.createRotationPDF(context, dateChose, users);
//
//        //Authorized user will download the file
//        String dataDirectory = request.getServletContext().getRealPath("/WEB-INF/downloads/pdf/");
////        Path file = Paths.get(dataDirectory, pathfile);
//        Path file = pathfile;
//        if (Files.exists(file)) {
//            response.setContentType("application/pdf");
//            response.addHeader("Content-Disposition", "attachment; filename=" + pathfile);
//            try {
//                Files.copy(file, response.getOutputStream());
//                response.getOutputStream().flush();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }

    //todo: l'ultimo implementato
//    @RequestMapping(value = "/rotation/download", method = RequestMethod.GET)
//    public void downloadQuotationFile(HttpServletResponse response) throws IOException, ParseException {
//
//        String sDate = "01/01/2020";
//        Date dateChose = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
//        List<User> users = personService.getUsersByRole("Autista di linea");
//        String pathfile = pdfRotation.createRotationPDF(context, dateChose, users);
//
//
//
//        FileSystemResource fsr = new FileSystemResource(new File(pathfile));
//        IOUtils.copy(fsr.getInputStream(), response.getOutputStream());
//        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
//        response.flushBuffer();
//    }


//    @GetMapping(value="/rotation/download")
//    public void downloadRotationPDF(HttpServletResponse response) throws IOException {
//        String sDate = "01/01/2020";
//        try {
//            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
//            List<User> users = personService.getUsersByRole("Autista di linea");
//            pdfRotation.createRotationPDF(context, date, users);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        DBFile dbFile = dbFileStorageService.getFileByName("Rotazione1-2020.pdf");
//        File finalFile =new File("Rotazione1-2020.pdf");
//
////        return ResponseEntity.ok()
////                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
////                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
////                .body(new ByteArrayResource(dbFile.getData()));
//        FileUtils.writeByteArrayToFile(finalFile, dbFile.getData());
//        FileSystemResource fsr = new FileSystemResource(finalFile);
//        IOUtils.copy(fsr.getInputStream(), response.getOutputStream());
//        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
//        response.flushBuffer();
//    }

    //    @GetMapping("/rotation/download")
//    public ResponseEntity<InputStreamResource> downloadPdf() throws ParseException {
//
//        String sDate = "01/01/2020";
//        Date dateChose = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
//        List<User> users = personService.getUsersByRole("Autista di linea");
//        String pathfile = pdfRotation.createRotationPDF(context, dateChose, users);
//
//        try
//        {
//            File file = new File(pathfile);
//
//            //salvataggio in DB
//            MultipartFile multipartFile = new MockMultipartFile(file.getName(), new FileInputStream(file));
//            dbFileStorageService.storeFile(multipartFile);
//
//            HttpHeaders respHeaders = new HttpHeaders();
//            MediaType mediaType = MediaType.parseMediaType("application/pdf");
//            respHeaders.setContentType(mediaType);
//            respHeaders.setContentLength(file.length());
//            respHeaders.setContentDispositionFormData("attachment", file.getName());
//            InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
//            return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
//        }
//        catch (Exception e)
//        {
//            String message = "Errore nel download del file;";
//
//            return new ResponseEntity<InputStreamResource>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}