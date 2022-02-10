package com.castro.bluefood.infrasctructure.web.controller;

import com.castro.bluefood.application.sevice.RelatorioService;
import com.castro.bluefood.application.sevice.RestauranteService;
import com.castro.bluefood.application.sevice.ValidationException;
import com.castro.bluefood.domain.cliente.Cliente;
import com.castro.bluefood.domain.pedido.*;
import com.castro.bluefood.domain.restaurante.*;
import com.castro.bluefood.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping(path = "/restaurante")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RelatorioService relatorioService;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CategoriaRestauranteRepository categoriaRestauranteRepository;

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    @GetMapping(path = "/home")
    public String home(Model model){

        Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
        List<Pedido> pedidos = pedidoRepository.findByRestaurante_IdOrderByDataDesc(restauranteId);
        model.addAttribute("pedidos",pedidos);

        return "restaurante-home";
    }


    @GetMapping("/edit")
    public String edit(Model model){

        Integer restauranteId = SecurityUtils.loggedRestaurante().getId();

        Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow(NoSuchElementException::new);

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

    @GetMapping(path = "/comidas")
    public String viewComidas(
            Model model
    ){

        Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
        Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow(NoSuchElementException::new);
        model.addAttribute("restaurante",restaurante);

        List<ItemCardapio> itensCardapio = itemCardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);

        model.addAttribute("itensCardapio",itensCardapio);
        model.addAttribute("itemCardapio",new ItemCardapio());


        return "restaurante-comidas";
    }

    @GetMapping(path = "/comidas/remover")
    public String remover(
            @RequestParam("itemId") Integer itemId, Model model
    ){
        itemCardapioRepository.deleteById(itemId);
        return "redirect:/restaurante/comidas";
    }

    @PostMapping(path = "/comidas/cadastrar")
    public String cadastrar(@Valid @ModelAttribute("itemCardapio") ItemCardapio itemCardapio,
                          Errors errors,
                          Model model){
        if(errors.hasErrors()){
            Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
            Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow(NoSuchElementException::new);
            model.addAttribute("restaurante",restaurante);

            List<ItemCardapio> itensCardapio = itemCardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);
            model.addAttribute("itensCardapio",itensCardapio);

            return "restaurante-comidas";
        }

        restauranteService.saveItemCardapio(itemCardapio);
        return "redirect:/restaurante/comidas";
    }

    @GetMapping(path = "/pedido")
    private String viewPedido(
            @RequestParam("pedidoId") Integer pedidoId,
            Model model
    ){
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(NoSuchElementException::new);
        model.addAttribute("pedido",pedido);
        return "restaurante-pedido";

    }

    @PostMapping(path = "/pedido/proximoStatus")
    public String proximoStatus(
            @RequestParam("pedidoId") Integer pedidoId,
            Model model
    ){
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(NoSuchElementException::new);
        pedido.definirProximoStatus();
        pedidoRepository.save(pedido);
        model.addAttribute("pedido",pedido);
        model.addAttribute("msg","Status alterando com sucesso");

        return "restaurante-pedido";
    }

    @GetMapping(path = "/relatorio/pedidos")
    public String relatorioPedidos(
            @ModelAttribute("relatorioPedidoFilter")RelatorioPedidoFilter filter,
            Model model
            ){

        Integer restauranteId = SecurityUtils.loggedRestaurante().getId();

        List<Pedido> pedidos = relatorioService.listPedidos(restauranteId,filter);

        model.addAttribute("pedidos",pedidos);
        model.addAttribute("filter",filter);

        return "restaurante-relatorio-pedidos";
    }

    @GetMapping("/relatorio/itens")
    public String relatorioItens(
            @ModelAttribute("relatorioItemFilter") RelatorioItemFilter filter,
            Model model) {
        Integer restauranteId = SecurityUtils.loggedRestaurante().getId();

        List<ItemCardapio> itensCardapio = itemCardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);
        model.addAttribute("itensCardapio", itensCardapio);

        List<RelatorioItemFaturamento> itensCalculados = relatorioService.calcularFaturamentoItens(restauranteId, filter);
        model.addAttribute("itensCalculados", itensCalculados);

        model.addAttribute("relatorioItemFilter", filter);

        return "restaurante-relatorio-itens";
    }

}
