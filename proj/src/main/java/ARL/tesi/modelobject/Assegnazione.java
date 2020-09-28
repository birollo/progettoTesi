package ARL.tesi.modelobject;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Assegnazione {
    @Id
    @GeneratedValue
    private int id;
    @JsonIgnore
    @ManyToOne
    private User user;
    @ManyToOne
    private Turno turno;
    private Date date;

    public Assegnazione() {
    }

    public Assegnazione(User user, Turno turno, Date date) {
        this.user = user;
        this.turno = turno;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
