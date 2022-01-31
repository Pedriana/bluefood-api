package com.castro.bluefood.domain.pedido;

import com.castro.bluefood.domain.cliente.Cliente;
import com.castro.bluefood.domain.pagamento.Pagamento;
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

        public String getDescricao(){
            return descricao;
        }

        public int getOrdem(){
            return ordem;
        }

        public boolean isUltimo() {
            return ultimo;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private LocalDateTime data;

    @NotNull
    private Status status;

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
    @Column(name = "taxa_entrega")
    private BigDecimal taxaEntrega;

    @NotNull
    private BigDecimal total;

    @OneToMany(mappedBy = "id.pedido",fetch = FetchType.EAGER)
    private Set<ItemPedido> itensPedido;

    public String getFormattedId(){
        return String.format("#%04d",id);
    }

}
