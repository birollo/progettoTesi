package ARL.tesi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ARL.tesi.modelobject.Role;
import ARL.tesi.modelobject.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> getUsersByRole(Role r);
    User findById(int id);
    User getUsersByNameAndSurname(String name, String surname);
    User save(User u);

}
