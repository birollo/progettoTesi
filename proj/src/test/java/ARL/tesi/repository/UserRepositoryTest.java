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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ShifftsRepository turnoRepo;

     @Test
    void getUsersByRoleTest() {
        Role role = new Role("ROLE_TEST", "Test");
        User alex = new User("Alex", "Rossi", "Alex123", "1234", role);
        entityManager.persist(role);
        entityManager.persist(alex);
        entityManager.flush();
        // given
        Shiffts turno =new Shiffts("122",false,"Mattino","lu-ve" ,570,600,0, LocalTime.of(9,30),LocalTime.of(10,00));
         Assegnazione a = new Assegnazione(alex, turno, new Date());
        entityManager.persist(turno);
        entityManager.flush();
        // when
        Shiffts found = turnoRepo.getByName("122");
        // then
        assertEquals(found.getName(),turno.getName());
    }
}