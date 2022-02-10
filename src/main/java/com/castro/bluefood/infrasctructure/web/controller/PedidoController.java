package com.castro.bluefood.infrasctructure.web.controller;

import com.castro.bluefood.domain.pedido.Pedido;
import com.castro.bluefood.domain.pedido.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/cliente/pedido")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping(path = "/view")
    public String viewPedido(
            @RequestParam("pedidoId") Integer pedidoId,
            Model model){

        Pedido pedido =pedidoRepository.findById(pedidoId).orElseThrow(NoSuchElementException::new);
        model.addAttribute("pedido",pedido);
        return "cliente-pedido";
    }
}
