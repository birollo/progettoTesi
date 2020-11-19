package ARL.tesi;

import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.Role;
import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.modelobject.User;
import ARL.tesi.util.controls.V102;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class V102Test {
    User u1;

    List<Assegnazione> assegnazioneListCorretta;
    List<Assegnazione> assegnazioneListErronea;

    V102 v102;

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
            Shiffts s = new Shiffts(String.valueOf(i), true, "Mattina","LU-VE", 0,0,0, LocalTime.of(0,0), LocalTime.of(0,0));
            assegnazioneListCorretta.add(new Assegnazione(u1, s, c.getTime()));
            c.add(Calendar.DATE, 1);
        }
        assegnazioneListErronea.addAll(assegnazioneListCorretta);



        v102 = new V102();

    }

    @Test
    void executeTest(){
        assertEquals(v102.execute(assegnazioneListErronea), "L'autista utenteTest1 Test nel mese dal 01-01-2020 al 10-01-2020 non ha almeno un turno di riposo che cade di domenica");
        assegnazioneListCorretta.get(4).getTurno().setName("R");
        assertEquals(v102.execute(assegnazioneListCorretta), "OK");
    }

}
