package com.prueba.api.usuario.service;

import com.prueba.api.usuario.model.User;
import com.prueba.api.usuario.model.UserDTO;
import com.prueba.api.usuario.model.UserResponse;

import java.util.List;

public interface UserService {

    public UserResponse addUser(User user, String token);
    public List<UserDTO> findAllUsers();
    public UserDTO findUserbyId(String userId);
    public void deleteUser(String userId);
    public UserResponse updateUser(User user, String token, String userId);

}
