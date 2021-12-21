package com.castro.bluefood.infrasctructure.web.controller;

import com.castro.bluefood.application.ClienteService;
import com.castro.bluefood.application.RestauranteService;
import com.castro.bluefood.application.ValidationException;
import com.castro.bluefood.domain.cliente.Cliente;
import com.castro.bluefood.domain.restaurante.CategoriaRestaurante;
import com.castro.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import com.castro.bluefood.domain.restaurante.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/public")
public class PublicController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private CategoriaRestauranteRepository categoriaRestauranteRepository;



    @GetMapping("/cliente/new")
    public String newCliente(Model model){

        //"cliente" representa object="${cliente} no cliente-cadastro"
        model.addAttribute("cliente",new Cliente());
        ControllerHelper.setEditMode(model,false);


        return "cliente-cadastro";
    }

    @GetMapping("/restaurante/new")
    public String newRestaurante(Model model) {
        model.addAttribute("restaurante", new Restaurante());
        ControllerHelper.setEditMode(model, false);
        ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
        return "restaurante-cadastro";
    }

    @PostMapping(path="/cliente/save")
    public String saveCliente(
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

        ControllerHelper.setEditMode(model,false);
        return "cliente-cadastro";
    }

    @PostMapping(path="/restaurante/save")
    public String saveRestaurante(
            @ModelAttribute("restaurante") @Valid Restaurante restaurante,
            Errors errors,
            Model model){

        if(!errors.hasErrors()){
            try {
                restauranteService.saveRestaurante(restaurante);
                model.addAttribute("msg","Restaurante salvo com sucesso!");
            }catch (ValidationException e){
                errors.rejectValue("email",null,e.getMessage());
            }
        }

        ControllerHelper.setEditMode(model,false);
        return "restaurante-cadastro";
    }
}
