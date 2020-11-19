package ARL.tesi.util.controls;


import ARL.tesi.modelobject.Assegnazione;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

//la durata del lavoro non deve essere superiore a 9 ore nella media di 7 giorni
public class V12 {

    public String execute(List<Assegnazione> assegnaziones) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        for (int i = 6; i < assegnaziones.size(); i++) {

            int count = 0;
            for (int k = i - 6; k <= i; k++) {
                count += assegnaziones.get(k).getTurno().getDuration();
            }
            float avg = (float) count / 7;

            if (avg > 9 * 60) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(assegnaziones.get(i-6).getDate());
                String d1 = sdf.format(cal.getTime());
                cal.setTime(assegnaziones.get(i).getDate());
                String d2 = sdf.format(cal.getTime());
                return "L'autista " + assegnaziones.get(0).getUser().getName() +
                        " " + assegnaziones.get(0).getUser().getSurname() + " nei giorni dal " +
                        d1 + " al " + d2 +
                        " ha una media di lavoro superiore a 9.";
            }

        }

        return "OK";

    }


}
