package ARL.tesi;

import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.Role;
import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.modelobject.User;
import ARL.tesi.util.controls.V01;
import ARL.tesi.util.controls.V112;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class V112Test {

    User u1;

    List<Assegnazione> assegnazioneListCorretta;
    List<Assegnazione> assegnazioneListErronea;

    V112 v112;

    @BeforeEach
    void init(){
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
            Shiffts s = new Shiffts("V", true, "Mattina","LU-VE", 0,0,0, LocalTime.of(0,0), LocalTime.of(0,0));
            assegnazioneListCorretta.add(new Assegnazione(u1, s, c.getTime()));
            c.add(Calendar.DATE, 1);
        }



        v112 = new V112();

    }

    @Test
    void executeTest(){
        assegnazioneListErronea.addAll(assegnazioneListCorretta);
        assertEquals(v112.execute(assegnazioneListErronea), "L'autistautenteTest1 Test ha 7 giorni consecutivi di vacanza senza riposo nel periodo dalTue Dec 31 00:00:00 CET 2019 al Tue Jan 07 00:00:00 CET 2020");
        assegnazioneListCorretta.get(6).getTurno().setName("R");
        assertEquals(v112.execute(assegnazioneListCorretta), "OK");
    }
}
