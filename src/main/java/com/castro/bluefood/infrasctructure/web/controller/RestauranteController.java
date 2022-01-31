package com.castro.bluefood.infrasctructure.web.controller;

import com.castro.bluefood.application.sevice.RestauranteService;
import com.castro.bluefood.application.sevice.ValidationException;
import com.castro.bluefood.domain.cliente.Cliente;
import com.castro.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import com.castro.bluefood.domain.restaurante.Restaurante;
import com.castro.bluefood.domain.restaurante.RestauranteRepository;
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
@RequestMapping(path = "/restaurante")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CategoriaRestauranteRepository categoriaRestauranteRepository;

    @GetMapping(path = "/home")
    public String home(){
        return "restaurante-home";
    }

    @GetMapping("/edit")
    public String edit(Model model){

        Integer restauranteId = SecurityUtils.loggedRestaurante().getId();

        Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow();

        model.addAttribute("restaurante",restaurante);

        ControllerHelper.setEditMode(model,true);
        ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository,model);

        return "restaurante-cadastro";
    }

    @PostMapping("/save")
    public String save(
            @ModelAttribute("restaurante") @Valid Restaurante r,
            Errors errors,
            Model model){

        if(!errors.hasErrors()){
            try {
                restauranteService.saveRestaurante(r);
                model.addAttribute("msg","Restaurante salvo com sucesso!");
            }catch (ValidationException e){
                errors.rejectValue("email",null,e.getMessage());
            }
        }

        ControllerHelper.setEditMode(model,true);
        ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository,model);
        return "restaurante-cadastro";
    }
}
