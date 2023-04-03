package com.prueba.api.usuario.mapper;

import com.prueba.api.usuario.model.User;
import com.prueba.api.usuario.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class AddUserRequestMapper implements MapperUtils {

    private static final Logger log = LoggerFactory.getLogger(AddUserRequestMapper.class);

    public UserDTO map(User user, String token) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        LocalDateTime createDate = LocalDateTime.now();
        log.info("Init mapper create User -"+ user);
        UserDTO userDTO = UserDTO.builder()
                                    .id(uuidAsString)
                                    .name(user.getName())
                                    .email(user.getEmail())
                                    .password(user.getPassword())
                                    .created(createDate)
                                    .modified(null)
                                    .lastLogin(createDate)
                                    .token(token)
                                    .isActive(true)
                                    .phones(mapPhone(user.getPhones()))
                                    .build();

        log.info("User mapped to UserDTO -"+ userDTO);
        return userDTO;

    }


}
