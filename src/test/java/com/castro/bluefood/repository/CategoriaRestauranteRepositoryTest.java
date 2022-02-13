package com.castro.bluefood.repository;

import static org.assertj.core.api.Assertions.assertThat;
import com.castro.bluefood.domain.restaurante.CategoriaRestaurante;
import com.castro.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;

@DataJpaTest
@ActiveProfiles("test")
public class CategoriaRestauranteRepositoryTest {

    @Autowired
    private CategoriaRestauranteRepository categoriaRestauranteRepository;

    @Test
    public void testInsertAndDelete() throws Exception{
        CategoriaRestaurante cr = new CategoriaRestaurante();
        cr.setNome("Chinesa");;
        cr.setImagem("chinesa.png");
        categoriaRestauranteRepository.save(cr);

//        assertThat (garanta que)
        assertThat(cr.getId()).isNotNull();
        CategoriaRestaurante cr2 = categoriaRestauranteRepository.findById(cr.getId()).orElseThrow(NoSuchElementException::new);
        assertThat(cr.getNome()).isEqualTo(cr2.getNome());
        assertThat(categoriaRestauranteRepository.findAll()).hasSize(7);

        categoriaRestauranteRepository.delete(cr);
        assertThat(categoriaRestauranteRepository.findAll()).hasSize(6);
    }

}
