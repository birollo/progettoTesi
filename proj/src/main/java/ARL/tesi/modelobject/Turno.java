package ARL.tesi.modelobject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Turno {
    @Id
    @GeneratedValue
    private int id;
    private int name;
    private boolean school;
    private int duration;
    private int value;
    private int valueServizio;
    private String tipo;

    @OneToMany
    private List<Assegnazione> assegnazione;



    public Turno() {
    }

    public Turno(int name,boolean school ,String tipo, int duration, int value, int valueServizio){
        this.name = name;
        this.school = school;
        this.tipo = tipo;
        this.duration = duration;
        this.value = value;
        this.valueServizio = valueServizio;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
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


    public void setId(int id) {
        this.id = id;
    }


    public int getValueServizio() {
        return valueServizio;
    }

    public void setValueServizio(int valueServizio) {
        this.valueServizio = valueServizio;
    }
}
