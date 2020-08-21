package com.bandtec.api.security;

import com.bandtec.domain.entities.Usuario;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JwtUserData {

    private JwtUserData() {
    }

    public static class UserData {
        private static String nomeCompleto;
        private static String nomeSocial;
        private static String email;
        private static String senha;
        private static Long userId;

        public static void clearData(){
            nomeCompleto = null;
            nomeSocial = null;
            email = null;
            senha = null;
            userId = null;
        }

        public static void fillData(Usuario user){
            setNomeCompleto(user.getNomeCompleto());
            setNomeSocial(user.getNomeSocial());
            setEmail(user.getEmail());
            setSenha(user.getSenha());
            setUserId(user.getId());
        }

        public static String getNomeCompleto() {
            return nomeCompleto;
        }

        public static void setNomeCompleto(String nomeCompleto) {
            UserData.nomeCompleto = nomeCompleto;
        }

        public static String getNomeSocial() {
            return nomeSocial;
        }

        public static void setNomeSocial(String nomeSocial) {
            UserData.nomeSocial = nomeSocial;
        }

        public static String getEmail() {
            return email;
        }

        public static void setEmail(String email) {
            UserData.email = email;
        }

        public static String getSenha() {
            return senha;
        }

        public static void setSenha(String senha) {
            UserData.senha = senha;
        }

        public static Long getUserId() {
            return userId;
        }

        public static void setUserId(Long userId) {
            UserData.userId = userId;
        }
    }


}
