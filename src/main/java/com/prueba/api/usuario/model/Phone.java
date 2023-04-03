package com.prueba.api.usuario.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Phone {
    private String number;
    private String citycode;
    private String countrycode;

    public Phone() {
    }
}
