package com.castro.bluefood.domain.pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class RelatorioItemFaturamento {

    private String nome;

    private Long quantidade;

    private BigDecimal valor;

}
