package com.castro.bluefood.application.sevice;

import com.castro.bluefood.domain.pedido.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RelatorioService {
    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listPedidos(Integer restauranteId, RelatorioPedidoFilter filter){
        Integer pedidoId = filter.getPedidoId();

        if(pedidoId!=null){
            Pedido pedido = pedidoRepository.findByIdAndRestaurante_Id(pedidoId,restauranteId);
            return List.of(pedido);
        }

        LocalDate dataInicial=filter.getDataInicial();
        LocalDate dataFinal=filter.getDataFinal();

        if(dataInicial==null){
            //retorna uma lista vazia
            return List.of();
        }

        if(dataFinal==null){
//            assume a data atual
            dataFinal=LocalDate.now();
            return List.of();
        }

        return pedidoRepository.findByDateInterval(restauranteId,dataInicial.atStartOfDay(),dataFinal.atTime(23,59,59));
    }

    public List<RelatorioItemFaturamento> calcularFaturamentoItens(Integer restauranteId, RelatorioItemFilter filter) {
        List<Object[]> itensObj;

        Integer itemId = filter.getItemId();
        LocalDate dataInicial = filter.getDataInicial();
        LocalDate dataFinal = filter.getDataFinal();

        if (dataInicial == null) {
            return List.of();
        }

        if (dataFinal == null) {
            dataFinal = LocalDate.now();
        }

        LocalDateTime dataHoraInicial = dataInicial.atStartOfDay();
        LocalDateTime dataHoraFinal = dataFinal.atTime(23, 59, 59);

        if (itemId != 0) {
            itensObj = pedidoRepository.findItensForFaturamento(restauranteId, itemId, dataHoraInicial, dataHoraFinal);

        } else {
            itensObj = pedidoRepository.findItensForFaturamento(restauranteId, dataHoraInicial, dataHoraFinal);
        }

        List<RelatorioItemFaturamento> itens = new ArrayList<>();

        for (Object[] item : itensObj) {
            String nome = (String) item[0];
            Long quantidade = (Long) item[1];
            BigDecimal valor = (BigDecimal) item[2];
            itens.add(new RelatorioItemFaturamento(nome, quantidade, valor));
        }

        return itens;
    }

}
