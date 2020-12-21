package ARL.tesi;


import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.Role;
import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.modelobject.User;
import ARL.tesi.util.controls.V11;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class V11Test {

    User u1;

    List<Assegnazione> assegnazioneListCorretta;
    List<Assegnazione> assegnazioneListErronea;

    V11 v11;

    @BeforeEach
    void init() {
        Role r = new Role("ROLE_USER", "Autista di linea");
        u1 = new User("utenteTest1", "Test", "u1", "password", r);

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

        for (int i = 0; i < 10; i++) {
            Shiffts s = new Shiffts(String.valueOf(i), true, "Mattina","LU-VE", 420, 0, 0, LocalTime.of(0, 0), LocalTime.of(0, 0));
            assegnazioneListCorretta.add(new Assegnazione(u1, s, c.getTime()));
            c.add(Calendar.DATE, 1);
        }

        v11 = new V11();
    }

    @Test
    void executeTest() {
        assertEquals("OK", v11.execute(assegnazioneListCorretta) );
        Shiffts s2 = new Shiffts("lungo", true, "Mattina","LU-VE", 800, 0, 0, LocalTime.of(0, 0), LocalTime.of(0, 0));
        assegnazioneListErronea.addAll(assegnazioneListCorretta);
        assegnazioneListErronea.get(0).setTurno(s2);
        assertEquals("Test utenteTest1 ha una durata media giornaliera superiore a 7 ore.", v11.execute(assegnazioneListErronea));
    }
}

