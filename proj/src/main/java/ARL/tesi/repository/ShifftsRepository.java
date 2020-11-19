package ARL.tesi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ARL.tesi.modelobject.Shiffts;

import java.util.List;

@Repository
public interface ShifftsRepository extends JpaRepository<Shiffts, Integer> {
        Shiffts getByName(String name);
        Shiffts getFirstByName(String name);
        List<Shiffts> findByName(String name);
        Shiffts getByNameAndVersion(String name, int version);




}