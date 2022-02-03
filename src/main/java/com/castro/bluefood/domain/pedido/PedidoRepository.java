package com.castro.bluefood.domain.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido,Integer> {

    @Query("SELECT p FROM Pedido p WHERE p.cliente.id=?1 ORDER BY p.data DESC")
    public List<Pedido> listPedidosByClientes(Integer clienteId);

    public List<Pedido> findByRestaurante_IdOrderByDataDesc(Integer restauranteId);

    public Pedido findByIdAndRestaurante_Id(Integer pedidoId, Integer restauranteId);

    @Query("SELECT p FROM Pedido p WHERE p.restaurante.id = ?1 AND p.data BETWEEN ?2 AND ?3")
    public List<Pedido> findByDateInterval(Integer restauranteId, LocalDateTime dataInicial, LocalDateTime dataFinal);

    //JPQL
    @Query("SELECT i.itemCardapio.nome, COUNT(i.itemCardapio.id), SUM(i.preco) FROM Pedido p INNER JOIN p.itens i "
            + "WHERE p.restaurante.id = ?1 AND i.itemCardapio.id = ?2 AND p.data BETWEEN ?3 AND ?4  GROUP BY i.itemCardapio.nome")
    public List<Object[]> findItensForFaturamento(Integer restauranteId, Integer itemCardapioId, LocalDateTime dataInicial, LocalDateTime dataFinal);

    @Query("SELECT i.itemCardapio.nome, COUNT(i.itemCardapio.id), SUM(i.preco) FROM Pedido p INNER JOIN p.itens i "
            + "WHERE p.restaurante.id =?1 AND p.data BETWEEN ?2 AND ?3 GROUP BY i.itemCardapio.nome")
    public List<Object[]> findItensForFaturamento(Integer restauranteId, LocalDateTime dataInicial, LocalDateTime dataFinal);

}
