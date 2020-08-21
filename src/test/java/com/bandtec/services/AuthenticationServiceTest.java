package com.bandtec.services;

import com.bandtec.api.security.JwtTokenUtil;
import com.bandtec.application.requests.AuthenticationRequest;
import com.bandtec.application.responses.AuthenticationResponse;
import com.bandtec.domain.dao.UserRepository;
import com.bandtec.domain.entities.Usuario;
import com.bandtec.services.impl.AuthenticationServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@ContextConfiguration
@RunWith(SpringRunner.class)
public class AuthenticationServiceTest{

    @Mock
    private UserRepository repository;

    @InjectMocks
    private AuthenticationService service = new AuthenticationServiceImpl(repository);

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    public static final String NOME_COMPLETO = "TESTES TESTES TESTES";
    public static final String NOME_SOCIAL = "TESTE";
    public static final String EMAIL = "teste@gmail.com";
    public static final String SENHA = "123";

    public static final Usuario USUARIO_VALID = Usuario.builder()
            .nomeCompleto(NOME_COMPLETO)
            .nomeSocial(NOME_SOCIAL)
            .email(EMAIL)
            .senha(SENHA)
            .build();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void authenticateWhenUserDataIsInvalid() {
        //Cenario
        AuthenticationRequest requestInvalid = new AuthenticationRequest(null,null);

        //Ação
        AuthenticationResponse response = service.authenticate(requestInvalid);

        //Verificação
        Assert.assertNotNull(response);
        Assert.assertTrue(response.isFailure());
        Assert.assertEquals("Dados invalidos", response.getMessages().get(0));
        Mockito.verify(repository, Mockito.never()).findByEmail(Mockito.anyString());
    }

    @Test
    public void authenticateWhenUserDataNull() {
        //Cenario
        AuthenticationRequest requestNull = null;

        //Ação
        AuthenticationResponse response = service.authenticate(requestNull);

        //Verificação
        Assert.assertNotNull(response);
        Assert.assertTrue(response.isFailure());
        Assert.assertEquals("Dados invalidos", response.getMessages().get(0));
        Mockito.verify(repository, Mockito.never()).findByEmail(Mockito.anyString());
    }

    @Test
    public void authenticateWhenPasswordIsWrong() {
        //Cenario
        AuthenticationRequest mockedRequest = new AuthenticationRequest(EMAIL,"senhaErrada");
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(USUARIO_VALID);

        //Ação
        AuthenticationResponse response = service.authenticate(mockedRequest);

        //Verificação
        Assert.assertNotNull(response);
        Assert.assertTrue(response.isFailure());
        Assert.assertEquals("Senha incorreta", response.getMessages().get(0));
    }

    @Test
    public void authenticateWhenReturnIsNull() {
        //Cenario
        AuthenticationRequest mockedRequest = new AuthenticationRequest(EMAIL,SENHA);
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(null);

        //Ação
        AuthenticationResponse response = service.authenticate(mockedRequest);

        //Verificação
        Assert.assertNotNull(response);
        Assert.assertTrue(response.isFailure());
        Assert.assertEquals("Usuário não encontrado", response.getMessages().get(0));
    }

    @Test
    public void authenticateWhenTokenIsInvalid() {
        //Cenario
        AuthenticationRequest mockedRequest = new AuthenticationRequest(EMAIL,SENHA);
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(USUARIO_VALID);
        Mockito.when(jwtTokenUtil.generateToken(Mockito.any(UserDetails.class))).thenReturn(null);

        //Ação
        AuthenticationResponse response = service.authenticate(mockedRequest);

        //Verificação
        Assert.assertNotNull(response);
        Assert.assertTrue(response.isFailure());
        Assert.assertEquals(null,response.getNomeSocial());
        Assert.assertEquals("Houve um problema na geração do token", response.getMessages().get(0));
    }

    @Test
    public void authenticateWhenAllDataIsValid() {
        //Cenario
        AuthenticationRequest mockedRequest = new AuthenticationRequest(EMAIL,SENHA);
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(USUARIO_VALID);
        Mockito.when(jwtTokenUtil.generateToken(Mockito.any(UserDetails.class))).thenReturn("token");

        //Ação
        AuthenticationResponse response = service.authenticate(mockedRequest);

        //Verificação
        Assert.assertNotNull(response);
        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals(NOME_SOCIAL,response.getNomeSocial());
        Assert.assertEquals("token",response.getJwttoken());
    }
}