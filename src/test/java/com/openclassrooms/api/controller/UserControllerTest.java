package com.openclassrooms.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.api.model.User;
import com.openclassrooms.api.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @Test
    void testGetUsers() throws Exception {
        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].prenom", containsInAnyOrder("Alexis", "Dorian")))
        ;
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void testGetUser(int val) throws Exception {
        mockMvc.perform(get("/user/" + val))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                .andExpect(jsonPath("$.nom", is("Taallah")))
        ;
    }

    @Test
    void testCreateAndDeleteUserGood() throws Exception {
        User u = new User();
        u.setNom("Macron");
        u.setPrenom("Manu");
        u.setMail("manu.macron@gmail.com");
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(u);

        int nbUsers = userController.getUsers().size();

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                .andExpect(jsonPath("$.nom", is("Macron")))
                .andExpect(jsonPath("$.prenom", is("Manu")))
        ;

        Optional<User> user = userService.getUserByMail("manu.macron@gmail.com");
        if (user.isPresent()) {
            mockMvc.perform(delete("/user/" + user.get().getId()))
                    .andExpect(status().isOk())
            ;
        }

        assertEquals(nbUsers, userController.getUsers().size());
    }

    @Test
    void testDeleteNotExist() throws Exception {
        mockMvc.perform(delete("/user/" + 0))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("", result.getResponse().getContentAsString()))
                .andExpect(result -> assertNull(result.getResponse().getContentType()))
        ;
    }

    @Test
    void testLoginGood() throws Exception {
        User u = new User();
        u.setMail("dorian.taallah@gmail.com");
        u.setMdp("f9068b3242fd2815f0391881dd42f1fef7b0b829d9b11faf0e330d0d63f67676");
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(u);

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                .andExpect(jsonPath("$.mail", is("dorian.taallah@gmail.com")))
                .andExpect(jsonPath("$.mdp", is("f9068b3242fd2815f0391881dd42f1fef7b0b829d9b11faf0e330d0d63f67676")))
        ;
    }

    @Test
    void testLoginUserNotexist() throws Exception {
        User u = new User();
        u.setMail("manu.macron@gmail.com");
        u.setMdp("f9068b3242fd2815f0391881dd42f1fef7b0b829d9b11faf0e330d0d63f67676");
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(u);

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                .andExpect(result -> assertEquals("null", result.getResponse().getContentAsString()))
        ;
    }

    @Test
    void testLoginWrongPwd() throws Exception {
        User u = new User();
        u.setMail("dorian.taallah@gmail.com");
        u.setMdp("f9068b7b0b8faf0e330d0d63f67676");
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(u);

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                .andExpect(result -> assertEquals("null", result.getResponse().getContentAsString()))
        ;
    }

    @Test
    void testUpdateGood() throws Exception {
        User u = new User();
        u.setId(8);
        u.setMail("manu.macron@gmail.com");
        u.setNom("Macron");
        u.setPrenom("Manu");

        userController.createUser(u);

        u.setNom("Biden");
        u.setPrenom("Joe");
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(u);
        mockMvc.perform(put("/user/" + u.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("application/json", result.getResponse().getContentType()))
                .andExpect(jsonPath("$.nom", is("Biden")))
                .andExpect(jsonPath("$.prenom", is("Joe")))
                .andExpect(jsonPath("$.mail", is("manu.macron@gmail.com")))
        ;

        userController.deleteUser(8);
    }

    @Test
    void testUpdateIdNotExist() throws Exception {
        User u = new User();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(u);

        mockMvc.perform(put("/user/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(result -> assertNull(result.getResponse().getContentType()))
                .andExpect(result -> assertEquals("", result.getResponse().getContentAsString()))
        ;
    }

    @Test
    void testUpdateWithoutUserParam() throws Exception {
        mockMvc.perform(put("/user/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertNull(result.getResponse().getContentType()))
                .andExpect(result -> assertEquals("", result.getResponse().getContentAsString()))
        ;
    }
}
