package com.castro.bluefood.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.castro.bluefood.application.sevice.ClienteService;

import com.castro.bluefood.application.sevice.ValidationException;
import com.castro.bluefood.domain.cliente.Cliente;
import com.castro.bluefood.domain.cliente.ClienteRepository;
//import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


@SpringBootTest
@ActiveProfiles("test")
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    //MockBean não busca no banco, mas sim no que eu criei no código
    @MockBean
    private ClienteRepository clienteRepository;

    @Test
    public void testWhenDuplicateEmail() throws Exception{
        Cliente c1 = new Cliente();
        c1.setId(1);
        c1.setNome("Pedriana");
        c1.setEmail("pedriana@gmail.com");

        Mockito.when(clienteRepository.findByEmail(c1.getEmail())).thenReturn(c1);

        Cliente c2 = new Cliente();
        c2.setEmail("pedriana@gmail.com");

        //Garante que uma exception vai ser lançada.
        assertThatExceptionOfType(ValidationException.class).isThrownBy(()-> clienteService.saveCliente(c2));


    }
}
