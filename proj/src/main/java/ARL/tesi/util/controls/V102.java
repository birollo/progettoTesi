package ARL.tesi.util.controls;


import ARL.tesi.modelobject.Assegnazione;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

//all interno di un mese almeno un turno di riposo deve cadere di domenica
public class V102 {


    public String execute(List<Assegnazione> assegnaziones){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        int count = 0;

        for (Assegnazione a : assegnaziones){
            Calendar cal = Calendar.getInstance();
            cal.setTime(a.getDate());
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && a.getTurno().getName().equals("R")){
                count ++;
            }


        }
        if (count <= 0){
            Calendar cal = Calendar.getInstance();
            cal.setTime(assegnaziones.get(0).getDate());
//            cal.add(Calendar.DATE,-1);
            String d1 = sdf.format(cal.getTime());
            cal.setTime(assegnaziones.get(assegnaziones.size()-1).getDate());
            String d2 = sdf.format(cal.getTime());
            return "L'autista " + assegnaziones.get(0).getUser().getName() + " " + assegnaziones.get(0).getUser().getSurname() +
                    " nel mese dal " + d1 + " al " + d2 +
                    " non ha almeno un turno di riposo che cade di domenica";
        }
        return "OK";

    }
}
