package com.bandtec.services;


import com.bandtec.application.requests.AuthenticationRequest;
import com.bandtec.application.responses.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
