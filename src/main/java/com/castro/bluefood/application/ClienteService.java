package com.castro.bluefood.application;

import com.castro.bluefood.domain.cliente.Cliente;
import com.castro.bluefood.domain.cliente.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    //Autowired - cria uma instância
    @Autowired
    private ClienteRepository clienteRepository;

    public void saveCliente(Cliente cliente){
            clienteRepository.save(cliente);
    }

}
