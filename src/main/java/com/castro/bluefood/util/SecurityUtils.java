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

        //AnonymousAuthenticationToken significa que n�o est� logado.
        if(authentication instanceof AnonymousAuthenticationToken){
            return null;
        }

        //authentication.getPrincipal() retorna um Object
        return (LoggedUser) authentication.getPrincipal();
    }

    public static Cliente loggedCliente(){
        LoggedUser loggedUser = loggedUser();

        if(loggedUser==null){
            throw  new IllegalStateException("N�o existe um usu�rio logado.");
        }

        if(!(loggedUser.getUsuario() instanceof Cliente)){
            throw  new IllegalStateException("O usu�rio logado n�o � um cliente.");
        }

        return (Cliente) loggedUser.getUsuario();
    }

    public static Restaurante loggedRestaurante(){
        LoggedUser loggedUser = loggedUser();

        if(loggedUser==null){
            throw  new IllegalStateException("N�o existe um usu�rio logado.");
        }

        if(!(loggedUser.getUsuario() instanceof Restaurante)){
            throw  new IllegalStateException("O usu�rio logado n�o � um restaurante.");
        }

        return (Restaurante) loggedUser.getUsuario();
    }
}
