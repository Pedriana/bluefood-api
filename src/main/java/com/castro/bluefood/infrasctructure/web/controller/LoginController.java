package com.castro.bluefood.infrasctructure.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(path = {"/login","/"})
    public String login(){
//        aqui retorna a página: /templates/login.html
        return "login";
    }

    @GetMapping(path = "/login-error")
    public String loginError(Model model){
        System.out.println("Passei pelo erro login.");
        model.addAttribute("msg","Credenciais inválidas");
        return "login";
    }
}
