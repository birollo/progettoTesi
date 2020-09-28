package ARL.tesi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ARL.tesi.modelobject.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Role getByName(String name);
}