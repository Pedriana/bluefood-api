package com.castro.bluefood.infrasctructure.web.controller;

import com.castro.bluefood.application.sevice.ClienteService;
import com.castro.bluefood.application.sevice.ValidationException;
import com.castro.bluefood.domain.cliente.Cliente;
import com.castro.bluefood.domain.cliente.ClienteRepository;
import com.castro.bluefood.util.SecurityUtils;
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
@RequestMapping(path = "/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @GetMapping(path = "/home")
    public String home(){
        return "cliente-home";
    }

    @GetMapping("/edit")
    public String edit(Model model){
        //editar o cliente que está logado
        Integer clienteId = SecurityUtils.loggedCliente().getId();

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();

        model.addAttribute("cliente",cliente);

        ControllerHelper.setEditMode(model,true);

        return "cliente-cadastro";
    }

    @PostMapping("/save")
    public String save(
            @ModelAttribute("cliente") @Valid Cliente c,
            Errors errors,
            Model model){

        if(!errors.hasErrors()){
            try {
                clienteService.saveCliente(c);
                model.addAttribute("msg","Cliente salvo com sucesso!");
            }catch (ValidationException e){
                errors.rejectValue("email",null,e.getMessage());
            }
        }

        ControllerHelper.setEditMode(model,true);
        return "cliente-cadastro";
    }

}
