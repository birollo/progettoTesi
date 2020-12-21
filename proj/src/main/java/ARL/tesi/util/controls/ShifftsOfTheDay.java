package ARL.tesi.util.controls;


import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.modelobject.User;
import ARL.tesi.service.AssegnazioneService;
import ARL.tesi.util.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownServiceException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShifftsOfTheDay {

    @Autowired
    Scheduler scheduler;

    @Autowired
    AssegnazioneService assegnazioneService;

    public List<String> execute(List<User> autisti, LocalDate d, List<Shiffts> allShiffts){
        List<Shiffts> shifftsOfTheDay = scheduler.getShifftsByDate(d,allShiffts);
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //per ogni autista
        List<String> response = new ArrayList<>();

        while (shifftsOfTheDay.size()>0){

            for (User u: autisti){
                if (u.getTurni().size() != 0){
                    Date date = Date.from(d.atStartOfDay(defaultZoneId).toInstant());
                    Shiffts s = assegnazioneService.getByUserAndDate(u,date).getTurno();
                    if (shifftsOfTheDay.contains(s)){
                        shifftsOfTheDay.remove(s);
                    }else {
                        //turno che non ci deve essere
                        if (s.getName().equals("R")){

                        }else {
                            String r = u.getSurname() + " " + u.getName() + " ha assegnato il turno " + s.getName() + " che non ci deve essere";
                            response.add(r);
                        }
                    }
                }

            }
            //questo giorno mancano dei turni
            if (shifftsOfTheDay.size() > 0){
                for (Shiffts s: shifftsOfTheDay){
                    response.add("Manca il turno " + s.getName());
                }
                return response;
            }



        }

        response.add("OK");
        return response;

    }



}
