package ARL.tesi.util.controls;

import ARL.tesi.modelobject.Assegnazione;

import java.time.LocalTime;
import java.util.List;

public class V91 {
    public int execute(List<Assegnazione> assegnaziones) {
        int count = 0;
        for (Assegnazione a : assegnaziones) {
            LocalTime midNight = LocalTime.parse("00:00:00");
            LocalTime six = LocalTime.parse("04:00:00");
            if (a.getTurno().getEndTime().isAfter(midNight) && a.getTurno().getEndTime().isBefore(six)) {
                String endString = String.valueOf(a.getTurno().getEndTime());
                int hours = Integer.valueOf(endString.split(":")[0]);
                int minutes = Integer.valueOf(endString.split(":")[1]);
                count += (hours * 60) + minutes;
            }
        }
        return count;
    }
}
