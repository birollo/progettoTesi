//package ARL.tesi.controller;
//
//
//import ARL.tesi.modelobject.Role;
//import ARL.tesi.modelobject.Shiffts;
//import ARL.tesi.modelobject.User;
//import ARL.tesi.service.PersonService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.aspectj.util.FileUtil;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.io.File;
//import java.io.IOException;
//import java.time.LocalTime;
//import java.util.Calendar;
//
//import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@ContextConfiguration(classes = MainController.class)
//public class MainControllerPostTest {
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private PersonService service;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Before()
//    public void setup()
//    {
//        //Init MockMvc Object and build
//        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }
//
//    @Test
//    public void createUserTest() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        User user=new User("Alex", "Rossi", "Alex123","1234",new Role("ROLE_TEST","Test"));
//        String json = mapper.writeValueAsString(user);
//
//        mvc.perform(MockMvcRequestBuilders.post("/register")
//                .content(json).param("date","1997-10-19")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
//                .andExpect(status().isFound())
//                .andExpect(redirectedUrl("/"));
//    }
//
//    @Test
//    public void createTurnoTest() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        Shiffts turno=new Shiffts("122",false,"Mattino","lu-ve" ,570,600,0, LocalTime.of(9,30),LocalTime.of(10,00));
//        String json = mapper.writeValueAsString(turno);
//
//        mvc.perform(MockMvcRequestBuilders.post("/turno/new")
//                .content(json)
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
//                .andExpect(status().isFound())
//                .andExpect(redirectedUrl("/"));
//    }
//
//    /*@Test
//    public void editTurnoTest() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        Turno turno=new Turno("122",false,"Mattino",570,"09:30","10:00");
//        String json = mapper.writeValueAsString(turno);
//        mvc.perform(MockMvcRequestBuilders.post("/turno/{id}/edit","id")
//                //.param("id","0")
//                .content(json)
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA))
//                .andExpect(status().isFound())
//                .andExpect(redirectedUrl("/user/0"));
//    }
//    @Test
//    public void editUserTest() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        User user=new User("Alex", "Rossi", "Alex123","1234",new Role("ROLE_TEST","Test"));
//        String json = mapper.writeValueAsString(user);
//        File file=new ClassPathResource("static/images/slc.png").getFile();
//        MockMultipartFile mockFile = new MockMultipartFile("file",file.getName(),"multipart/form-data",FileUtil.readAsByteArray(file));
//        MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json", json.getBytes());
//        mvc.perform(MockMvcRequestBuilders.multipart("/user/{id}","id")
//                .file(mockFile)
//                .param("id","0")
//                .content(json)
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA))
//                //.andExpect(status().isFound())
//                .andExpect(redirectedUrl("/user/0"));
//    }*/
//
//}
