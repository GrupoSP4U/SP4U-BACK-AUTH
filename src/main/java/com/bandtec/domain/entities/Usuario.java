package com.bandtec.domain.entities;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity(name = "USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME_COMPLETO")
    private String nomeCompleto;

    @Column(name = "NOME_SOCIAL")
    private String nomeSocial;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SENHA")
    private String senha;
}
