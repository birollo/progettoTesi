package ARL.tesi.controller;


import ARL.tesi.modelobject.Assegnazione;
import ARL.tesi.modelobject.Role;
import ARL.tesi.modelobject.Shiffts;
import ARL.tesi.modelobject.User;
import ARL.tesi.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MainControllerGetTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private PersonService service;

    @Test
    public void indexTest() {
        // given
        given(service.getAuthenticatedUser())
                .willReturn(new User("Alex", "Rossi", "Alex123","1234",new Role("ROLE_TEST","Test")));

        List<User> users=new ArrayList<>();
        users.add(new User());
        given(service.getUsers()).willReturn(users);

        List<Shiffts> turni=new ArrayList<>();
        turni.add(new Shiffts());
        given(service.getTurni()).willReturn(turni);

        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/",String.class);

        // then
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(response.getBody());
        assertEquals(response.getHeaders().getContentType().toString(), "text/html;charset=UTF-8");
    }

//    @Test
//    public void indexTestIfNull() {
//        // given
//        given(service.getAuthenticatedUser()).willThrow(new NullPointerException());
//        given(service.getUsers()).willThrow(new NullPointerException());
//        given(service.getTurni()).willThrow(new NullPointerException());
//
//        // when
//        ResponseEntity<String> response = restTemplate.getForEntity("/",String.class);
//
//        // then
//        assertEquals(response.getStatusCode().value(), HttpStatus.INTERNAL_SERVER_ERROR.value());
//    }

    @Test
    public void loginTest() {
        //given
        List<Role> roles=new ArrayList<>();
        roles.add(new Role());
        given(service.getRoles()).willReturn(roles);

        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/login",String.class);

        // then
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(response.getBody());
        assertEquals(response.getHeaders().getContentType().toString(), "text/html;charset=UTF-8");
    }

