package com.castro.bluefood.infrasctructure.web.controller;

import com.castro.bluefood.domain.pedido.*;
import com.castro.bluefood.domain.restaurante.ItemCardapio;
import com.castro.bluefood.domain.restaurante.ItemCardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/cliente/carrinho")
//Quais atributos tem que ficar no model após várias requisições: @SessionAttributes("carrinho")
@SessionAttributes("carrinho")
public class CarrinhoController {

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    @Autowired
    private PedidoRepository pedidoRepository;


    //@ModelAttribute("carrinho") - pra quando eu precisar acessar o carrinho
    @ModelAttribute("carrinho")
    public Carrinho carrinho(){
        return new Carrinho();
    }

    @GetMapping(path = "/visualizar")
    public String viewCarrinho() {
        return "cliente-carrinho";
    }

    @GetMapping(path = "/adicionar")
    public String adicionarItem(
            //itemId é o item do cardapio
            @RequestParam("itemId") Integer itemId,
            @RequestParam("quantidade") Integer quantidade,
            @RequestParam("observacoes") String observacoes,
            @ModelAttribute("carrinho") Carrinho carrinho,
            Model model
    ){
        ItemCardapio itemCardapio = itemCardapioRepository.findById(itemId).orElseThrow(NoSuchElementException::new);

        //carrinho tem que está ativo na sessão do usuário:
        try {
            System.out.println(itemCardapio);
            carrinho.adicionarItem(itemCardapio,quantidade,observacoes);
        } catch (RestauranteDiferenteException e) {
            System.out.println("DEU RUIM!");
            model.addAttribute("msg","Não é possível adicionar comidas de restaurantes diferentes");
        }

        return "cliente-carrinho";
    }

    @GetMapping(path = "/remover")
    public String removerItem(
            @RequestParam("itemId") Integer itemId,
            @ModelAttribute("carrinho") Carrinho carrinho,
            SessionStatus sessionStatus,
            Model model) {

        ItemCardapio itemCardapio = itemCardapioRepository.findById(itemId).orElseThrow(NoSuchElementException::new);

        carrinho.removerItem(itemCardapio);

        //eliminar da sessao (atributos ligados a sessao: carrinho): sessionStatus.setComplete();
        if (carrinho.vazio()) {
            sessionStatus.setComplete();
        }

        return "cliente-carrinho";
    }

    @GetMapping(path = "/refazerCarrinho")
    public String refazerCarrinho(
            @RequestParam("pedidoId") Integer pedidoId,
            @ModelAttribute("carrinho") Carrinho carrinho,
            Model model ) {

        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(NoSuchElementException::new);
        carrinho.limpar();

        for(ItemPedido  itemPedido:pedido.getItensPedido()){
            carrinho.adicionarItem((itemPedido));
        }

        return "cliente-carrinho";

    }

}
