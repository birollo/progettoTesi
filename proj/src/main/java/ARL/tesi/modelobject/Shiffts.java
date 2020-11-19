package ARL.tesi.modelobject;

import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Shiffts implements Comparable<Shiffts> {
    @Id
    @GeneratedValue
    private int id;
    @NonNull
    private String name;
    private boolean school;
    private int duration;
    private int value;
    private String tipo;
    private int version;
    private LocalTime startTime;
    private LocalTime endTime;
    private String days;

    @OneToMany
    private List<Assegnazione> assegnazione;



    public Shiffts() {
    }

    public Shiffts(String name, boolean school , String tipo, String days, int duration,
                   int value, int version, LocalTime startTime, LocalTime endTime){
        this.name = name;
        this.school = school;
        this.tipo = tipo;
        this.duration = duration;
        this.value = value;
        this.version = version;
        this.startTime = startTime;
        this.endTime = endTime;
        this.days = days;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Assegnazione> getAssegnazione() {
        return assegnazione;
    }

    public void setAssegnazione(List<Assegnazione> assegnazione) {
        this.assegnazione = assegnazione;
    }

    public boolean isSchool() {
        return school;
    }

    public void setSchool(boolean school) {
        this.school = school;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public int compareTo(Shiffts o) {
        String thisName = this.getName();
        String tempName = o.getName();
        int last = thisName.compareTo(tempName);
        return last;
    }
}
