package com.bandtec.application.responses;

import com.bandtec.domain.notifications.Response;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AuthenticationResponse extends Response implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private String jwttoken;
    private String nomeSocial;
    private Long userId;

    public AuthenticationResponse(String jwttoken) {
        super();
        this.jwttoken = jwttoken;
    }

    public AuthenticationResponse() {
        super();
    }

}