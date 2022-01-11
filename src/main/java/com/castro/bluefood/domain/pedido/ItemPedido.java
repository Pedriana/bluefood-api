package com.castro.bluefood.domain.pedido;

import com.castro.bluefood.domain.restaurante.ItemCardapio;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuppressWarnings("serial")
@Entity
@Table(name = "intem_pedido")
public class ItemPedido implements Serializable {

    //usar chave composta PK
//    private Integer Id;

    @EmbeddedId
    @EqualsAndHashCode.Include
    private ItemPedidoPK id;

    @NotNull
    @ManyToOne //*itens -> 1 pedido - Many indica o dono do relacionamento
    private ItemCardapio itemCardapio;

    @NotNull
    private Integer quantidade;

    @Size(max = 50)
    private String observacoes;

    @NotNull
    private BigDecimal preco;

    public BigDecimal getPrecoCalculado(){
        return preco.multiply(BigDecimal.valueOf(quantidade));
    }

}
