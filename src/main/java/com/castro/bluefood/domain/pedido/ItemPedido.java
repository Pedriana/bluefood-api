package com.castro.bluefood.domain.pedido;

import com.castro.bluefood.domain.restaurante.ItemCardapio;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPedido {

    @EqualsAndHashCode.Include
    private Integer Id;

    private ItemCardapio itemCardapio;

    private Integer quantidade;

    private String observacoes;

    private BigDecimal preco;

    public BigDecimal getPrecoCalculado(){
        return preco.multiply(BigDecimal.valueOf(quantidade));
    }

}
