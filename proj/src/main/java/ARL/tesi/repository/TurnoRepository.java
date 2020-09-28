package ARL.tesi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ARL.tesi.modelobject.Turno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Integer> {
        Turno getByName(int name);
        Turno getFirstByName(int name);
}