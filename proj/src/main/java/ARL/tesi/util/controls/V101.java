package ARL.tesi.util.controls;

import ARL.tesi.modelobject.Assegnazione;

import java.util.List;

public class V101 {

    public int execute(List<Assegnazione> assegnaziones){
        int count = 0;
        for (Assegnazione a: assegnaziones){
            if (a.getTurno().getName().equals("R")){
                count++;
            }
        }
        return count;
    }
}
