package com.castro.bluefood.domain.pedido;

import com.castro.bluefood.domain.cliente.Cliente;
import com.castro.bluefood.domain.restaurante.Restaurante;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

    public enum Status{
        //o status tem uma ordem
        Producao(1,"Em produção",false),
        Entrega(2,"Saiu pra entrega",false),
        Concluido(3,"Concluído",true);

        Status(int ordem, String descricao, boolean ultimo){
            this.ordem=ordem;
            this.descricao=descricao;
            this.ultimo=ultimo;
        }

        int ordem;
        String descricao;
        boolean ultimo;//indica se é o último status
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private LocalDateTime data;

    @NotNull
    //*pedidos -> 1 cliente
    @ManyToOne
    private Cliente cliente;

    @NotNull
    //*pedidos -> 1 restaurante
    @ManyToOne
    private Restaurante restaurante;

    @NotNull
    //preço sem a taxa de entrega
    private BigDecimal subtotal;

    @NotNull
    private BigDecimal total;

    @OneToMany(mappedBy = "id.pedido")
    private Set<ItemPedido> itensPedido;

}
