package com.prueba.api.usuario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.api.usuario.model.User;
import com.prueba.api.usuario.model.UserResponse;
import com.prueba.api.usuario.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegratedTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private User user;
    @Autowired
    private  UserService userService;

    @Test
    public void auuthToken() throws Exception {
        mockMvc.perform(post("/token"))
                .andExpect(status().isOk());
    }

    @Test
    public void addUser_created() throws Exception {
        user = objectMapper.readValue(new ClassPathResource("user_request.json").getFile(),
                User.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Auth-Token","eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJhcGlKV1QiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjMwMTk3NjQwLCJleHAiOjE2MzAxOTc2NDJ9.0D-wyd9Q3NbdJUpQPJh8ptGl3bFhpqiqCxfFAXBDGVUZQliRMpWql02zQ7KNO7fYvsocViZIuck-76y-3t96iw")
                .content( objectMapper.writeValueAsString(user))).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void addUser_noBody_themThrow400() throws Exception {
        user = objectMapper.readValue(new ClassPathResource("user_request.json").getFile(),
                User.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Auth-Token","eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJhcGlKV1QiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjMwMTk3NjQwLCJleHAiOjE2MzAxOTc2NDJ9.0D-wyd9Q3NbdJUpQPJh8ptGl3bFhpqiqCxfFAXBDGVUZQliRMpWql02zQ7KNO7fYvsocViZIuck-76y-3t96iw"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAllUsers_ok() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Auth-Token","eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJhcGlKV1QiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjMwMTk3NjQwLCJleHAiOjE2MzAxOTc2NDJ9.0D-wyd9Q3NbdJUpQPJh8ptGl3bFhpqiqCxfFAXBDGVUZQliRMpWql02zQ7KNO7fYvsocViZIuck-76y-3t96iw"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void findUserById_OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/").param("userId", "10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Auth-Token","eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJhcGlKV1QiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjMwMTk3NjQwLCJleHAiOjE2MzAxOTc2NDJ9.0D-wyd9Q3NbdJUpQPJh8ptGl3bFhpqiqCxfFAXBDGVUZQliRMpWql02zQ7KNO7fYvsocViZIuck-76y-3t96iw"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void findUserById_BadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/idUser")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Auth-Token","eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJhcGlKV1QiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjMwMTk3NjQwLCJleHAiOjE2MzAxOTc2NDJ9.0D-wyd9Q3NbdJUpQPJh8ptGl3bFhpqiqCxfFAXBDGVUZQliRMpWql02zQ7KNO7fYvsocViZIuck-76y-3t96iw"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteUser_ok() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{idUser}", "11")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Auth-Token","eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJhcGlKV1QiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjMwMTk3NjQwLCJleHAiOjE2MzAxOTc2NDJ9.0D-wyd9Q3NbdJUpQPJh8ptGl3bFhpqiqCxfFAXBDGVUZQliRMpWql02zQ7KNO7fYvsocViZIuck-76y-3t96iw"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteUser_BadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/idUser")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Auth-Token","eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJhcGlKV1QiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjMwMTk3NjQwLCJleHAiOjE2MzAxOTc2NDJ9.0D-wyd9Q3NbdJUpQPJh8ptGl3bFhpqiqCxfFAXBDGVUZQliRMpWql02zQ7KNO7fYvsocViZIuck-76y-3t96iw"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
