package com.prueba.api.usuario.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.api.usuario.errorHandler.UserException;
import com.prueba.api.usuario.mapper.AddUserRequestMapper;
import com.prueba.api.usuario.mapper.AddUserResponseMapper;
import com.prueba.api.usuario.mapper.UpdateUserRequestMapper;
import com.prueba.api.usuario.model.User;
import com.prueba.api.usuario.model.UserDTO;
import com.prueba.api.usuario.model.UserResponse;
import com.prueba.api.usuario.repository.UserRepository;
import com.prueba.api.usuario.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private AddUserRequestMapper requestMapper;
    @Mock
    private AddUserResponseMapper responseMapper;
    @Mock
    private UpdateUserRequestMapper updateUserRequestMapper;
    @Mock
    private UserRepository userRepository;
    private User user;
    private UserDTO userDTO;
    private UserResponse userResponse;
    @BeforeEach
    public void init() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        user = objectMapper.readValue(new ClassPathResource("user_request.json").getFile(),
                User.class);
        userDTO = objectMapper.readValue(new ClassPathResource("userDTO_request.json").getFile(),
                UserDTO.class);
        userResponse = objectMapper.readValue(new ClassPathResource("user_response.json").getFile(),
                UserResponse.class);
    }

    @Test
    public void addUser_ok(){
        when(requestMapper.map(any(User.class), anyString())).thenReturn(userDTO);
        when(userRepository.save(any(UserDTO.class))).thenReturn(userDTO);
        when(responseMapper.map(any(UserDTO.class))).thenReturn(userResponse);
        assertNotNull(service.addUser(user, "tokenMock"));
        verify(requestMapper, times(1)).map(user, "tokenMock");
        verify(userRepository, times(1)).save(userDTO);
        verify(responseMapper, times(1)).map(userDTO);
    }

    @Test
    public void addUser_whenEmailIsExist(){

        when(requestMapper.map(any(User.class), anyString())).thenReturn(userDTO);
        List<UserDTO> userDTOList = Collections.singletonList(userDTO);
        when(userRepository.findByEmail(any(String.class))).thenReturn(userDTOList);

        UserException exception = assertThrows(UserException.class, () -> {
            service.addUser(user, "tokenMock");
        });
        assertEquals("El correo ya registrado", exception.getMessage());
    }

    @Test
    public void findAllUsers_ok(){
        List<UserDTO> users = Arrays.asList(userDTO, userDTO);
        when(userRepository.findAll()).thenReturn(users);
        assertNotNull(service.findAllUsers());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void findUserById_ok(){
        when(userRepository.findById(anyString())).thenReturn(java.util.Optional.ofNullable(userDTO));
        assertNotNull(service.findUserbyId("userId"));
        verify(userRepository, times(1)).findById("userId");
    }

    @Test
    public void findUserById_whnNotFoundId(){
        UserException exception = assertThrows(UserException.class, () -> {
            service.findUserbyId("userString");
        });
        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    public void deleteUser_ok(){
        doNothing().when(userRepository).deleteById(anyString());
        service.deleteUser("userId");
        verify(userRepository, times(1)).deleteById("userId");
    }

    @Test
    public void updateUser_ok(){
        when(userRepository.findById(anyString())).thenReturn(Optional.of(userDTO));
        when(updateUserRequestMapper.map(any(User.class), anyString(),any(UserDTO.class))).thenReturn(userDTO);
        when(userRepository.save(any(UserDTO.class))).thenReturn(userDTO);
        when(responseMapper.map(any(UserDTO.class))).thenReturn(userResponse);
        assertNotNull(service.updateUser(user, "tokenMock", "eqweqw"));
        verify(userRepository, times(1)).save(userDTO);
        verify(responseMapper, times(1)).map(userDTO);
    }


}
