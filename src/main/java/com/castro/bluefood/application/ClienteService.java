package com.castro.bluefood.application;

import com.castro.bluefood.domain.cliente.Cliente;
import com.castro.bluefood.domain.cliente.ClienteRepository;
import com.castro.bluefood.domain.restaurante.Restaurante;
import com.castro.bluefood.domain.restaurante.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Transactional
    public void saveCliente(Cliente c) throws ValidationException{
        if(!validateEmail(c.getEmail(),c.getId())){//!false
            System.out.println("Erro na validação");
            throw new ValidationException("O email está duplicado");
        }

        if(c.getId()!=null){
            Cliente clienteDB = clienteRepository.findById(c.getId()).orElseThrow();
            c.setSenha(clienteDB.getSenha());
        }else{
            c.encryptPassword();
        }
        System.out.println("SEM Erro na validação");
        clienteRepository.save(c);
    }

    private boolean validateEmail(String email,Integer id){
        Restaurante restaurante = restauranteRepository.findByEmail(email);
        Cliente c = clienteRepository.findByEmail(email);// sim

        if(restaurante==null){
            return false;
        }
        if(c!=null){ // sim
            if(id==null){ //sim
                return false;
            }
            if(!c.getId().equals(id)){
                return false;
            }
        }
        return true;
    }
}
