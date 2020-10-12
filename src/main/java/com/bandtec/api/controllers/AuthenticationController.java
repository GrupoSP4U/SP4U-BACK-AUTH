package com.bandtec.api.controllers;

import com.bandtec.api.security.BlackList;
import com.bandtec.application.requests.AuthenticationRequest;
import com.bandtec.application.responses.AuthenticationResponse;
import com.bandtec.domain.notifications.Response;
import com.bandtec.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;


@RestController
@CrossOrigin
public class AuthenticationController {

    private AuthenticationService service;

    private BlackList blackList;

    public AuthenticationController(AuthenticationService service) {
        super();
        this.service = service;
        this.blackList = new BlackList();
    }

    @PostMapping("/authenticate")
    public ResponseEntity authentication(@RequestBody @Valid AuthenticationRequest request){

        AuthenticationResponse response = service.authenticate(request);
        return response.isSuccess() ? status(HttpStatus.OK).body(response) : status(HttpStatus.NOT_FOUND).body(response);
    }

    @CrossOrigin
    @PostMapping("/logoff")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        blackList.addToList(token);
        Response response = new Response();
        return ok(response);
    }
}
