package ARL.tesi;

import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.Role;
import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.modelobject.User;
import ARL.tesi.util.Scheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SchedulerTest {

    public SchedulerTest() {
    }

    List<Shiffts> shiffts = new ArrayList<>();
    List<User> users = new ArrayList<>();
    User u1;
    @Autowired
    Scheduler scheduler;

    private List<LocalDate> month;

    @BeforeEach
    void init(){
        month = new ArrayList<>();
        Role r = new Role("ROLE_USER", "Autista di linea");
        u1 = new User("utenteTest1", "Test", "u1", "password",r);
        users.add(u1);

        String dt = "2020-11-16";  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dt);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH)+1;
        int d = c.get(Calendar.DAY_OF_MONTH);
        LocalDate localDate = LocalDate.of(y,m,d);




        for (int i =0; i<10; i++){
            Shiffts s = new Shiffts(String.valueOf(i), true, "Mattina","LU-VE", 0,0,0, LocalTime.of(0,0), LocalTime.of(0,0));
            shiffts.add(s);
            d++;
            month.add(LocalDate.of(y,m,d));
        }

    }

    @Test
    void SchedulerTest(){
        scheduler.scheduler(month, shiffts, users);
    }
}
