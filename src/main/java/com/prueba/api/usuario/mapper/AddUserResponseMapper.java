package com.prueba.api.usuario.mapper;

import com.prueba.api.usuario.model.UserDTO;
import com.prueba.api.usuario.model.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AddUserResponseMapper implements Mapper<UserDTO, UserResponse>{
    private static final Logger log = LoggerFactory.getLogger(AddUserRequestMapper.class);

    @Override
    public UserResponse map(UserDTO request) {
        log.info("Init mapper add User response -"+ request);
        UserResponse userResponse = UserResponse.builder()
                .id(request.getId())
                .created(request.getCreated())
                .lastLogin(request.getLastLogin())
                .modified(request.getModified())
                .token(request.getToken())
                .isActive(request.getIsActive())
                .build();
        log.info("UserDTO mapped ro UserResponse -"+ request);
        return userResponse;
    }
}
