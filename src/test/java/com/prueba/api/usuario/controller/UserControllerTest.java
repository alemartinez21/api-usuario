package com.prueba.api.usuario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.api.usuario.model.User;
import com.prueba.api.usuario.model.UserDTO;
import com.prueba.api.usuario.model.UserResponse;
import com.prueba.api.usuario.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;

    private User user;
    private UserResponse userResponse;
    private UserDTO userDTO;

    @BeforeEach
    public void init() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        user = objectMapper.readValue(new ClassPathResource("user_request.json").getFile(),
                User.class);
        userResponse = objectMapper.readValue(new ClassPathResource("user_response.json").getFile(),
                UserResponse.class);
        userDTO = objectMapper.readValue(new ClassPathResource("userDTO_request.json").getFile(),
                UserDTO.class);
    }

    @Test
    public void addUser_ok(){
        when(userService.addUser(any(User.class), anyString())).thenReturn(userResponse);
        ResponseEntity<UserResponse> response =
                userController.addUser("tokenMock", user);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertNotNull(response.getBody());
        verify(userService, times(1)).addUser(any(User.class),anyString());
    }


    @Test
    public void findAllUsers_ok(){
        List<UserDTO> users = Arrays.asList(userDTO, userDTO);
        when(userService.findAllUsers()).thenReturn(users);
        ResponseEntity<?> response = userController.findAllUsers("tokenMock");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(response.getBody());
        verify(userService, times(1)).findAllUsers();
    }

    @Test
    public void findUserById_ok(){
        when(userService.findUserbyId(anyString())).thenReturn(userDTO);
        ResponseEntity<UserDTO> response = userController.findUserbyId("userToken", "userId");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(response.getBody());
        verify(userService, times(1)).findUserbyId("userId");
    }

    @Test
    public void deleteUser_ok(){
        doNothing().when(userService).deleteUser(anyString());
        ResponseEntity<Void> response = userController.deleteUser("userToken", "userId");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userService, times(1)).deleteUser("userId");
    }

    @Test
    public void updateUser_ok(){
        when(userService.updateUser(any(User.class), anyString(), anyString())).thenReturn(userResponse);
        ResponseEntity<UserResponse> response =
                userController.updateUser("tokenMock", "id", user);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(response.getBody());
        verify(userService, times(1)).updateUser(any(User.class),anyString(),anyString());
    }


}
