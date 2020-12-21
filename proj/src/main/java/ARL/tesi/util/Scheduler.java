package ARL.tesi.util;

import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.modelobject.User;
import ARL.tesi.service.AssegnazioneService;
import ARL.tesi.service.ShifftService;
import ARL.tesi.util.controls.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class Scheduler {

    @Autowired
    private ShifftService shifftService;

    @Autowired
    private Executor executor;

    @Autowired
    private AssegnazioneService assegnazioneService;


//todo: provare ad non interagire mai con il db
    public List<List<Assegnazione>> scheduler(List<LocalDate> month, List<Shiffts> shiffts, List<User> autisti) {
        List<List<Assegnazione>> pianificazione = new ArrayList<>();
        List<List<Shiffts>> shifftsMatrix = getShifftsMatrixOfTheMonth(autisti,month, shiffts);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        //per ogni utente
        for (User u: autisti){
            List<Assegnazione> assegnaziones = new ArrayList<>();
            //per ogni giorno del mese
            for (int i = 0; i < month.size(); i++){
                if (assegnazioneService.getByUserAndDate(u,Date.from(month.get(i).atStartOfDay(defaultZoneId).toInstant()))  == null){
                    //picco un turno random
                    Random rand = new Random();
//
                    Shiffts randomShiffs = shifftsMatrix.get(i).get(rand.nextInt(shifftsMatrix.get(i).size()));
//                    //default time zone

//                    Shiffts randomShiffs =  shifftsMatrix.get(i).get(0);

                    Date date = Date.from(month.get(i).atStartOfDay(defaultZoneId).toInstant());
                    Assegnazione a = new Assegnazione(u, randomShiffs,date);
                    assegnaziones.add(a);
                    assegnazioneService.save(a);
                    boolean isOK = checkAdd(date,u);
                    //se e tutto OK rimuovo il turrno dalla lista
                    if (isOK){
                        //tutto a posto
                        if(shifftsMatrix.get(i).remove(randomShiffs)){

                        }else {
                            System.out.println("turno" + randomShiffs.getName() + " non eliminato");
                        }
                        //se non va bene assegno di default un R e lo toglo dalla lista
                    }else {
                        assegnazioneService.delete(a);
                        Shiffts s = shifftService.getLastByName("R");
                        Assegnazione assegnazione = new Assegnazione(u, s, date);
                        assegnazioneService.save(assegnazione);
                        shifftsMatrix.get(i).remove(s);
                    }
                }else {
                    continue;
                }


            }
            System.out.println("fatto " + u.getName());
//            u.getTurni().addAll(assegnaziones);


        }

        for (List<Shiffts> ls: shifftsMatrix){
            if (ls.size() != 0){
                System.out.println("lista piena");
            }
        }


        return pianificazione;
    }

    boolean checkAdd(Date d, User u){
        List<String> responseCheck = executor.executeAllCostrains(u,d);
        String finalResponse = response(responseCheck);
        if (finalResponse.equals("OK")){
            return true;
        }else {
            return false;
        }
    }

    String response(List<String> in){

        String response = "OK";
        for (String s: in){
            if (s.equals(response)){

            }else {
                return s;
            }
        }
        return response;
    }



    public List<List<Shiffts>> getShifftsMatrixOfTheMonth(List<User> autisti, List<LocalDate> month, List<Shiffts> shiffts){
        List<List<Shiffts>> shifftsMatrix = new ArrayList<>();
        int userSize = autisti.size();
        Shiffts shifftsR = shifftService.getLastByName("R");
        for (LocalDate d : month) {
            List<Shiffts> shifftsOfTheDay = getShifftsByDate(d, shiffts);
            //todo: e un <= qua ?
            for (int i = shifftsOfTheDay.size(); i < userSize; i++){
                shifftsOfTheDay.add(shifftsR);
            }

            shifftsMatrix.add(shifftsOfTheDay);

        }
        return shifftsMatrix;
    }

    //todo: creare lista vacanze e togliere porcata
    public List<Shiffts> getShifftsByDate(LocalDate d, List<Shiffts> shiffts) {
        List<Shiffts> shifftsOfTheDay = new ArrayList<>();
        List<LocalDate> holidays = new ArrayList<>();
        String day = d.getDayOfWeek().toString();
//        List<String> dayOfTheWeek = new ArrayList<>();
//        List<String> dayOfTheWeekEnd = new ArrayList<>();
//        dayOfTheWeek.add("MONDAY");
//        dayOfTheWeek.add("TUESDAY");
//        dayOfTheWeek.add("WEDNESDAY");
//        dayOfTheWeek.add("THURSDAY");
//        dayOfTheWeek.add("FRIDAY");
//        dayOfTheWeekEnd.add("SATURDAY");
//        dayOfTheWeekEnd.add("SUNDAY");

        //todo: manca parte di branching tra SC e SA

        for (Shiffts s: shiffts){
            if (s.getDays().contains(day) && s.isSchool()){
                shifftsOfTheDay.add(s);
            }
        }


        return shifftsOfTheDay;
    }
}
