package com.prueba.api.usuario.mapper;

import com.prueba.api.usuario.model.Phone;
import com.prueba.api.usuario.model.PhoneDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public interface MapperUtils {
    Logger log = LoggerFactory.getLogger(AddUserRequestMapper.class);
    default List<PhoneDTO> mapPhone(List<Phone> request) {
        log.info("Init mapper Phone to PhoneDTO -" + request.toString());

        List<PhoneDTO> phoneDTO = request.stream()
                .map(phone-> PhoneDTO.builder()
                        .number(phone.getNumber())
                        .citycode(phone.getCitycode())
                        .countrycode(phone.getCountrycode())
                        .build()

                ).collect(Collectors.toList());

        log.info("User mapped to UserDTO -"+ phoneDTO);
        return phoneDTO;
    }
}
