package com.prueba.api.usuario.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.api.usuario.model.User;
import com.prueba.api.usuario.model.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AddAddUserRequestMapperTest {
    @InjectMocks
    private AddUserRequestMapper addUserRequestMapper;
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void init() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        user = objectMapper.readValue(new ClassPathResource("user_request.json").getFile(),
                User.class);
        userDTO = objectMapper.readValue(new ClassPathResource("userDTO_request.json").getFile(),
                UserDTO.class);
    }

    @Test
    public void addUser_ok(){
        assertEquals(userDTO.getName(), addUserRequestMapper.map(user,"tokenMock").getName());
        assertEquals(userDTO.getToken(), addUserRequestMapper.map(user,"tokenMock").getToken());
        assertEquals(userDTO.getEmail(), addUserRequestMapper.map(user,"tokenMock").getEmail());
        assertEquals(userDTO.getPhones().get(0).getCitycode(), addUserRequestMapper.map(user,"tokenMock").getPhones().get(0).getCitycode());
    }
}
