package ARL.tesi.service;
import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.Role;
import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.modelobject.User;
import ARL.tesi.repository.AssegnazioneRepository;
import ARL.tesi.repository.RoleRepository;
import ARL.tesi.repository.ShifftsRepository;
import ARL.tesi.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;


import java.time.LocalTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class PersonServiceTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private ShifftsRepository turnoRepo;

    @Mock
    private RoleRepository roleRepo;

    @Mock
    private AssegnazioneRepository assegnazioneRepo;

    @InjectMocks
    private PersonService service;

    //////////////////////// User /////////////////////////////////
    @Test
    void saveUserTest() {
        Role role=new Role("ROLE_TEST","Test");
        User alex = new User("Alex","Rossi","Alex123", "1234",role);
        when(userRepo.save(Mockito.any(User.class))).then(returnsFirstArg());
        User savedUser = service.addUser(alex);
        assertEquals(savedUser.getUsername(),alex.getUsername());
    }

    @Test
    void getUsersTest() {
        PersonService s=Mockito.mock(PersonService.class);
        s.getUsers();
        Mockito.verify(s, Mockito.times(1)).getUsers();
    }

    @Test
    void findUserByUsernameTest() {
        PersonService s=Mockito.mock(PersonService.class);
        String username="Alex";
        s.findUserByUsername(username);
        Mockito.verify(s, Mockito.times(1)).findUserByUsername(username);
    }

    @Test
    void getUserTest() {
        PersonService s=Mockito.mock(PersonService.class);
        int id=1;
        s.getUser(id);
        Mockito.verify(s, Mockito.times(1)).getUser(id);
    }

    @Test
    void getUserByRoleTest() {
        PersonService s=Mockito.mock(PersonService.class);
        String role="Test";
        s.getUsersByRole(role);
        Mockito.verify(s, Mockito.times(1)).getUsersByRole(role);
    }

    @Test
    void getAuthenticatedUserTest() {
        PersonService s=Mockito.mock(PersonService.class);
        s.getAuthenticatedUser();
        Mockito.verify(s, Mockito.times(1)).getAuthenticatedUser();
    }

    @Test
    void deleteUserTest() {
        User user=Mockito.mock(User.class);
        PersonService s=Mockito.mock(PersonService.class);
        s.delete(user);
        Mockito.verify(s, Mockito.times(1)).delete(user);
    }

    //////////////////////// Turno /////////////////////////////////
    @Test
    void saveTurnoTest() {
        Shiffts turno=new Shiffts("122",false,"Mattino","lu-ve" ,570,600,0, LocalTime.of(9,30),LocalTime.of(10,00));
        when(turnoRepo.save(Mockito.any(Shiffts.class))).then(returnsFirstArg());
        Shiffts savedTurno = service.addTurno(turno);
        assertEquals(savedTurno.getName(),turno.getName());
    }

    @Test
    void getTurniTest() {
        PersonService s=Mockito.mock(PersonService.class);
        s.getTurni();
        Mockito.verify(s, Mockito.times(1)).getTurni();
    }

    @Test
    void getTurnoTest() {
        PersonService s=Mockito.mock(PersonService.class);
        int id=1;
        s.getTurno(id);
        Mockito.verify(s, Mockito.times(1)).getTurno(id);
    }

    @Test
    void getTurniByUserTest() {
        PersonService s=Mockito.mock(PersonService.class);
        User user=Mockito.mock(User.class);
        s.getTurniByUser(user);
        Mockito.verify(s, Mockito.times(1)).getTurniByUser(user);
    }

    @Test
    void getTotalMinutes() {
        PersonService s=Mockito.mock(PersonService.class);
        User user=Mockito.mock(User.class);
        s.getTotalMinutes(user);
        Mockito.verify(s, Mockito.times(1)).getTotalMinutes(user);
    }

    @Test
    void deleteTurnoTest() {
        Shiffts turno=Mockito.mock(Shiffts.class);
        PersonService s=Mockito.mock(PersonService.class);
        s.deleteTurno(turno);
        Mockito.verify(s, Mockito.times(1)).deleteTurno(turno);
    }

    //////////////////////// Role /////////////////////////////////
    @Test
    void saveRoleTest() {
        Role role=new Role("ROLE_TEST","Test");
        when(roleRepo.save(Mockito.any(Role.class))).then(returnsFirstArg());
        Role savedRole = service.addRole(role);
        assertEquals(savedRole.getName(),role.getName());
    }

    @Test
    void getRolesTest() {
        PersonService s=Mockito.mock(PersonService.class);
        s.getRoles();
        Mockito.verify(s, Mockito.times(1)).getRoles();
    }

    @Test
    void deleteRoleTest() {
        Role role=Mockito.mock(Role.class);
        PersonService s=Mockito.mock(PersonService.class);
        s.deleteRole(role);
        Mockito.verify(s, Mockito.times(1)).deleteRole(role);
    }

    //////////////////////// Assegnazione /////////////////////////////////
    @Test
    void saveAssegnazioneTest() {
        Role role=new Role("ROLE_TEST","Test");
        User alex = new User("Alex","Rossi","Alex123", "1234",role);
        Shiffts turno=new Shiffts("122",false,"Mattino","lu-ve" ,570,600,0, LocalTime.of(9,30),LocalTime.of(10,00));
        Assegnazione assegnazione=new Assegnazione(alex,turno,new Date());
        when(assegnazioneRepo.save(Mockito.any(Assegnazione.class))).then(returnsFirstArg());
        Assegnazione savedAssegnazione = service.addAssegnazione(assegnazione);
        assertEquals(savedAssegnazione.getUser().getUsername(),alex.getUsername());
        assertEquals(savedAssegnazione.getTurno().getName(),turno.getName());
    }

    @Test
    void getAssegnazioniTest() {
        PersonService s=Mockito.mock(PersonService.class);
        s.getAssegnazioni();
        Mockito.verify(s, Mockito.times(1)).getAssegnazioni();
    }

    @Test
    void getAssegnazioniByUserTest() {
        PersonService s=Mockito.mock(PersonService.class);
        User user=Mockito.mock(User.class);
        s.getAssegnazioniByUser(user);
        Mockito.verify(s, Mockito.times(1)).getAssegnazioniByUser(user);
    }

    @Test
    void deleteAssegnazioneTest() {
        Assegnazione assegnazione=Mockito.mock(Assegnazione.class);
        PersonService s=Mockito.mock(PersonService.class);
        s.delete(assegnazione);
        Mockito.verify(s, Mockito.times(1)).delete(assegnazione);
    }
}