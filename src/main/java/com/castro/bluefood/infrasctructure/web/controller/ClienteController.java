package com.castro.bluefood.infrasctructure.web.controller;

import com.castro.bluefood.application.sevice.ClienteService;
import com.castro.bluefood.application.sevice.RestauranteService;
import com.castro.bluefood.application.sevice.ValidationException;
import com.castro.bluefood.domain.cliente.Cliente;
import com.castro.bluefood.domain.cliente.ClienteRepository;
import com.castro.bluefood.domain.pedido.Pedido;
import com.castro.bluefood.domain.pedido.PedidoRepository;
import com.castro.bluefood.domain.restaurante.*;
import com.castro.bluefood.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping(path = "/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CategoriaRestauranteRepository categoriaRestauranteRepository;

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping(path = "/home")
    public String home(Model model){

        List<CategoriaRestaurante> categorias = categoriaRestauranteRepository.findAll(Sort.by("nome"));
        model.addAttribute("categorias",categorias);

        //pra estar disponível na home desde o início
        model.addAttribute("searchFilter",new SearchFilter());

        List<Pedido> pedidos = pedidoRepository.listPedidosByClientes(SecurityUtils.loggedCliente().getId());
        model.addAttribute("pedidos",pedidos);

        return "cliente-home";
    }

    @GetMapping("/edit")
    public String edit(Model model){
        //editar o cliente que está logado
        Integer clienteId = SecurityUtils.loggedCliente().getId();

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(NoSuchElementException::new);

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

    @GetMapping(path="/search")
    public String search(
                @ModelAttribute("searchFilter") SearchFilter filter,
                @RequestParam(value="cmd",required = false) String command,
                Model model){

        filter.processFilter(command);

        List<Restaurante> restaurantes = restauranteService.search(filter);

        model.addAttribute("restaurantes",restaurantes);
        model.addAttribute("cep",SecurityUtils.loggedCliente().getCep());
//
        //para ter categorias em cliente-busca
        ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository,model);

        model.addAttribute("searchFilter",filter);
        return "cliente-busca";
    }

    @GetMapping(path = "/restaurante")
    public String viewRestaurante(@RequestParam("restauranteId") Integer restauranteId,
                                  @RequestParam(value = "categoria",required = false) String categoria,
                                  Model model){

        Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow(NoSuchElementException::new);
        model.addAttribute("restaurante",restaurante);
        model.addAttribute("cep",SecurityUtils.loggedCliente().getCep());

        List<String> categorias = itemCardapioRepository.findCategorias(restauranteId);
        model.addAttribute("categorias",categorias);

        List<ItemCardapio> itensCardapioDestaque;
        List<ItemCardapio> itensCardapioNaoDestaque;

        if(categoria==null){

            itensCardapioDestaque=itemCardapioRepository.findByRestaurante_IdAndDestaqueOrderByNome(restauranteId,true);
            itensCardapioNaoDestaque=itemCardapioRepository.findByRestaurante_IdAndDestaqueOrderByNome(restauranteId,false);

        }else {

            itensCardapioDestaque = itemCardapioRepository.findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(restauranteId, true,categoria);
            itensCardapioNaoDestaque = itemCardapioRepository.findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(restauranteId, false,categoria);

        }

        model.addAttribute("itensCardapioDestaque", itensCardapioDestaque);
        model.addAttribute("itensCardapioNaoDestaque", itensCardapioNaoDestaque);
        model.addAttribute("categoriaSelecionada",categoria);

        return "cliente-restaurante";
    }
}
