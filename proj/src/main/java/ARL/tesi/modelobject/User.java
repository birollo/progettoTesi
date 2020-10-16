package ARL.tesi.modelobject;

//import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String surname;
    //@Column(unique=true)
    private String username;
    private String password;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date bDate;
    private double workingPercentage;




    private int holidays;
    private int hiringYear;

    @ManyToOne
    //@JoinColumn(name="ROLE_ID")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Assegnazione> turni;

    public User() {
    }

    public User(String name, String surname, String username, String password, Role role) {
        this.name=name;
        this.surname=surname;
        this.username = username;
        BCryptPasswordEncoder cryptPass=new BCryptPasswordEncoder();
        this.password=cryptPass.encode(password);
        this.role=role;
    }

    public User(String name, String surname, String username, String password, Date bDate, int hiringYear, Role role){
        this.name=name;
        this.surname=surname;
        this.username = username;
        BCryptPasswordEncoder cryptPass=new BCryptPasswordEncoder();
        this.password=cryptPass.encode(password);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        this.bDate = calendar.getTime();
        this.hiringYear = hiringYear;
        this.role=role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder cryptPass=new BCryptPasswordEncoder();
        this.password = cryptPass.encode(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getbDate() {
        return bDate;
    }

    public void setbDate(Date bDate) {
        this.bDate = bDate;
    }

    public double getWorkingPercentage() {
        return workingPercentage;
    }

    public void setWorkingPercentage(double workingPercentage) {
        this.workingPercentage = workingPercentage;
    }

    public int getHolidays() {
        return holidays;
    }

    public void setHolidays(int holidays) {
        this.holidays = holidays;
    }

    public List<Assegnazione> getTurni() {
        return turni;
    }

    public int getHiringYear() {
        return hiringYear;
    }

    public void setHiringYear(int hiringYear) {
        this.hiringYear = hiringYear;
    }

    public void setTurni(List<Assegnazione> turni) {
        this.turni = turni;
    }

}
