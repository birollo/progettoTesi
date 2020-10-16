package ARL.tesi.service;

import ARL.tesi.exception.FileStorageException;
import ARL.tesi.exception.MyFileNotFoundException;
import ARL.tesi.modelobject.DBFile;
import ARL.tesi.repository.DBFileRepository;
import ARL.tesi.repository.ShifftsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.util.Date;
import java.util.List;

@Service
public class DBFileStorageService {

    @Autowired
    private DBFileRepository dbFileRepository;

    @Autowired
    private ShifftsRepository shifftsRepository;


    public DBFile storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        String fileName = file.getName();
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Non e stato possibile trovare il file: " + fileName + " perché contiene caratteri invalidi");
            }

            //todo: assegno fileType a dipendenza del estensione
            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes(), new Date());

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Non è stato possibile salvare il file: " + fileName , ex);
        }
    }

    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File non presente" + fileId));
    }

    public List<DBFile> getAllByType(String type){
//        return dbFileRepository.getAllByFileType(type);
        return dbFileRepository.getAllByFileTypeOrderByDateDesc(type);

    }

    public DBFile getFileByName(String name){
        return dbFileRepository.getFirstByFileName(name);
    }
}
