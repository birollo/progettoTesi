package ARL.tesi.repository;

import ARL.tesi.modelobject.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {
    DBFile getFirstByFileName(String fileName);
}