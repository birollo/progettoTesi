package ARL.tesi;


import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.Role;
import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.modelobject.User;
import ARL.tesi.repository.RoleRepository;
import ARL.tesi.repository.ShifftsRepository;
import ARL.tesi.service.AssegnazioneService;
import ARL.tesi.service.PersonService;
import ARL.tesi.service.ShifftService;
import ARL.tesi.util.controls.V01;
import com.mysql.cj.x.protobuf.MysqlxCursor;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class V01Test {


    User u1;

    List<Assegnazione> assegnazioneListCorretta;
    List<Assegnazione> assegnazioneListErronea;
    V01 v01;

    @BeforeEach
    void init() throws ParseException {
        Role r = new Role("ROLE_USER", "Autista di linea");
        u1 = new User("utenteTest1", "Test", "u1", "password",r);

        assegnazioneListCorretta = new ArrayList<>();
        assegnazioneListErronea = new ArrayList<>();
        String dt = "2020-01-01";  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i =0; i<10; i++){
            Shiffts s = new Shiffts(String.valueOf(i), true, "Mattina","LU-VE", 0,0,0, LocalTime.of(0,0), LocalTime.of(0,0));
            assegnazioneListCorretta.add(new Assegnazione(u1, s, c.getTime()));
            c.add(Calendar.DATE, 1);
        }
        assegnazioneListErronea.addAll(assegnazioneListCorretta);
        assegnazioneListErronea.remove(5);

        v01 = new V01();
    }

    @Test
    void executeTest(){
        String ok = "OK";
        String errore = "L'autista utenteTest1 Test non ha un turno assegnato il 06-01-2020";
        String testOK = v01.execute(assegnazioneListCorretta);
        String testErrore = v01.execute(assegnazioneListErronea);
        assertEquals(ok, testOK);
        assertEquals(errore, testErrore);

    }

}
