package ARL.tesi.util.controls;

import ARL.tesi.modelobject.Assegnazione;

import java.util.List;

public class MonthDurationCount {

    public int execute(List<Assegnazione> assegnaziones){
        int count = 0;
        for (Assegnazione a: assegnaziones){
            count += a.getTurno().getValue();
        }
        return count;
    }
}
