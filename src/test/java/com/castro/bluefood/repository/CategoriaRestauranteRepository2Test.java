package com.castro.bluefood.repository;

import com.castro.bluefood.domain.restaurante.CategoriaRestaurante;
import com.castro.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CategoriaRestauranteRepository2Test {

    // outra forma de implementar teste usando:  TestEntityManager

    @Autowired
    private TestEntityManager em;// para operações que vão ser refletidas no banco de dados

    @Test
    public void testInsertAndDelete() throws Exception{
        CategoriaRestaurante cr = new CategoriaRestaurante();
        cr.setNome("Chinesa");;
        cr.setImagem("chinesa.png");
        em.persistAndFlush(cr);

        assertThat(cr.getId()).isNotNull();
        CategoriaRestaurante cr2 = em.find(CategoriaRestaurante.class, cr.getId());
        assertThat(cr.getNome()).isEqualTo(cr2.getNome());
        em.remove(cr);
        assertThat(em.find(CategoriaRestaurante.class,cr.getId())).isNull();
    }

}
