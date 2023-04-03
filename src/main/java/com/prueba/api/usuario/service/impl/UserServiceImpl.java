package com.prueba.api.usuario.service.impl;

import com.prueba.api.usuario.errorHandler.UserException;
import com.prueba.api.usuario.mapper.AddUserRequestMapper;
import com.prueba.api.usuario.mapper.AddUserResponseMapper;
import com.prueba.api.usuario.mapper.UpdateUserRequestMapper;
import com.prueba.api.usuario.model.User;
import com.prueba.api.usuario.model.UserDTO;
import com.prueba.api.usuario.model.UserResponse;
import com.prueba.api.usuario.repository.UserRepository;
import com.prueba.api.usuario.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(AddUserRequestMapper.class);
    private final AddUserRequestMapper requestMapper;
    private final UpdateUserRequestMapper updateUserRequestMapper;
    private final AddUserResponseMapper responseMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(AddUserRequestMapper requestMapper, UpdateUserRequestMapper updateUserRequestMapper, AddUserResponseMapper responseMapper, UserRepository userRepository) {
        this.requestMapper = requestMapper;
        this.updateUserRequestMapper = updateUserRequestMapper;
        this.responseMapper = responseMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse addUser(User user, String token) {
        log.info("Init service addUser -"+ user);
        UserDTO userDTO = requestMapper.map(user, token);
        if(findUserByEmail(userDTO.getEmail())){
            userDTO = userRepository.save(userDTO);
            UserResponse response = responseMapper.map(userDTO);
            log.info("Usuario agregado -"+ response);
            return response;
        }else{
            log.error("Error de correo ya ingresado -"+ user.getEmail());
            throw new UserException("El correo ya registrado", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<UserDTO> listUsers = userRepository.findAll();
        log.info("Lista de usuarios -"+ listUsers);
        return listUsers;
    }

    @Override
    public UserDTO findUserbyId(String userId) {
        log.info("Busca usuario por id -"+ userId);
        Optional<UserDTO> userDTO = userRepository.findById(userId);
        if(userDTO.isPresent()){
            return userDTO.get();
        }else{
            throw new UserException("Usuario no encontrado", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void deleteUser(String userId) {
        log.info("Elimina un usuario -"+ userId);
        try{
            userRepository.deleteById(userId);
        }catch (Exception exception){
            throw new UserException("Usuario indicado no existe en el sistema", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public UserResponse updateUser(User user, String token, String userId) {
        log.info("Init service updateUser -"+ user);
        //busca el usuario
        Optional<UserDTO> userIsFinded = userRepository.findById(userId);
        if(userIsFinded.isPresent()){
            try {
                UserDTO userDTO = updateUserRequestMapper.map(user,userId,userIsFinded.get());
                userDTO = userRepository.save(userDTO);
                UserResponse response = responseMapper.map(userDTO);
                log.info("Usuario agregado -"+ response);
                return response;
            }catch (Exception e){
                log.error("Error de correo ya ingresado -"+ user.getEmail());
                throw new UserException("El correo ya registrado", HttpStatus.BAD_REQUEST);
            }
        }else{
            log.error("Usuario no existe -"+ user.getEmail());
            throw new UserException("Usuario no encontrado", HttpStatus.BAD_REQUEST);
        }

    }

    private boolean findUserByEmail(String email){
        List<UserDTO> users = userRepository.findByEmail(email);
        return CollectionUtils.isEmpty(users);
    }

}
