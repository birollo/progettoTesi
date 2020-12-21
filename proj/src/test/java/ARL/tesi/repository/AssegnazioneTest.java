package ARL.tesi.repository;

import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.Role;
import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.modelobject.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


import java.time.LocalTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
class AssegnazioneRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AssegnazioneRepository assegnazioneRepo;

    @Test
    void getUsersByRoleTest() {
        Role role = new Role("ROLE_TEST", "Test");
        User alex = new User("Alex", "Rossi", "Alex123", "1234", role);
        Shiffts turno = new Shiffts("122",false,"Mattino","lu-ve" ,570,600,0, LocalTime.of(9,30),LocalTime.of(10,00));
        entityManager.persist(role);
        entityManager.persist(alex);
        entityManager.persist(turno);
        entityManager.flush();
        // given
        Assegnazione assegnazione = new Assegnazione(alex,turno,new Date());
        entityManager.persist(assegnazione);
        entityManager.flush();

        // when
        Assegnazione found = assegnazioneRepo.findAssegnazioniByUser(alex).get(0);

        // then
        assertEquals(found.getUser().getUsername(),assegnazione.getUser().getUsername());
    }
}