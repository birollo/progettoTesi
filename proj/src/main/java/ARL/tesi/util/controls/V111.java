package ARL.tesi.util.controls;

import ARL.tesi.modelobject.Assegnazione;

import java.util.List;

public class V111 implements SoftCostraint{

    public int execute(List<Assegnazione> assegnaziones){
        int count =0;
        for (Assegnazione a:assegnaziones){
            if (a.getTurno().getName().equals("V")){
                count++;
            }
        }
        return count;
    }
}
