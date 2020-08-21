package com.bandtec.domain.dao;


import com.bandtec.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);

    boolean existsByEmail(String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE Usuario SET senha = ?1 where email = ?2", nativeQuery = true)
    int updatePassword(String senha, String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE Usuario SET email = ?1 where id = ?2", nativeQuery = true)
    int updateEmail(String email, Long id);

    @Query(value = "SELECT * FROM Usuario where email = ?1 and senha = ?2", nativeQuery = true)
    List<Usuario> getUsuarioByPassword(String email, String senha);

}
