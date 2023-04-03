package com.prueba.api.usuario.auth;

import com.prueba.api.usuario.model.Token;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {
    static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Value("${auth.secretkey}")
    private String secretKey;

    @Value("${auth.id.jwt}")
    private String apiJWT;

    @Value("${auth.claim}")
    private String claimName;

    @PostMapping(path = "/token",produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Token> getToken() {
        log.info("Inicia Obtencion de Token");

        Token token = new Token();
        token.setNewToken(getJWTToken());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    /**
     * Genera Token
     * @return
     */
    private String getJWTToken() {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        return Jwts
                .builder()
                .setId(apiJWT)
                .claim(claimName,
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 2000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
    }
}
