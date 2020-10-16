package ARL.tesi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ARL.tesi.modelobject.Shiffts;

import java.util.List;

@Repository
public interface ShifftsRepository extends JpaRepository<Shiffts, Integer> {
        Shiffts getByName(int name);
        Shiffts getFirstByName(int name);
        List<Shiffts> findByName(int name);
        Shiffts getByNameAndVersion(int name, int version);

}