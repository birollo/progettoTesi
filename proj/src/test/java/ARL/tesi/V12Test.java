package ARL.tesi;

import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.Role;
import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.modelobject.User;

import ARL.tesi.util.controls.V12;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class V12Test {

    User u1;

    List<Assegnazione> assegnazioneListCorretta;
    List<Assegnazione> assegnazioneListErronea;
    V12 v12;

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
            Shiffts s = new Shiffts(String.valueOf(i), true, "Mattina","LU-VE", 480,0,0, LocalTime.of(0,0), LocalTime.of(0,0));
            assegnazioneListCorretta.add(new Assegnazione(u1, s, c.getTime()));
            c.add(Calendar.DATE, 1);
        }


        v12 = new V12();
    }

    @Test
    void executeTest(){
        String ok = "OK";
        String errore = "Test utenteTest1 nei giorni dal 01-01-2020 al 07-01-2020 ha una media di lavoro superiore a 9.";
        String testOK = v12.execute(assegnazioneListCorretta);
        assegnazioneListErronea.addAll(assegnazioneListCorretta);
        assegnazioneListErronea.get(0).getTurno().setDuration(600);
        assegnazioneListErronea.get(1).getTurno().setDuration(600);
        assegnazioneListErronea.get(2).getTurno().setDuration(600);
        assegnazioneListErronea.get(3).getTurno().setDuration(600);
        String testErrore = v12.execute(assegnazioneListErronea);
        assertEquals(ok, testOK);
        assertEquals(errore, testErrore);

    }


}
