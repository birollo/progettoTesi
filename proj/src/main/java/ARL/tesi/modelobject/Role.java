package ARL.tesi.modelobject;

//import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Role {

    @Id
    @GeneratedValue
    private int id;

    private String type;
    private String name;

//    @OneToMany(mappedBy="role")
//    //@JsonIgnore
//    private List<User> user;

    public Role() {
    }

    public Role(String type, String name) {
//        this.user=new ArrayList<>();
        this.type = type;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public List<User> getUser() {
//        return user;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
