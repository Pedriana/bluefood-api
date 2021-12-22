package com.castro.bluefood.util;

import com.castro.bluefood.domain.cliente.Cliente;
import com.castro.bluefood.domain.restaurante.Restaurante;
import com.castro.bluefood.infrasctructure.web.security.LoggedUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {


    public static LoggedUser loggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //AnonymousAuthenticationToken significa que não está logado.
        if(authentication instanceof AnonymousAuthenticationToken){
            return null;
        }

        //authentication.getPrincipal() retorna um Object
        return (LoggedUser) authentication.getPrincipal();
    }

    public static Cliente loggedCliente(){
        LoggedUser loggedUser = loggedUser();

        if(loggedUser==null){
            throw  new IllegalStateException("Não existe um usuário logado.");
        }

        if(!(loggedUser.getUsuario() instanceof Cliente)){
            throw  new IllegalStateException("O usuário logado não é um cliente.");
        }

        return (Cliente) loggedUser.getUsuario();
    }

    public static Restaurante loggedRestaurante(){
        LoggedUser loggedUser = loggedUser();

        if(loggedUser==null){
            throw  new IllegalStateException("Não existe um usuário logado.");
        }

        if(!(loggedUser.getUsuario() instanceof Restaurante)){
            throw  new IllegalStateException("O usuário logado não é um restaurante.");
        }

        return (Restaurante) loggedUser.getUsuario();
    }
}
