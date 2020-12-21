package ARL.tesi.modelobject;



import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
public class Holidays {

    @Id
    @GeneratedValue
    private int id;
    @NonNull
    private String name;

    private String da;

    private String a;

    private String type;

    public Holidays() {
    }

    public Holidays(String name, String da, String a, String type) {
        this.name = name;
        this.da = da;
        this.a = a;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDa() {
        return da;
    }

    public void setDa(String da) {
        this.da = da;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

