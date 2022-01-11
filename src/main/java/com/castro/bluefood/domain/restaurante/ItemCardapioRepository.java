package com.castro.bluefood.domain.restaurante;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemCardapioRepository extends JpaRepository<ItemCardapio,Integer> {

    public List<ItemCardapio> findByRestaurante_IdOrderByNome(Integer restauranteId);

    public List<ItemCardapio> findByRestaurante_IdAndDestaqueOrderByNome(Integer restauranteId,boolean destaque);

    public List<ItemCardapio> findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(Integer restauranteId,boolean destaque, String Categoria);

    //JPQL - é uma especie de SQL do JPA, trabalha com elementos de objetos (classes e atributos)
    //?1 pegar o primeiro parametro passado
    @Query("SELECT DISTINCT ic.categoria FROM ItemCardapio ic WHERE ic.restaurante.id = ?1 ORDER BY ic.categoria")
    public List<String> findCategorias (Integer restauranteId);

}
