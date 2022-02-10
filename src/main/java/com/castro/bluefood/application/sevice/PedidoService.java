package com.castro.bluefood.application.sevice;

import com.castro.bluefood.domain.pagamento.DadosCartao;
import com.castro.bluefood.domain.pagamento.Pagamento;
import com.castro.bluefood.domain.pagamento.PagamentoRepository;
import com.castro.bluefood.domain.pagamento.StatusPagamento;
import com.castro.bluefood.domain.pedido.*;
import com.castro.bluefood.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

//pega esse Value do application.properties
    @Value("${bluefood.sbpay.url}")
    private String sbPayUrl;
//
    @Value("${bluefood.sbpay.token}")
    private String sbPayToken;


    @SuppressWarnings("unchecked")
    //rollbackFor = PagamentoException.clas se ocorrer um erro em PagamentoExceptio, desfaz tudo
    @Transactional(rollbackFor = PagamentoException.class)
    public Pedido criarEPagar(Carrinho carrinho, String numCartao) throws PagamentoException {
        Pedido pedido = new Pedido();
        pedido.setData(LocalDateTime.now());
        pedido.setCliente(SecurityUtils.loggedCliente());
        pedido.setRestaurante(carrinho.getRestaurante());
        pedido.setStatus(Pedido.Status.Producao);
        pedido.setTaxaEntrega(carrinho.getRestaurante().getTaxaEntrega());
        pedido.setSubtotal(carrinho.getPrecoTotal(false));//sem a taxa de entrega
        pedido.setTotal(carrinho.getPrecoTotal(true));//com a taxa de entrega

        pedido = pedidoRepository.save(pedido);

        int ordem = 1;

        for (ItemPedido itemPedido : carrinho.getItens()) {
            //item pedido não tem o Id gerado automaticamente, entao tem que ser criado
            itemPedido.setId(new ItemPedidoPK(pedido, ordem++));
            itemPedidoRepository.save(itemPedido);
        }

        DadosCartao dadosCartao = new DadosCartao();
        dadosCartao.setNumCartao(numCartao);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Token", sbPayToken);
        //HttpEntity - transporta os dados via dados http (body, header)
        HttpEntity<DadosCartao> requestEntity = new HttpEntity<>(dadosCartao, headers);

//RestTemplate é utilizado pra fazer invocação de webservice
        RestTemplate restTemplate = new RestTemplate();
//
        Map<String, String> response;
        try {
            response = restTemplate.postForObject(sbPayUrl, requestEntity, Map.class);
        } catch (Exception e) {
            throw new PagamentoException("Erro no servidor de pagamento");
        }
        StatusPagamento statusPagamento = StatusPagamento.valueOf(response.get("statusPagamento"));

        if (statusPagamento != StatusPagamento.Autorizado) {
            throw new PagamentoException(statusPagamento.getDescricao());
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setData(LocalDateTime.now());
        pagamento.setPedido(pedido);
        pagamento.definirNumeroEBandeira(numCartao);
        pagamentoRepository.save(pagamento);

        return pedido;
    }

}
