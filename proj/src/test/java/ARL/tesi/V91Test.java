package ARL.tesi;

import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.Role;
import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.modelobject.User;
import ARL.tesi.util.controls.V21;
import ARL.tesi.util.controls.V91;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class V91Test {
    User u1;

    List<Assegnazione> assegnazioniSenza;
    List<Assegnazione> assegnazioniNotturno;
    V91 v91;

    @BeforeEach
    void init(){
        Role r = new Role("ROLE_USER", "Autista di linea");
        u1 = new User("utente", "Test", "u1", "password",r);
        assegnazioniSenza = new ArrayList<>();
        assegnazioniNotturno = new ArrayList<>();
        String dt = "2020-01-01";  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i =0; i<10; i++){
            Shiffts s = new Shiffts(String.valueOf(i), true, "Mattina","LU-VE", 480,0,0, LocalTime.of(6,30), LocalTime.of(18,0));
            assegnazioniSenza.add(new Assegnazione(u1, s, c.getTime()));
            c.add(Calendar.DATE, 1);
        }


        v91 = new V91();
    }

    @Test
    void executeTest(){
        assertEquals(v91.execute(assegnazioniSenza), 0);
        assegnazioniNotturno.addAll(assegnazioniSenza);
        assegnazioniNotturno.get(0).getTurno().setEndTime(LocalTime.of(1,30));
        assertEquals(v91.execute(assegnazioniNotturno), 90);
    }
}
