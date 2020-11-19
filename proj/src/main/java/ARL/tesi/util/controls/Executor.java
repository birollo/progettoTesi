package ARL.tesi.util.controls;

import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.User;
import ARL.tesi.service.AssegnazioneService;
import ARL.tesi.service.PersonService;
import org.aspectj.weaver.patterns.PerSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class Executor {

    @Autowired
    private AssegnazioneService assegnazioneService;

    public List<String> executeAllCostrains(User user, Date date){
        List<String> responses = new ArrayList<>();
        List<Assegnazione> assegnaziones;
        V01 v01 = new V01();
        V11 v11 = new V11();
        V12 v12 = new V12();
        V102 v102 = new V102();
        V112 v112 = new V112();
        try {
            assegnaziones = assegnazioneService.getOneMonthByUser(user, date);
            //todo: parametrizzare 7 o esecuzioni a dipendenza dellla lunghezza
            if (assegnaziones.size() != 0 && assegnaziones.size() > 6 ){
                String sV01 = v01.execute(assegnaziones);
                String sV11 = v11.execute(assegnaziones);
                String sV12 = v12.execute(assegnaziones);
                String sV102 = v102.execute(assegnaziones);
                String sV112 = v112.execute(assegnaziones);
                responses.add(sV01);
                responses.add(sV11);
                responses.add(sV12);
                responses.add(sV102);
                responses.add(sV112);
            }else {
                responses = new ArrayList<>();
                responses.add("OK");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return responses;
    }

    public List<Integer> executeAllCalculation(User user, Date date){
        List<Integer> responses = new ArrayList<>();
        List<Assegnazione> assegnaziones;
        V21 v21 = new V21();
        V91 v91 = new V91();
        V101 v101 = new V101();
        V111 v111 = new V111();
        try {
            assegnaziones = assegnazioneService.getOneMonthByUser(user, date);
            int sV21 = v21.execute(assegnaziones);
            int sV91 = v91.execute(assegnaziones);
            int sV101 = v101.execute(assegnaziones);
            int sV111 = v111.execute(assegnaziones);
            responses.add(sV21);
            responses.add(sV91);
            responses.add(sV101);
            responses.add(sV111);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return responses;
    }
}
