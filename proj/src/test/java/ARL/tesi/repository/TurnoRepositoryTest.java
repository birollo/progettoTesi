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
class TurnoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ShifftsRepository turnoRepo;

    @Test
    void getUsersByRoleTest() {
        Role role = new Role("ROLE_TEST", "Test");
        User alex = new User("Alex", "Rossi", "Alex123", "1234", role);
//        entityManager.persist(role);
//        entityManager.persist(alex);
//        entityManager.flush();
        // given
        Shiffts turno = new Shiffts("122",false,"Mattino","lu-ve" ,570,600,0, LocalTime.of(9,30), LocalTime.of(10,00));
//        turno.setUsers(alex);
//        entityManager.persist(turno);
//        entityManager.flush();
        // when
//        Turno found = turnoRepo.findTurnosByUser(alex).get(0);
        // then
        Assegnazione a = new Assegnazione(alex, turno, new Date());

        assertEquals(turno.getName(),a.getTurno().getName());
    }
}