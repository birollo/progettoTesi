package ARL.tesi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.Turno;
import ARL.tesi.modelobject.User;

import java.util.Date;
import java.util.List;

@Repository
public interface AssegnazioneRepository extends JpaRepository<Assegnazione, Integer> {
    List<Assegnazione> findAssegnazioniByUser(User user);
    Assegnazione findAssegnazioneByDateAndUser(Date date, User user);
    @Transactional
    void deleteAssegnazioneByDateAndTurnoAndUser(Date date, Turno turno, User user);
}