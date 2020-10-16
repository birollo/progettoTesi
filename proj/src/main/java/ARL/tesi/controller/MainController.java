package ARL.tesi.controller;


import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.DBFile;
import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.modelobject.User;
import ARL.tesi.repository.ShifftsRepository;
import ARL.tesi.service.AssegnazioneService;
import ARL.tesi.service.DBFileStorageService;
import ARL.tesi.service.PersonService;
import ARL.tesi.service.ShifftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    PersonService personService;

    @Autowired
    ShifftService shifftService;

    @Autowired
    AssegnazioneService assegnazioneService;

    @Autowired
    DBFileStorageService dbFileStorageService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("user", personService.getAuthenticatedUser());
        model.addAttribute("users", personService.getUsers());
        model.addAttribute("turni", personService.getTurni());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/logout")
    public String logout(){
        return "redirect:/";
    }

    //per funzione ajax caricamento
    @GetMapping(value = "/listaPersone")
    public @ResponseBody List<User> listPerson(){
        List<User> autisti = personService.getUsersByRole("Autista di linea");
        return autisti;
    }

    //per funzione ajax caricamento
    @GetMapping(value = "/assegnazione/{id}/{date}/getStartTime")
    public @ResponseBody Shiffts getStartTime(@RequestParam("turno") String name){
        Shiffts s = shifftService.getLastByName(Integer.valueOf(name));
        LocalTime startTime = s.getStartTime();
        s.setStartTime(startTime);
        return shifftService.getLastByName(Integer.valueOf(name));
    }


    @GetMapping("/listUsers")
    public String utenti(Model model){
        model.addAttribute("user", personService.getAuthenticatedUser());
        model.addAttribute("autisti", personService.getUsersByRole("Autista di linea"));
        model.addAttribute("speciali", personService.getUsersByRole("Autista tratte speciali"));
        model.addAttribute("riserve", personService.getUsersByRole("Riserva"));
        model.addAttribute("carrozieri", personService.getUsersByRole("Carroziere"));
        model.addAttribute("users", personService.getUsers());
        return "listUsers";
    }

    @GetMapping("/listTurni")
    public String turni(Model model){
        model.addAttribute("user", personService.getAuthenticatedUser());
        model.addAttribute("turni", shifftService.getLastVersionOfAll());
        return "listTurni";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("roles", personService.getRoles());
        return "register";
    }

    @PostMapping("/register")
    public String newUser(User user, @RequestParam("date") String date){
        try {
            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
            user.setbDate(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        personService.addUser(user);
        return "redirect:/";
    }

    @GetMapping("/turno/new")
    public String newPost(Model model){
        model.addAttribute("user", personService.getAuthenticatedUser());
        model.addAttribute("turno", new Shiffts());
        model.addAttribute("users", personService.getUsers());
        return "createTurno";
    }

    @PostMapping("/turno/new")
    public String post(Shiffts turno){
        personService.addTurno(turno);
        return "redirect:/";
    }

    @GetMapping("/turno/{id}")
    public String detailTurno(@PathVariable int id, Model model){
        model.addAttribute("turno", shifftService.getOne(id));
        model.addAttribute("user", personService.getAuthenticatedUser());
        return "turniDetails";
    }

    @GetMapping("/turno/{id}/edit")
    public String editTurno(@PathVariable int id, Model model){
        Shiffts turno =  shifftService.getOne(id);
        model.addAttribute("turno", turno);
        model.addAttribute("users", personService.getUsers());
        model.addAttribute("roles", personService.getRoles());
        return "createTurno";
    }

    @PostMapping("/turno/{id}/edit")
    public String putTurno(@PathVariable int id, Shiffts newTurno){
        Shiffts turno =  shifftService.getOne(id);
        newTurno.setId(id);
        if(newTurno.getDuration()!=0){
            turno.setDuration(newTurno.getDuration());
        }
        if(newTurno.getName()> 0){
            turno.setName(newTurno.getName());
        }

        personService.addTurno(turno);
        return "redirect:/turno/{id}";
    }

    @GetMapping(value = "/turno/{id}/delete")
    public String deleteTurno(@PathVariable int id){
        //todo: non cancellare ma solo togliere visibilita
        personService.deleteTurno(shifftService.getOne(id));
        return "redirect:/";
    }


    @GetMapping("/user/{id}")
    public String detail(@PathVariable int id, Model model){
        model.addAttribute("user", personService.getUser(id));
        model.addAttribute("turni", personService.getTurniByUser(personService.getUser(id)));
        model.addAttribute("totMin", personService.getTotalMinutes(personService.getUser(id)));
        return "userProfile";
    }

    @PostMapping("/user/{id}")
    public String put(@PathVariable int id, User newUser){
        User user = personService.getUser(id);
        newUser.setId(id);
        if(newUser.getName()!=null){
            user.setName(newUser.getName());
        }
        if(newUser.getSurname()!=null){
            user.setSurname(newUser.getSurname());
        }
        if(newUser.getUsername()!=null){
            user.setUsername(newUser.getUsername());
        }
        if(newUser.getRole()!=null){
            user.setRole(newUser.getRole());
        }

        personService.addUser(user);
        return "redirect:/user/{id}";
    }

    @GetMapping("/user/{id}/edit")
    public String edit(@PathVariable int id, Model model){
        User user = personService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", personService.getRoles());
        return "register";
    }

    @GetMapping(value = "/user/{id}/delete")
    public String deleteUser(@PathVariable int id){
        personService.delete(personService.getUser(id));
        return "redirect:/";
    }


    @GetMapping(value = "turno/excelUpload")
    public String excelUpload(Model model){
        //prendo tutti gli ecxcell
        List<DBFile> dbFileList = dbFileStorageService.getAllByType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        model.addAttribute("excells", dbFileList);
        return "excelUpload";
    }

    @GetMapping(value = "/assegnazione/{id}/{date}")
    public String addAssignment(Model model,@PathVariable String id, @PathVariable Date date){
        String name = id.split(" ")[0];
        String surname = id.split(" ")[1];
        User u = personService.getuserByNameAndSurname(name, surname);
        model.addAttribute("user", u);

        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //todo:forse e qua che si toglie un giorno
        cal.add(Calendar.DATE, -1);

        Date dateBefore = cal.getTime();
        String stringDateBefore = simpleDateFormat.format(dateBefore);
        try {
            dateBefore = simpleDateFormat.parse(stringDateBefore);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        String fdate = simpleDateFormat.format(date);

        int idUser = u.getId();
        Assegnazione lastAssignment = assegnazioneService.getByUserAndDate(u, dateBefore);
        Shiffts lastShifft;
        if(lastAssignment == null){
            lastShifft = new Shiffts(0, true, "", 0, 0, 0, 0,
                                        LocalTime.of(0,0), LocalTime.of(0,0));
        }else {
            lastShifft = lastAssignment.getTurno();
        }
        //todo: le ore nel database sono ridotte di 1, ma le carica giuste
//        lastShifft.setEndTime(lastShifft.getEndTime().plusHours(-1));

        model.addAttribute("date", fdate);
        model.addAttribute("turni", shifftService.getLastVersionOfAll());
        model.addAttribute("lastShifft" , lastShifft);
        return "/addAssignment";
    }

    @PostMapping(value = "/assegnazione/{id}/{date}")
    public String checkAssignment(@RequestParam("thisTurno") int idTurno, @PathVariable String id, @PathVariable Date date){
        String name = id.split(" ")[0];
        String surname = id.split(" ")[1];
        User u = personService.getuserByNameAndSurname(name,surname);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //todo:qui clicchi un giorno e nel db c e un giorno in piu
//        cal.add(Calendar.DATE, +1);
        Date dateBefore1Days = cal.getTime();

        Shiffts t = shifftService.getLastByName(idTurno);

        Assegnazione a = new Assegnazione(u,t,dateBefore1Days);
        assegnazioneService.save(a);


        return "redirect:/";
    }


}
