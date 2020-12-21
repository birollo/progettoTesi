package ARL.tesi.repository;

import ARL.tesi.modelobject.Role;
import ARL.tesi.modelobject.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepo;

    @Test
    void getUsersByRoleTest() {
        // given
        String test="Test";
        Role role=new Role("ROLE_TEST",test);
        entityManager.persist(role);
        entityManager.flush();

        // when
        Role found = roleRepo.getByName(test);

        // then
        assertEquals(role.getName(),found.getName());
    }
}