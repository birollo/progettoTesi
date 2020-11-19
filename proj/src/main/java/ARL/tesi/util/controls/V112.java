package ARL.tesi.util.controls;

import ARL.tesi.modelobject.Assegnazione;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


//todo: magari aggiungere metodo per stringa di errore comune a tutti i Vxxx
public class V112 {

    public String execute(List<Assegnazione> assegnaziones){
        int count=0;
        for (Assegnazione a: assegnaziones){
            if (count == 6 && a.getTurno().getName().equals("R")){
                return "OK";
            }
            if (a.getTurno().getName().equals("V")){
                count ++;
                if (count == 7){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(a.getDate());
                    calendar.add(Calendar.DAY_OF_MONTH, -7);
                    Date d = calendar.getTime();
                    return "L'autista" + assegnaziones.get(0).getUser().getName() + " " +
                            assegnaziones.get(0).getUser().getSurname() + " ha 7 giorni consecutivi di vacanza senza riposo nel periodo dal" +
                            d + " al " + a.getDate();
                }
            }else {
                count = 0;
            }
        }
        return "OK";
    }


}

