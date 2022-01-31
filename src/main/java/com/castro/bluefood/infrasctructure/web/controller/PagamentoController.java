package com.castro.bluefood.infrasctructure.web.controller;

import com.castro.bluefood.application.sevice.PagamentoException;
import com.castro.bluefood.application.sevice.PedidoService;
import com.castro.bluefood.domain.pedido.Carrinho;
import com.castro.bluefood.domain.pedido.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/cliente/pagamento")
@SessionAttributes("carrinho")
public class PagamentoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping(path = "/pagar")
    public String pagar(
            @RequestParam("numCartao") String numCartao,
            @ModelAttribute("carrinho") Carrinho carrinho,
            SessionStatus sessionStatus,
            Model model) {

        try {
            Pedido pedido = pedidoService.criarEPagar(carrinho, numCartao);
            sessionStatus.setComplete(); //limpa o carrinho, tira da sessao

            //redirect pede pro navegador redirecionar
            return "redirect:/cliente/pedido/view?pedidoId=" + pedido.getId();

        } catch (PagamentoException e) {
            model.addAttribute("msg", e.getMessage());
            return "cliente-carrinho";
        }
    }
}
