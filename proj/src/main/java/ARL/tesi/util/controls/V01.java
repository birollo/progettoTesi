package ARL.tesi.util.controls;


import ARL.tesi.modelobject.Assegnazione;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

//in ongi giorno deve esserci un turno
public class V01 {

    public String execute(List<Assegnazione> assegnazioneList){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(assegnazioneList.get(0).getDate());
//        calendar.add(Calendar.DATE,-1);
        for (int i=0; i<assegnazioneList.size(); i ++){
            String s1 = sdf.format(calendar.getTime());
            String s2 = sdf.format(assegnazioneList.get(i).getDate());
            String c = "cio";
            if (s1.equals(s2)){

            }else {
                Calendar cal = Calendar.getInstance();
                cal.setTime(assegnazioneList.get(i).getDate());
                cal.add(Calendar.DATE,-1);
                String dt = sdf.format(cal.getTime());
                return "L'autista " + assegnazioneList.get(0).getUser().getName() + " " +
                        assegnazioneList.get(0).getUser().getSurname() + " non ha un turno assegnato il " + dt;
            }
            calendar.add(Calendar.DATE, 1);
        }
        return "OK";
    }
}
