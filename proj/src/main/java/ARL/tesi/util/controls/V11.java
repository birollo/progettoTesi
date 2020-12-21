package ARL.tesi.util.controls;

import ARL.tesi.modelobject.Assegnazione;

import java.util.List;

public class V11 implements HardCostraint{

    public String execute(List<Assegnazione> assegnaziones){
        int countDuration = 0;
        int count = 0;
        for (Assegnazione a: assegnaziones){
            countDuration+= a.getTurno().getDuration();
            count++;
        }
        float avg = (float) countDuration/count;
        if (avg > 7*60){
            return assegnaziones.get(0).getUser().getSurname() + " " + assegnaziones.get(0).getUser().getName() +
                    " ha una durata media giornaliera superiore a 7 ore.";
        }else {
            return "OK";
        }
    }
}
