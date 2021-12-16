package com.castro.bluefood.infrasctructure.web.controller;

import com.castro.bluefood.application.ClienteService;
import com.castro.bluefood.domain.cliente.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/public")
public class PublicController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/cliente/new")
    public String newCliente(Model model){

        //"cliente" representa object="${cliente} no cliente-cadastro"
        model.addAttribute("cliente",new Cliente());
        ControllerHelper.setEditMode(model,false);
        return "cliente-cadastro";
    }

    @PostMapping(path="/cliente/save")
    public String saveCliente(
            @ModelAttribute("cliente") @Valid Cliente c,
            Errors errors,
            Model model){

        if(!errors.hasErrors()){
            clienteService.saveCliente(c);
            model.addAttribute("msg","Cliente salvo com sucesso!");
        }

        ControllerHelper.setEditMode(model,false);
        return "cliente-cadastro";
    }
}
