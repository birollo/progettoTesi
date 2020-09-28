package ARL.tesi.controller;



import ARL.tesi.modelobject.DBFile;
import ARL.tesi.modelobject.Turno;
import ARL.tesi.repository.TurnoRepository;
import ARL.tesi.service.DBFileStorageService;
import ARL.tesi.util.ShifftsReader;
import ARL.tesi.util.UploadFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileController {


    @Autowired
    private DBFileStorageService dbFileStorageService;

    @Autowired
    protected ServletContext context;

    @Autowired
    private TurnoRepository turnoRepository;

//    @Autowired
//    private PDFRotation pdfRotation;



    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        DBFile dbFile = dbFileStorageService.storeFile(file);

        //todo: usato anche per rotazione
//            aggiornamento turni
        ShifftsReader shifftsReader = new ShifftsReader();
        try {
            for (Turno t : shifftsReader.readExcell(file)){

                if (turnoRepository.getByName(t.getName()) != null){
                    Turno turn = turnoRepository.getByName(t.getName());
                    turn.setDuration(t.getDuration());
                    turn.setValue(t.getValue());
                    turn.setValueServizio(t.getValueServizio());
                    turnoRepository.save(turn);
                }else{
                    turnoRepository.save(t);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        turnoRepository.saveAll(shifftsReader.readExcell(file));

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
        DBFile dbFile = dbFileStorageService.getFileByName("provaScript3.xlsx");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        DBFile dbFile = dbFileStorageService.getFileByName("provaScript4.xlsx");


        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
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