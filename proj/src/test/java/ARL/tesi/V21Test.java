package ARL.tesi;

import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.Role;
import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.modelobject.User;
import ARL.tesi.util.controls.V21;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class V21Test {


    User u1;

    List<Assegnazione> assegnazioniSenza;
    List<Assegnazione> assegnazioniNotturno;
    V21 v21;

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


        v21 = new V21();
    }

    @Test
    void executeTest(){
        assertEquals(v21.execute(assegnazioniSenza), 0);
        assegnazioniNotturno.addAll(assegnazioniSenza);
        assegnazioniNotturno.get(0).getTurno().setEndTime(LocalTime.of(1,30));
        assertEquals(v21.execute(assegnazioniNotturno), 90);
    }
}
