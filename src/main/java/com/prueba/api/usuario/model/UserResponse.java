package com.prueba.api.usuario.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class UserResponse {

    @JsonProperty("id")
    private String id;
    @JsonProperty("created")
    private LocalDateTime created;
    @JsonProperty("modified")
    private LocalDateTime modified;
    @JsonProperty("last_login")
    private LocalDateTime lastLogin;
    @JsonProperty("token")
    private String token;
    @JsonProperty("is_active")
    private Boolean isActive;
}
