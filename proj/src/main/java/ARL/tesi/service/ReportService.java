package ARL.tesi.service;


import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.Reports;
import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.modelobject.User;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    AssegnazioneService assegnazioneService;

    public Reports createReport(User u, Date d){
        List<Assegnazione> assegnaziones;
        try {
            assegnaziones = assegnazioneService.getOneMonthByUser(u,d);
        } catch (ParseException e) {
            assegnaziones = new ArrayList<>();
            e.printStackTrace();

        }
        List<Date> dates = new ArrayList<>();
        List<String> shifftsName = new ArrayList<>();
        List<LocalTime> shifftsStart = new ArrayList<>();
        List<LocalTime> shifftsEnd = new ArrayList<>();
        List<Integer> durata = new ArrayList<>();
        List<Integer> valore = new ArrayList<>();
        List<Integer> totaleMinuti = new ArrayList<>();

        for (Assegnazione a : assegnaziones){
            dates.add(a.getDate());
            shifftsName.add(a.getTurno().getName());
            shifftsStart.add(a.getTurno().getStartTime());
            shifftsEnd.add(a.getTurno().getEndTime());
            durata.add(a.getTurno().getDuration());
            valore.add(a.getTurno().getValue());
            totaleMinuti.add(a.getTurno().getDuration());
        }
        return new Reports(dates,shifftsName,shifftsStart,shifftsEnd, durata, valore, totaleMinuti);
    }
}