//    @Test
//    public void loginTestIfNull() {
//        // given
//        given(service.getRoles()).willThrow(new NullPointerException());
//
//        // when
//        ResponseEntity<String> response = restTemplate.getForEntity("/login",String.class);
//
//        // then
//        assertEquals(response.getStatusCode().value(), HttpStatus.INTERNAL_SERVER_ERROR.value());
//    }

    @Test
    public void registerTest() {
        //given
        List<Role> roles=new ArrayList<>();
        roles.add(new Role());
        given(service.getRoles()).willReturn(roles);

        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/register",String.class);

        // then
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(response.getBody());
        assertEquals(response.getHeaders().getContentType().toString(), "text/html;charset=UTF-8");
    }

    @Test
    public void registerTestIfNull() {
        // given
        given(service.getRoles()).willThrow(new NullPointerException());

        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/register",String.class);

        // then
        assertEquals(response.getStatusCode().value(), 200);

    }

    @Test
    public void logoutTest() {
        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/logout",String.class);

        // then
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(response.getBody());
        assertEquals(response.getHeaders().getContentType().toString(), "text/html;charset=UTF-8");
    }

    @Test
    public void newTurnoTest() {
        // given
        given(service.getAuthenticatedUser())
                .willReturn(new User("Alex", "Rossi", "Alex123","1234",new Role("ROLE_TEST","Test")));

        List<User> users=new ArrayList<>();
        users.add(new User());
        given(service.getUsers()).willReturn(users);


        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/turno/new", String.class);

        // then
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(response.getBody());
        assertEquals(response.getHeaders().getContentType().toString(), "text/html;charset=UTF-8");
    }

    @Test
    public void userDetailTest() {
        // given
        given(service.getTotalMinutes(new User())).willReturn(5500);

        List<Assegnazione> turni=new ArrayList<>();
        turni.add(new Assegnazione());
        given(service.getTurniByUser(new User())).willReturn(turni);

        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/user/"+1, String.class);

        // then
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(response.getBody());
        assertEquals(response.getHeaders().getContentType().toString(), "text/html;charset=UTF-8");
    }

    @Test
    public void editUserTest() {
        // given
        List<Role> roles=new ArrayList<>();
        roles.add(new Role());
        given(service.getRoles()).willReturn(roles);

        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/user/"+1+"/edit", String.class);

        // then
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(response.getBody());
        assertEquals(response.getHeaders().getContentType().toString(), "text/html;charset=UTF-8");
    }

    @Test
    public void editUserIfNull() {
        // given
        //given(service.getUser(1)).willThrow(new NullPointerException());
        given(service.getRoles()).willThrow(new NullPointerException());

        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/user/"+1000+"/edit", String.class);

        // then
        assertEquals(response.getStatusCode().value(), 200);
    }

    @Test
    public void deleteUserTest() {
        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/user/"+1+"/delete",String.class);

        // then
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(response.getBody());
        assertEquals(response.getHeaders().getContentType().toString(), "text/html;charset=UTF-8");
    }

    @Test
    public void turnoDetailTest() {
        // given
        given(service.getAuthenticatedUser())
                .willReturn(new User("Alex", "Rossi", "Alex123","1234",new Role("ROLE_TEST","Test")));

        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/turno/"+1,String.class);

        // then
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(response.getBody());
        assertEquals(response.getHeaders().getContentType().toString(), "text/html;charset=UTF-8");
    }

    @Test
    public void turnoEditTest() {
        // given
        given(service.getAuthenticatedUser())
                .willReturn(new User("Alex", "Rossi", "Alex123","1234",new Role("ROLE_TEST","Test")));

        List<User> users=new ArrayList<>();
        users.add(new User());
        given(service.getUsers()).willReturn(users);

        List<Role> roles=new ArrayList<>();
        roles.add(new Role());
        given(service.getRoles()).willReturn(roles);

        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/turno/"+1+"/edit",String.class);

        // then
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(response.getBody());
        assertEquals(response.getHeaders().getContentType().toString(), "text/html;charset=UTF-8");
    }

    @Test
    public void deleteTurnoTest() {
        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/turno/"+1+"/delete", String.class);

        // then
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(response.getBody());
        assertEquals(response.getHeaders().getContentType().toString(), "text/html;charset=UTF-8");
    }

    @Test
    public void excellUploadTest() {
        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/turno/excelUpload", String.class);

        // then
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(response.getBody());
        assertEquals(response.getHeaders().getContentType().toString(), "text/html;charset=UTF-8");
    }

    @Test
    public void listUsersTest() {
        // given
        given(service.getAuthenticatedUser())
                .willReturn(new User("Alex", "Rossi", "Alex123","1234",new Role("ROLE_TEST","Test")));

        List<User> users=new ArrayList<>();
        users.add(new User());
        given(service.getUsers()).willReturn(users);

        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/listUsers",String.class);

        // then
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(response.getBody());
        assertEquals(response.getHeaders().getContentType().toString(), "text/html;charset=UTF-8");
    }

    @Test
    public void listTurniTest() {
        // given
        given(service.getAuthenticatedUser())
                .willReturn(new User("Alex", "Rossi", "Alex123","1234",new Role("ROLE_TEST","Test")));

        List<Shiffts> turni=new ArrayList<>();
        turni.add(new Shiffts());
        given(service.getTurni()).willReturn(turni);

        // when
        ResponseEntity<String> response = restTemplate.getForEntity("/listTurni",String.class);

        // then
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(response.getBody());
        assertEquals(response.getHeaders().getContentType().toString(), "text/html;charset=UTF-8");
    }

    /*@Test
    public void listPersonTest() {
        // given
        List<User> users=new ArrayList<>();
        users.add(new User("Alex", "Rossi", "Alex123","1234",new Role("ROLE_TEST","Test")));
        given(service.getUsersByRole("Test")).willReturn(users);
        // when
        ResponseEntity<Collections> response = restTemplate.getForEntity("/listaPersone", Collections.class);
        // then
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(response.getBody());
        assertEquals(response.getHeaders().getContentType().toString(), "text/html;charset=UTF-8");
    }
    @Test
    public void newUserTest() {
        // when
        HttpHeaders header=new HttpHeaders();
        header.set("name", "Alex");
        User user= new User("Alex","Rossi","Alex123","1234",new Role());
        HttpEntity<User> request=new HttpEntity<>(user, header);
        ResponseEntity<String> response = restTemplate.postForEntity("/register", request, String.class);
        // then
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(response.getBody());
    }*/
}