package com.castro.bluefood.domain.restaurante;

import com.castro.bluefood.domain.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante,Integer> {

    public Restaurante findByEmail(String email);
}
