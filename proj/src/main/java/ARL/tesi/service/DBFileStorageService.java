package ARL.tesi.service;

import ARL.tesi.exception.FileStorageException;
import ARL.tesi.exception.MyFileNotFoundException;
import ARL.tesi.modelobject.DBFile;
import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.repository.DBFileRepository;
import ARL.tesi.repository.ShifftsRepository;
import ARL.tesi.util.ShifftsReader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class DBFileStorageService {

    @Autowired
    private DBFileRepository dbFileRepository;

    @Autowired
    private ShifftsRepository shifftsRepository;

    @Autowired
    ShifftService shifftService;

    @Autowired
    ShifftsReader shifftsReader;

    public DBFile storeFile(MultipartFile file) throws Exception {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        System.out.println("dentro storeFile");
//        String fileName = file.getName();
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Non e stato possibile trovare il file: " + fileName + " perché contiene caratteri invalidi");
            }
            if (Objects.requireNonNull(file.getOriginalFilename()).contains("xlsx")) {
                try {
                    List<Shiffts> shiffts = shifftsReader.readExcell(file);
                    for (Shiffts s : shiffts) {
                        if (shifftService.getByName(s.getName()).size() > 0) {
                            s.setVersion(shifftService.getLastByName(s.getName()).getVersion() + 1);
                            shifftService.save(s);
                        } else {
                            shifftService.save(s);
                        }
                    }
                } catch (Exception e) {
                    throw e;
                }
                //todo: tolto per deploy
            }


            DBFile dbFile;
            if (file.getName().contains("pdf")){
                dbFile = new DBFile(fileName, String.valueOf(MediaType.APPLICATION_PDF), file.getBytes(), new Date());
            }else{
                dbFile = new DBFile(fileName, file.getContentType(), file.getBytes(), new Date());
            }
            //todo: assegno fileType a dipendenza del estensione


            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Non è stato possibile salvare il file: " + fileName, ex);
        }
    }

    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File non presente" + fileId));
    }

    public List<DBFile> getAllByType(String type) {
//        return dbFileRepository.getAllByFileType(type);
        return dbFileRepository.getAllByFileTypeOrderByDateDesc(type);

    }

    public DBFile getFileByName(String name) {
        return dbFileRepository.getFirstByFileName(name);
    }
}
