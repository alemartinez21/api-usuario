package com.prueba.api.usuario.repository;

import com.prueba.api.usuario.model.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserDTO, String> {

    @Query(value = "select u from UserDTO u WHERE u.email = :email")
    public List<UserDTO> findByEmail(@Param("email") String email);


}
