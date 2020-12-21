package ARL.tesi.service;


import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.User;
import ARL.tesi.repository.AssegnazioneRepository;
import com.aspose.cells.IFilePathProvider;
import org.apache.poi.ss.usermodel.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AssegnazioneService {

    @Autowired
    AssegnazioneRepository assegnazioneRepository;

    public void save(Assegnazione a) {
        if (assegnazioneRepository.getAssegnazioneByUserAndDate(a.getUser(), a.getDate()) == null) {
            assegnazioneRepository.save(a);
        } else { //sostituzione
            assegnazioneRepository.delete(assegnazioneRepository.getAssegnazioneByUserAndDate(a.getUser(), a.getDate()));
            assegnazioneRepository.save(a);
        }

    }

    public void delete(Assegnazione a){
        if (assegnazioneRepository.getAssegnazioneByUserAndDate(a.getUser(), a.getDate()) == null){

        }else {
            assegnazioneRepository.delete(a);
        }

    }

    public void deleteByUserAndDare(User u, Date d){
        if (assegnazioneRepository.getAssegnazioneByUserAndDate(u, d) == null){

        }else {
            Assegnazione a = getByUserAndDate(u,d);
            assegnazioneRepository.delete(a);
        }

    }

    public Assegnazione getByUserAndDate(User u, Date d) {
        return assegnazioneRepository.getAssegnazioneByUserAndDate(u, d);
    }

    public List<Assegnazione> getOneMonthByUser(User u, Date d) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(d);
        end.setTime(d);
        start.set(Calendar.DAY_OF_MONTH, 1);
        end.set(Calendar.DATE, start.getActualMaximum(Calendar.DATE));
        String startToParse = sdf.format(start.getTime());  // dt is now the new date
        String endToParse = sdf.format(end.getTime());
        Date date1 = sdf.parse(startToParse);
        Date date2 = sdf.parse(endToParse);

        return assegnazioneRepository.findByUserAndDateBetweenOrderByDate(u, date1, date2);


    }

    public Assegnazione getByDate(List<Assegnazione> assegnazioni, Date date) {
        for (Assegnazione a : assegnazioni) {
            if (a.getDate() == date) {
                return a;
            }
        }
        return null;
    }
}
