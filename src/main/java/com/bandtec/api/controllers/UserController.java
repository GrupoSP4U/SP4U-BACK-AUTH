package com.bandtec.api.controllers;

import com.bandtec.domain.dao.UserRepository;
import com.bandtec.domain.dtos.UsuarioDTO;
import com.bandtec.domain.entities.Usuario;
import com.bandtec.domain.notifications.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository repository;

    @PostMapping("/user")
    public ResponseEntity createUser(@RequestBody UsuarioDTO usuarioDTO){
        Response response = new Response();
        try{
            Usuario usuario = Usuario.builder().email(usuarioDTO.getEmail()).nomeCompleto(usuarioDTO.getNomeCompleto()).
                    nomeSocial(usuarioDTO.getNomeSocial()).senha(usuarioDTO.getSenha()).build();
            repository.save(usuario);
        } catch (Exception e){
            response.fail("Algo deu errado");
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<Usuario> getUsuarios(){
        return repository.findAll();
    }

}
