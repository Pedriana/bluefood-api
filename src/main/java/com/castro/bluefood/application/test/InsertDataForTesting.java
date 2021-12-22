package com.castro.bluefood.application.test;


import com.castro.bluefood.domain.cliente.Cliente;
import com.castro.bluefood.domain.cliente.ClienteRepository;
import com.castro.bluefood.domain.restaurante.CategoriaRestaurante;
import com.castro.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import com.castro.bluefood.domain.restaurante.Restaurante;
import com.castro.bluefood.domain.restaurante.RestauranteRepository;
import com.castro.bluefood.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

//Pro spring conhecer a classe usa-se Component
@Component
public class InsertDataForTesting {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CategoriaRestauranteRepository categoriaRestauranteRepository;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event){
        Cliente[] clientes = clientes();
        Restaurante[] restaurantes = restaurantes();
    }

    private Restaurante[] restaurantes() {
        List<Restaurante> restaurantes = new ArrayList<>();

        CategoriaRestaurante categoriaPizza = categoriaRestauranteRepository.findById(1).orElseThrow(NoSuchElementException::new);
        CategoriaRestaurante categoriaSanduiche = categoriaRestauranteRepository.findById(2).orElseThrow(NoSuchElementException::new);
        CategoriaRestaurante categoriaSobremesa = categoriaRestauranteRepository.findById(5).orElseThrow(NoSuchElementException::new);
        CategoriaRestaurante categoriaJapones = categoriaRestauranteRepository.findById(6).orElseThrow(NoSuchElementException::new);

        Restaurante r = new Restaurante();
        r.setNome("Bubger King");
        r.setEmail("r1@bluefood.com.br");
        r.setSenha(StringUtils.encrypt("r"));
        r.setCnpj("00000000000101");
        r.setTaxaEntrega(BigDecimal.valueOf(3.2));
        r.setTelefone("99876671010");
        r.getCategorias().add(categoriaSanduiche);
        r.getCategorias().add(categoriaSobremesa);
        r.setLogotipo("0001-logo.png");
        r.setTempoEntregaBase(30);
        restauranteRepository.save(r);
        restaurantes.add(r);

        r = new Restaurante();
        r.setNome("Mc Naldo's");
        r.setEmail("r2@bluefood.com.br");
        r.setSenha(StringUtils.encrypt("r"));
        r.setCnpj("00000000000102");
        r.setTaxaEntrega(BigDecimal.valueOf(4.5));
        r.setTelefone("99876671011");
        r.getCategorias().add(categoriaSanduiche);
        r.getCategorias().add(categoriaSobremesa);
        r.setLogotipo("0002-logo.png");
        r.setTempoEntregaBase(25);
        restauranteRepository.save(r);
        restaurantes.add(r);

        r = new Restaurante();
        r.setNome("Sbubby");
        r.setEmail("r3@bluefood.com.br");
        r.setSenha(StringUtils.encrypt("r"));
        r.setCnpj("00000000000103");
        r.setTaxaEntrega(BigDecimal.valueOf(12.2));
        r.setTelefone("99876671012");
        r.getCategorias().add(categoriaSanduiche);
        r.getCategorias().add(categoriaSobremesa);
        r.setLogotipo("0003-logo.png");
        r.setTempoEntregaBase(38);
        restauranteRepository.save(r);
        restaurantes.add(r);

        r = new Restaurante();
        r.setNome("Pizza Brut");
        r.setEmail("r4@bluefood.com.br");
        r.setSenha(StringUtils.encrypt("r"));
        r.setCnpj("00000000000104");
        r.setTaxaEntrega(BigDecimal.valueOf(9.8));
        r.setTelefone("99876671013");
        r.getCategorias().add(categoriaPizza);
        r.getCategorias().add(categoriaSobremesa);
        r.setLogotipo("0004-logo.png");
        r.setTempoEntregaBase(22);
        restauranteRepository.save(r);
        restaurantes.add(r);

        r = new Restaurante();
        r.setNome("Wiki Japa");
        r.setEmail("r5@bluefood.com.br");
        r.setSenha(StringUtils.encrypt("r"));
        r.setCnpj("00000000000105");
        r.setTaxaEntrega(BigDecimal.valueOf(14.9));
        r.setTelefone("99876671014");
        r.getCategorias().add(categoriaJapones);
        r.getCategorias().add(categoriaSobremesa);
        r.setLogotipo("0005-logo.png");
        r.setTempoEntregaBase(19);
        restauranteRepository.save(r);
        restaurantes.add(r);

        Restaurante[] array = new Restaurante[restaurantes.size()];
        return restaurantes.toArray(array);
    }

    private Cliente[] clientes() {
        List<Cliente> clientes = new ArrayList<>();

        Cliente c = new Cliente();
        c.setNome("João Silva");
        c.setEmail("joao@bluefood.com.br");
        c.setSenha(StringUtils.encrypt("c"));
        c.setCep("89300100");
        c.setCpf("03099887666");
        c.setTelefone("99355430001");
        clientes.add(c);
        clienteRepository.save(c);

        c = new Cliente();
        c.setNome("Maria Torres");
        c.setEmail("maria@bluefood.com.br");
        c.setSenha(StringUtils.encrypt("c"));
        c.setCep("89300101");
        c.setCpf("03099887677");
        c.setTelefone("99355430002");
        clientes.add(c);
        clienteRepository.save(c);

        Cliente[] array = new Cliente[clientes.size()];
        return clientes.toArray(array);
    }
}
