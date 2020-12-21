package ARL.tesi.service;


import ARL.tesi.modelobject.Holidays;
import ARL.tesi.repository.HolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidaysService {
    @Autowired
    HolidaysRepository holidaysRepository;

    public Holidays getByName(String name){
        return holidaysRepository.getByName(name);
    }

    public void save(Holidays h){
        holidaysRepository.save(h);
    }

    public List<Holidays> getAll(){
        return holidaysRepository.findAll();
    }
}
