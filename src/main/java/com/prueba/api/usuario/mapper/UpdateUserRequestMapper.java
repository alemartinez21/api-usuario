package com.prueba.api.usuario.mapper;

import com.prueba.api.usuario.model.User;
import com.prueba.api.usuario.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class UpdateUserRequestMapper implements MapperUtils {
    private static final Logger log = LoggerFactory.getLogger(AddUserRequestMapper.class);

    public UserDTO map(User user, String userId, UserDTO userSaved) {
        LocalDateTime modifiedDate = LocalDateTime.now();
        log.info("Init mapper create User -"+ user);
        UserDTO userDTO = UserDTO.builder()
                .id(userId)
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .created(userSaved.getCreated())
                .modified(modifiedDate)
                .lastLogin(userSaved.getLastLogin())
                .token(userSaved.getToken())
                .isActive(userSaved.getIsActive())
                .phones(mapPhone(user.getPhones()))
                .build();

        log.info("User mapped to UserDTO -"+ userDTO);
        return userDTO;

    }

}
