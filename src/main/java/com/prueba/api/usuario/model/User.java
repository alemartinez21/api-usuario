package com.prueba.api.usuario.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class User {
    @NotEmpty(message = "Nombre de usuario no puede ser vacio")
    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "mail debe cumplir con el siguiente formato (aaaaaaa@dominio.cl) ")
    private String email;
    @Pattern(regexp = "^(?=(?:.*\\d){2})(?=(?:.*[A-Z]){1})(?=(?:.*[a-z]){2})\\S{1,}$",
            message = "contraseña debe tener una mayúscula, letras minúsculas, y dos números)")
    private String password;
    private List<Phone> phones;

    public User() {
    }
}
