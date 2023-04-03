package com.prueba.api.usuario.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.api.usuario.model.User;
import com.prueba.api.usuario.model.UserDTO;
import com.prueba.api.usuario.model.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AddUserResponseMapperTest {
    @InjectMocks
    private AddUserResponseMapper responseMapper;
    private UserDTO userDTO;
    private UserResponse userResponse;
    private LocalDateTime created;
    @BeforeEach
    public void init() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        created = LocalDateTime.now();
        userResponse = objectMapper.readValue(new ClassPathResource("user_response.json").getFile(),
                UserResponse.class);
        userDTO = objectMapper.readValue(new ClassPathResource("userDTO_request.json").getFile(),
                UserDTO.class);
        userDTO.setCreated(created);
    }

    @Test
    public void addUser_ok(){
        assertEquals(userResponse.getToken(), responseMapper.map(userDTO).getToken());
        assertEquals(userResponse.getId(), responseMapper.map(userDTO).getId());
        assertEquals(created, responseMapper.map(userDTO).getCreated());

    }
}
