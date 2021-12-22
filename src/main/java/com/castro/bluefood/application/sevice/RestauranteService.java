package com.castro.bluefood.application.sevice;

import com.castro.bluefood.domain.cliente.Cliente;
import com.castro.bluefood.domain.cliente.ClienteRepository;
import com.castro.bluefood.domain.restaurante.Restaurante;
import com.castro.bluefood.domain.restaurante.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ImageService imageService;

    @Transactional
    public void saveRestaurante(Restaurante res) throws ValidationException{
        if(!validateEmail(res.getEmail(),res.getId())){//!false
            System.out.println("Erro na validação");
            throw new ValidationException("O email está duplicado");
        }

        if(res.getId()!=null){
            Restaurante restauranteDB = restauranteRepository.findById(res.getId()).orElseThrow();
            res.setSenha(restauranteDB.getSenha());

        }else{
            res.encryptPassword();
            res = restauranteRepository.save(res);
            res.setLogotipoFileName();//nomeia o arquivo
            imageService.uploadLogotipo(res.getLogotipoFile(),res.getLogotipo());

        }
        System.out.println("SEM Erro na validação");
        restauranteRepository.save(res);
    }

    private boolean validateEmail(String email,Integer id){
        Cliente cliente = clienteRepository.findByEmail(email);

        if(cliente!=null){
            return false;
        }

        Restaurante res = restauranteRepository.findByEmail(email);// sim

        if(res!=null){ // sim
            if(id==null){ //sim
                return false;
            }
            if(!res.getId().equals(id)){
                return false;
            }
        }
        return true;
    }
}
