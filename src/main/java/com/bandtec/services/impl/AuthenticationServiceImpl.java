package com.bandtec.services.impl;


import com.bandtec.api.security.JwtTokenUtil;
import com.bandtec.api.security.JwtUserData;
import com.bandtec.application.requests.AuthenticationRequest;
import com.bandtec.application.responses.AuthenticationResponse;
import com.bandtec.domain.dao.UserRepository;
import com.bandtec.domain.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.bandtec.services.AuthenticationService;

import java.util.ArrayList;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private UserRepository repository;

    public AuthenticationServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        AuthenticationResponse response = new AuthenticationResponse();

        if (validateData(request)) {
            response.fail("Dados invalidos");
            return response;
        }

        Usuario user;

        try {
            user = findUser(request.getEmail(), request.getPassword());
            if (user == null) {
                response.fail("Senha incorreta");
                return response;
            } else {
                JwtUserData.UserData.fillData(user);
            }

        } catch (Exception e) {
            response.fail("Usuário não encontrado");
            return response;
        }

        final UserDetails userDetails = new org.springframework.security.core.userdetails.User(request.getEmail(), request.getPassword(),
                new ArrayList<>());

        final String token = jwtTokenUtil.generateToken(userDetails);

        try {
            if (token == null) throw new NullPointerException();
            response.setJwttoken(token);
        } catch (Exception e) {
            response.fail("Houve um problema na geração do token");
            return response;
        }
        try {
            response.setNomeSocial(user.getNomeSocial());
            response.setUserId(user.getId());
        } catch (Exception e) {
            response.fail("Erro ao setar dados do usuário!");
        }


        JwtUserData.UserData.clearData();

        return response;
    }

    private Usuario findUser(String email, String password) {

        Usuario result = repository.findByEmail(email);

        if (result.getSenha().equals(password)) {
            return result;
        } else {
            return null;
        }
    }

    private boolean validateData(AuthenticationRequest request) {
        return request == null || request.getEmail() == null || request.getPassword() == null;
    }
}
