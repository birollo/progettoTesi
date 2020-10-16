package ARL.tesi.modelobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
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
    private Shiffts turno;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date date;

    public Assegnazione() {
    }

    public Assegnazione(User user, Shiffts turno, Date date) {
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

    public Shiffts getTurno() {
        return turno;
    }

    public void setTurno(Shiffts turno) {
        this.turno = turno;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
