package com.bandtec.domain.dtos;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsuarioDTO {

    private String nomeCompleto;

    private String nomeSocial;

    private String email;

    private String senha;

}
