package com.castro.bluefood.domain.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido,Integer> {

    //Todos os pedidos de um determinado cliente
    @Query("SELECT p FROM Pedido p WHERE p.cliente.id=?1 ORDER BY p.data DESC")
//    ?1 - indica o primeiro paramentro passado abaixo
    public List<Pedido> listPedidosByClientes(Integer clienteId);
}
