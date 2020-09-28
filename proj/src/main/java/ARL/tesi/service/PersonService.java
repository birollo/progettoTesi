package ARL.tesi.service;

import ARL.tesi.util.ShifftsReader;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.Role;
import ARL.tesi.modelobject.Turno;
import ARL.tesi.modelobject.User;
import ARL.tesi.repository.AssegnazioneRepository;
import ARL.tesi.repository.RoleRepository;
import ARL.tesi.repository.TurnoRepository;
import ARL.tesi.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PersonService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private TurnoRepository turnoRepo;

    @Autowired
    private AssegnazioneRepository assegnazioneRepo;

    //Init method
    @PostConstruct
    public void init() throws ParseException {
        createRoles();
        createUsers();
        createShiffts();
        createAssignments();
    }

    private void createRoles(){
        if(roleRepo.findAll().size() == 0) {
            addRole(new Role("ROLE_USER", "Autista di linea"));
            addRole(new Role("ROLE_SPECIAL", "Autista tratte speciali"));
            addRole(new Role("ROLE_RISERVA", "Riserva"));
            addRole(new Role("ROLE_CARROZIERE", "Carroziere"));
        }
    }

    private void createUsers() throws ParseException {
        if(userRepo.findAll().size() == 0) {
            Role admin=addRole(new Role("ROLE_ADMIN", "Amministratore"));
            User user=new User("admin", "admin","admin", "admin",admin);

            addUser(user);

            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
            user=new User("Paolo", "Aguiari","Aguiari P.", "paguari", formatter.parse("07-28-1969"),2006 ,roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Rolando", "Aguiari","Aguiari R.", "raguari",formatter.parse("02-05-1968"),2002 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Luca", "Albini","Albini L.", "lalbini",formatter.parse("03-14-1979"),2016 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Aurelio", "Barelli","Barelli A.", "abarelli",formatter.parse("09-26-1969"),2009 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Davide", "Barraco","Barraco D.", "dbarraco",formatter.parse("12-01-1987"),2019 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Massimo", "Belluschi","Belluschi M.", "mbelluschi",formatter.parse("03-24-1964"),2000 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Salvatore", "Bilardo","Bilardo S.", "sbilardo",formatter.parse("06-08-1975"),2019 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Daniele", "Bonzani","Bonzani D.", "dbonzani",formatter.parse("05-26-1973"),1 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Paolo", "Borioli","Borioli P.", "pborioli",formatter.parse("07-02-1971"),2004 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Donato", "Bortone","Bortone D.", "dbortone",formatter.parse("05-20-1954"),1988 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Alessandro", "Brugnara","Brugnara A.", "abrugnara",formatter.parse("01-17-1981"),2016 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Davide", "Brugnara","Brugnara D.", "dbrugnara",formatter.parse("07-13-1978"),2018 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Mirko", "Butti","Butti M.", "mbutti", formatter.parse("01-11-1973"),2009 ,roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Camillo", "Cantarella","Cantarella C.", "ccantarella",formatter.parse("08-07-1971"),2006 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Robert", "Ceko","Ceko R.", "rceko",formatter.parse("04-22-1975"),2018 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Marco", "Cerutti","Cerutti M.", "mcerutti",formatter.parse("12-12-1984"),2013 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Igor", "Cuttone","Cuttone I.", "icuttone",formatter.parse("04-22-1992"),2019 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Daniele", "Di Siervi","Di Siervi D.", "ddisiervi",formatter.parse("06-28-1977"),2019 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Luca", "Ferrari","Ferrari L.", "lferrari",formatter.parse("12-09-1984"),2010 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Roberto", "Fotia","Fotia R.", "rfotia",formatter.parse("08-21-1979"),2019 , roleRepo.getByName("Autista di linea"));
            addUser(user);
        }
    }

    private void createShiffts(){
        if (turnoRepo.findAll().size() == 0){
            ShifftsReader sr = new ShifftsReader();
            File file1 = Paths.get(".", "/src/main/resources/files", "provaScript2.xlsx").normalize().toFile();
            try {
                MultipartFile multipartFile = new MockMultipartFile("test.xlsx", new FileInputStream(file1));
                List<Turno> shiffts = sr.readExcell(multipartFile);
                for (int k = 0; k <shiffts.size(); k++){
                    turnoRepo.save(shiffts.get(k));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createAssignments(){
        if (assegnazioneRepo.findAll().size() == 0){
            User user=findUserByUsername("Aguiari R.");
            Turno turno = turnoRepo.getFirstByName(4);
            Calendar cal1=Calendar.getInstance();
            cal1.set(Calendar.MILLISECOND, 0);
            cal1.set(Calendar.SECOND, 0);
            cal1.set(Calendar.MINUTE, 0);
            cal1.set(Calendar.HOUR_OF_DAY, 0);
            cal1.set(Calendar.DAY_OF_MONTH, 26);
            cal1.set(Calendar.MONTH, Calendar.DECEMBER);
            cal1.set(Calendar.YEAR, 2019);
            Assegnazione assegnazione1=new Assegnazione(user, turno, cal1.getTime());
            addAssegnazione(assegnazione1);
        }
    }

    /*public List<Item> searchByCategoryAndType(String category, Type type) {
        return repo.findTop3ByCategoryTypeIgnoreCaseAndTypeOrderByDateDesc(category, type);
    }

    public List<Item> list(String search) {
        return repo.findTop5ByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryTypeContainingIgnoreCaseOrderByDateDesc(search, search, search);
    */


    public User addUser(User user){
        return userRepo.save(user);
    }

    public Collection<User> getUsers(){
        return userRepo.findAll();
    }

    public User findUserByUsername(String username) {
        ArrayList<User> users= (ArrayList<User>) getUsers();
        for(User user : users){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public void delete(User user){
        userRepo.delete(user);
    }

    public User getUser(int id){
        return userRepo.findById(id);
    }

    public List<User> getUsersByRole(String roleName){
        return userRepo.getUsersByRole( roleRepo.getByName(roleName));
    }

    public User getAuthenticatedUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return findUserByUsername(username);
    }

    //////////////////////// Role /////////////////////////////////
    public Role addRole(Role role){
        return roleRepo.save(role);
    }

    public void deleteRole(Role role){roleRepo.delete(role);}

    public Collection<Role> getRoles(){
        return roleRepo.findAll();
    }


    //////////////////////// Turno /////////////////////////////////
    public Turno addTurno(Turno turno){
        return turnoRepo.save(turno);
    }

    public Collection<Turno> getTurni(){
        return turnoRepo.findAll();
    }

    public Optional<Turno> getTurno(int id){ return turnoRepo.findById(id);}

    public void deleteTurno(Turno turno){
        turnoRepo.delete(turno);
    }

    public Collection<Assegnazione> getTurniByUser(User user) {
        return userRepo.getOne(user.getId()).getTurni();
    }

    public int getTotalMinutes(User user){
        Collection<Assegnazione> turni=getTurniByUser(user);
        int totMin=0;
        for(Assegnazione t : turni){
            totMin+=t.getTurno().getDuration();
        }
        return totMin;
    }

    //////////////////////// Assegnazione /////////////////////////////////
    public Assegnazione addAssegnazione(Assegnazione assegnazione){
        return assegnazioneRepo.save(assegnazione);
    }

    public Collection<Assegnazione> getAssegnazioni(){
        return assegnazioneRepo.findAll();
    }

    public void delete(Assegnazione assegnazione){
        assegnazioneRepo.delete(assegnazione);
    }

    public Collection<Assegnazione> getAssegnazioniByUser(User user){ return assegnazioneRepo.findAssegnazioniByUser(user); }

    public User getuserByNameAndSurname(String name, String surname){
        return userRepo.getUsersByNameAndSurname(name, surname);
    }

    public int getLastMonthReport(User user){
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int month = 1; //cal.get(Calendar.MONTH);
        int year = 2020;//cal.get(Calendar.YEAR);
        int lastMonth=0;
        if(month==1){
            lastMonth=12;
            year--;
        }else{
            lastMonth=month-1;
        }
        Collection<Assegnazione> assegnazioni=getAssegnazioniByUser(user);
        int totMin=0;
        for(Assegnazione a : assegnazioni){
            if(a.getDate().getMonth()==lastMonth && a.getDate().getYear()==year){
                totMin+=a.getTurno().getDuration();
            }
        }
        return totMin;
    }
}
