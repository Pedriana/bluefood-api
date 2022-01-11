package com.castro.bluefood.domain.pedido;

//ItemPedidoPK  é uma convenção por padrão pra indicar a classe que representa a PK
// pedido e ordem : PK composta

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@AllArgsConstructor //recebe os 2 parâmetros
@NoArgsConstructor // sem parâmetros
@EqualsAndHashCode
@SuppressWarnings("serial")
@Embeddable //
public class ItemPedidoPK implements Serializable {

    @NotNull
    @ManyToOne // é também uma chave estrangeira
    private Pedido pedido;

    @NotNull
    private Integer ordem;
}
