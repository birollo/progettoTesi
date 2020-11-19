package ARL.tesi;

import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.Role;
import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.modelobject.User;
import ARL.tesi.util.controls.V111;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class V111Test {

    V111 v111;

    User u1;

    List<Assegnazione> assegnazioneListSenzaV;
    List<Assegnazione> assegnazioneListCon2V;

    @BeforeEach
    void init(){
        Role r = new Role("ROLE_USER", "Autista di linea");
        u1 = new User("utenteTest1", "Test", "u1", "password",r);

        assegnazioneListSenzaV = new ArrayList<>();
        assegnazioneListCon2V = new ArrayList<>();
        String dt = "2020-01-01";  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i =0; i<10; i++){
            Shiffts s = new Shiffts(String.valueOf(i), true, "Mattina", "LU-VE",0,0,0, LocalTime.of(0,0), LocalTime.of(0,0));
            assegnazioneListSenzaV.add(new Assegnazione(u1, s, c.getTime()));
            c.add(Calendar.DATE, 1);
        }
        v111 = new V111();
    }

    @Test
    void executeTest(){
        assertEquals(0, v111.execute(assegnazioneListSenzaV));
        Shiffts sV = new Shiffts("V", true, "Mattina","LU-VE", 0,0,0, LocalTime.of(0,0), LocalTime.of(0,0));
        assegnazioneListCon2V.addAll(assegnazioneListSenzaV);
        assegnazioneListCon2V.get(0).setTurno(sV);
        assegnazioneListCon2V.get(1).setTurno(sV);
        assertEquals(2, v111.execute(assegnazioneListCon2V));
    }


}
