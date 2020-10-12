package com.bandtec.application.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    @NotNull
    private String email;
    @NotNull
    private String password;
}
