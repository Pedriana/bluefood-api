package com.castro.bluefood.domain.restaurante;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante,Integer> {

    public Restaurante findByEmail(String email);

    public List<Restaurante> findByNomeIgnoreCaseContaining(String nome);

    public List<Restaurante> findByCategorias_Id(Integer categoriaId);

}
