package com.prueba.api.usuario.controller;

import com.prueba.api.usuario.model.User;
import com.prueba.api.usuario.model.UserDTO;
import com.prueba.api.usuario.model.UserResponse;
import com.prueba.api.usuario.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


     /**
     * Crea un nuevo usuario
     * @return ResponseEntity
     */
    @PostMapping(path = "/create", consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = {MediaType.APPLICATION_JSON_VALUE } )
    public ResponseEntity<UserResponse> addUser(@RequestHeader("Auth-Token") String token,  @Valid @RequestBody User user) {
        log.info("Init add User Controller /create " + user.toString());
        log.info("token " + token);
        return new ResponseEntity<>(userService.addUser(user, token), HttpStatus.CREATED);

    }

    /**
     * Retorna todos los usuarios registrados
     * @return List<User>
     */
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<UserDTO>> findAllUsers(@RequestHeader("Auth-Token") String token){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    /**
     * Retorna usuario por id
     * @return List<User>
     */
    @GetMapping(path = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UserDTO> findUserbyId(@RequestHeader("Auth-Token") String token, @PathVariable String userId){
        return ResponseEntity.ok(userService.findUserbyId(userId));
    }

    /**
     * elimina un usuario por id
     */
    @DeleteMapping(path = "/delete/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Void> deleteUser(@RequestHeader("Auth-Token") String token, @PathVariable String userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(OK);
    }

    /**
     * Actualiza un usuario
     */
    @PutMapping(path = "/update/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UserResponse> updateUser(@RequestHeader("Auth-Token") String token, @PathVariable String userId,
                                                   @RequestBody User user){
        return new ResponseEntity<>(userService.updateUser(user, token, userId), OK);
    }


}
