package com.castro.bluefood.application.sevice;

import com.castro.bluefood.domain.pedido.*;
import com.castro.bluefood.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

//    @Autowired
//    private PagamentoRepository pagamentoRepository;
//
//    @Value("${bluefood.sbpay.url}")
//    private String sbPayUrl;
//
//    @Value("${bluefood.sbpay.token}")
//    private String sbPayToken;


//    @SuppressWarnings("unchecked")
//    @Transactional(rollbackFor = PagamentoException.class)
//    public Pedido criarEPagar(Carrinho carrinho, String numCartao) throws PagamentoException {
        public Pedido criarEPagar(Carrinho carrinho, String numCartao) {
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

//        DadosCartao dadosCartao = new DadosCartao();
//        dadosCartao.setNumCartao(numCartao);
//
//        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//        headers.add("Token", sbPayToken);
//
//        HttpEntity<DadosCartao> requestEntity = new HttpEntity<>(dadosCartao, headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        Map<String, String> response;
//        try {
//            response = restTemplate.postForObject(sbPayUrl, requestEntity, Map.class);
//        } catch (Exception e) {
//            throw new PagamentoException("Erro no servidor de pagamento");
//        }
//
//        StatusPagamento statusPagamento = StatusPagamento.valueOf(response.get("status"));
//
//        if (statusPagamento != StatusPagamento.Autorizado) {
//            throw new PagamentoException(statusPagamento.getDescricao());
//        }
//
//        Pagamento pagamento = new Pagamento();
//        pagamento.setData(LocalDateTime.now());
//        pagamento.setPedido(pedido);
//        pagamento.definirNumeroEBandeira(numCartao);
//        pagamentoRepository.save(pagamento);

        return pedido;
    }

}
