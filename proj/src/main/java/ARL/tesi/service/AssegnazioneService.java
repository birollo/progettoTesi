package ARL.tesi.service;


import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.User;
import ARL.tesi.repository.AssegnazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AssegnazioneService {

    @Autowired
    AssegnazioneRepository assegnazioneRepository;

    public void save(Assegnazione a){
        if (assegnazioneRepository.getAssegnazioneByUserAndDate(a.getUser(), a.getDate()) == null){
            assegnazioneRepository.save(a);
        }else { //sostituzione
            assegnazioneRepository.delete(assegnazioneRepository.getAssegnazioneByUserAndDate(a.getUser(), a.getDate()));
            assegnazioneRepository.save(a);
        }

    }

    public Assegnazione getByUserAndDate(User u, Date d){
        return assegnazioneRepository.getAssegnazioneByUserAndDate(u,d);
    }
}
