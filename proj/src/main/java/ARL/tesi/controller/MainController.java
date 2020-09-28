package ARL.tesi.controller;


import ARL.tesi.modelobject.Turno;
import ARL.tesi.modelobject.User;
import ARL.tesi.repository.TurnoRepository;
import ARL.tesi.service.PersonService;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    PersonService personService;

    @Autowired
    TurnoRepository turnoRepository;

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
    public @ResponseBody
    List<User> listPerson(){
        return (List<User>) personService.getUsersByRole("Autista di linea");
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
        model.addAttribute("turni", personService.getTurni());
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
        model.addAttribute("turno", new Turno());
        model.addAttribute("users", personService.getUsers());
        return "createTurno";
    }

    @PostMapping("/turno/new")
    public String post(Turno turno){
        personService.addTurno(turno);
        return "redirect:/";
    }

    @GetMapping("/turno/{id}")
    public String detailTurno(@PathVariable int id, Model model){
        model.addAttribute("turno", turnoRepository.getOne(id));
        model.addAttribute("user", personService.getAuthenticatedUser());
        return "turniDetails";
    }

    @GetMapping("/turno/{id}/edit")
    public String editTurno(@PathVariable int id, Model model){
        Turno turno =  turnoRepository.getOne(id);
        model.addAttribute("turno", turno);
        model.addAttribute("users", personService.getUsers());
        model.addAttribute("roles", personService.getRoles());
        return "createTurno";
    }

    @PostMapping("/turno/{id}/edit")
    public String putTurno(@PathVariable int id, Turno newTurno){
        Turno turno =  turnoRepository.getOne(id);
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
        personService.deleteTurno(turnoRepository.getOne(id));
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
    public String excelUpload(){
        return "excelUpload";
    }

    @GetMapping(value = "/assegnazione/{id}/{date}")
    public String addAssignment(Model model,@PathVariable String id, @PathVariable Date date){
        String name = id.split(" ")[0];
        String surname = id.split(" ")[1];
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String fdate = simpleDateFormat.format(date);
        model.addAttribute("user", personService.getuserByNameAndSurname(name, surname));
        model.addAttribute("date", fdate);
        model.addAttribute("turni", personService.getTurni());
        return "/addAssignment";
    }

    @PostMapping(value = "/assegnazione/{id}/{date}")
    public String checkAssignment(@RequestParam("thisTurno") String idTurno, @PathVariable String id, @PathVariable Date date){
        //todo: crare assegnazione
        return "redirect:/";
    }

}
