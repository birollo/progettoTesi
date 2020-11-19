package ARL.tesi.service;


import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.repository.ShifftsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShifftService {

    @Autowired
    ShifftsRepository shifftsRepository;

    public Shiffts getOne(int id){return shifftsRepository.getOne(id);}

    public void save(Shiffts s){
        shifftsRepository.save(s);
    }

    public List<Shiffts> getByName(String name){return shifftsRepository.findByName(name);}

    public List<Shiffts> getLastVersionOfAll(){
        //prendo tutti
        List<Shiffts> all = shifftsRepository.findAll();
        List<Shiffts> lastVersionDuplicates = new ArrayList<>();
        //prendo l'ultima versione per ogni turno ma crea duplicati
        for (Shiffts s: all){
            lastVersionDuplicates.add(getLastByName(s.getName()));
        }
        //rimuovo duplicati
        Set<Shiffts> lastVersionOfAll = new LinkedHashSet<>(lastVersionDuplicates);
        List<Shiffts> finalList = new ArrayList<>();
        //cast tra Set e list
        for (Shiffts ss: lastVersionOfAll){
            finalList.add(ss);
        }
        //ordino la lista

        Collections.sort(finalList);
       return finalList;

    }

    public Shiffts getLastByName(String name){
        List<Shiffts> oldShiffts = shifftsRepository.findByName(name);
        if (oldShiffts.size() == 0){
            return null;
        }else {
            int k =0;
            for (Shiffts i : oldShiffts){
                if (i.getVersion() > k){
                    k=i.getVersion();
                }
            }
            return shifftsRepository.getByNameAndVersion(name, k);
        }
    }

    public List<Shiffts> getAllByDateAndSchool(String date, Boolean school){
        List<Shiffts> shiffts = getLastVersionOfAll();
        List<Shiffts> shifftsList = new ArrayList<>();
        for (Shiffts s: shiffts){
            if (s.getDays().equals(date) && s.isSchool() == school){
                shifftsList.add(s);
            }
        }

        return shifftsList;
    }
}
