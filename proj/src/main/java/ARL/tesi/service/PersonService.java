package ARL.tesi.service;

import ARL.tesi.modelobject.*;
import ARL.tesi.util.ShifftsReader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ARL.tesi.repository.AssegnazioneRepository;
import ARL.tesi.repository.RoleRepository;
import ARL.tesi.repository.ShifftsRepository;
import ARL.tesi.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

@Service
public class PersonService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ShifftService shifftService;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private ShifftsRepository turnoRepo;

    @Autowired
    private AssegnazioneRepository assegnazioneRepo;

    @Autowired
    private AssegnazioneService assegnazioneService;

    @Autowired
    private ShifftsReader shifftsReader;

    //Init method
    @PostConstruct
    public void init() throws ParseException {
        createRoles();
        createUsers();
        createAdmin();
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

    private void createAdmin(){
        Role admin=addRole(new Role("ROLE_ADMIN", "Amministratore"));
        User user = new User("admin", "admin", "admin", "bus2020bus",admin);
        addUser(user);
    }

    private void createUsers() throws ParseException {
        if(userRepo.findAll().size() == 0) {
            Role admin=addRole(new Role("ROLE_ADMIN", "Amministratore"));
            User user=new User("admin", "admin","admin", "bus2020bus",admin);

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

            user=new User("Davide", "Gasparini","Gaqsparini D.", "dgasparini",formatter.parse("07-14-1994"),2016 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Daniele", "Gatti","Gatti D.", "dgatti",formatter.parse("04-16-1970"),2008 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Paolo", "Guida","Guida P.", "pguida",formatter.parse("04-19-1980"),2008 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Mauro", "Landi","Landi M.", "mlandi",formatter.parse("10-06-1978"),2019 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Marco", "Lazzarotto","Lazzarotto M.", "mlazzarotto",formatter.parse("06-19-1985"),2020 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Marco", "Maghella","Maghella M.", "mmaghella",formatter.parse("01-10-1981"),2019 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Tiziano", "Maiocchi","Maiocchi T.", "tmaiocchi",formatter.parse("08-21-1979"),1988 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Andrea", "Mariani","Mariani A.", "amariani",formatter.parse("10-07-1982"),2018 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Tommaso", "Orfano","Orfano T.", "torfano",formatter.parse("04-12-1961"),1995 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Roberto", "Pasqualin","Pasqualin R.", "rpasqualin",formatter.parse("05-19-1977"),2012 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Mario", "Pietrafesa","Pietrafesa M.", "mpietrafesa",formatter.parse("10-08-1983"),2014 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Gionata", "Provenzi","Provenzi G.", "gprovenzi",formatter.parse("03-12-1980"),2008, roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Roberto", "Riva","Riva R.", "rriva",formatter.parse("12-01-1970"),2008 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Paolo", "Roggiani","Roggiani P.", "proggiani",formatter.parse("01-01-1975"),2012 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Francesco", "Romanazzi","Romanazzi F.", "fromanazzi",formatter.parse("01-02-1975"),2012 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Dario", "Salerno","Salerno D.", "dsalerno",formatter.parse("06-24-1977"),2008 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Fabio", "Spanò","Spanò F.", "fspano",formatter.parse("11-16-1965"),1999 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Mattia", "Ubbiali","Ubbiali M.", "mubbiali",formatter.parse("11-25-1990"),2019 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Daniele", "Zanchi","Zanchi D.", "dzanchi",formatter.parse("11-18-1979"),2020 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Andrea", "Zerbini","Zerbini A.", "azerbini",formatter.parse("05-30-1976"),2014 , roleRepo.getByName("Autista di linea"));
            addUser(user);

            user=new User("Lorenzo", "Zerbini","Zerbini L.", "lzerbini",formatter.parse("11-27-1978"),2014 , roleRepo.getByName("Autista di linea"));
            addUser(user);


        }
    }

    private void createShiffts(){
        int size = turnoRepo.findAll().size();
        if (turnoRepo.findAll().size() == 0){
            File file1 = Paths.get(".", "/src/main/resources/files", "provaScriptIniziale.xlsx").normalize().toFile();
            try {
                MultipartFile multipartFile = new MockMultipartFile("test.xlsx", new FileInputStream(file1));
                List<Shiffts> shiffts = shifftsReader.readExcell(multipartFile);

                for (Shiffts s : shiffts){
                    if (shifftService.getByName(s.getName()).size() > 0) {
                        s.setVersion(shifftService.getLastByName(s.getName()).getVersion() + 1);
                        shifftService.save(s);
                    } else {
                        shifftService.save(s);
                    }
                }

                Shiffts r = new Shiffts("R", true, "tipo", "", 0,0,0, LocalTime.of(0,0), LocalTime.of(0,0));
                Shiffts c = new Shiffts("C", true, "tipo", "", 0,0,0, LocalTime.of(0,0), LocalTime.of(0,0));
                shifftService.save(r);
                shifftService.save(c);

            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private void createAssignments(){
        if (assegnazioneRepo.findAll().size() == 0){
            User user=findUserByUsername("Aguiari R.");
            Shiffts turno = turnoRepo.getFirstByName("103");
            Calendar cal1=Calendar.getInstance();
            cal1.set(Calendar.MILLISECOND, 0);
            cal1.set(Calendar.SECOND, 0);
            cal1.set(Calendar.MINUTE, 0);
            cal1.set(Calendar.HOUR_OF_DAY, 0);
            cal1.set(Calendar.DAY_OF_MONTH, 13);
            cal1.set(Calendar.MONTH, Calendar.OCTOBER);
            cal1.set(Calendar.YEAR, 2020);
            assegnazioneRepo.save(new Assegnazione(user,turno,cal1.getTime()));
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


    //////////////////////// Shiffts /////////////////////////////////
    public Shiffts addTurno(Shiffts turno){
        return turnoRepo.save(turno);
    }

    public Collection<Shiffts> getTurni(){
        return turnoRepo.findAll();
    }

    public Optional<Shiffts> getTurno(int id){ return turnoRepo.findById(id);}

    public void deleteTurno(Shiffts turno){
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

    public User save(User u){
       return userRepo.save(u);
    }

//    public int getLastMonthReport(User user){
//        Date today = new Date();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(today);
//        int month = 1; //cal.get(Calendar.MONTH);
//        int year = 2020;//cal.get(Calendar.YEAR);
//        int lastMonth=0;
//        if(month==1){
//            lastMonth=12;
//            year--;
//        }else{
//            lastMonth=month-1;
//        }
//        Collection<Assegnazione> assegnazioni=getAssegnazioniByUser(user);
//        int totMin=0;
//        for(Assegnazione a : assegnazioni){
//            if(a.getDate().getMonth()==lastMonth && a.getDate().getYear()==year){
//                totMin+=a.getTurno().getDuration();
//            }
//        }
//        return totMin;
//    }

//    public List<String> getReportByDate(Date d) throws ParseException {
//        Reports reports = new Reports();
//        List<User> drivers = userRepo.getUsersByRole(roleRepo.getByName( "Autista di linea"));
//        List<String> names = new ArrayList<>();
//        List<String> surnames = new ArrayList<>();
//        //todo: assegnazioni deve diventare idTurni, aggiungere altre liste di stringhe in base alla necessita
//        List<List<Assegnazione>> assegnaziones = new ArrayList<>();
//        for (User u : drivers){
//            names.add(u.getName());
//            surnames.add(u.getSurname());
//            assegnaziones.add(assegnazioneService.getOneMonthByUser(u, d));
//        }
//        reports.setDriversName(names);
//
//
//        return names;
//    }

}
