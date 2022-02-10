package com.castro.bluefood.application.sevice;

import com.castro.bluefood.domain.cliente.Cliente;
import com.castro.bluefood.domain.cliente.ClienteRepository;
import com.castro.bluefood.domain.restaurante.*;
import com.castro.bluefood.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    ItemCardapioRepository itemCardapioRepository;

    @Transactional
    public void saveRestaurante(Restaurante res) throws ValidationException{
        if(!validateEmail(res.getEmail(),res.getId())){//!false
            System.out.println("Erro na validação");
            throw new ValidationException("O email está duplicado");
        }

        if(res.getId()!=null){
            Restaurante restauranteDB = restauranteRepository.findById(res.getId()).orElseThrow(NoSuchElementException::new);
            res.setSenha(restauranteDB.getSenha());
            res.setLogotipo(restauranteDB.getLogotipo());
            restauranteRepository.save(res);

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

    public List<Restaurante> search(SearchFilter filter){
        List<Restaurante> restaurantes;
        if(filter.getSearchType()== SearchFilter.SearchType.Texto){
            restaurantes=restauranteRepository.findByNomeIgnoreCaseContaining(filter.getTexto());
        }else if(filter.getSearchType()== SearchFilter.SearchType.Categoria){
            restaurantes=restauranteRepository.findByCategorias_Id(filter.getCategoriaId());
        }else{
            throw new IllegalStateException(("O tipo de busca" + filter.getSearchType()+" não é suportado"));
        }
        Iterator<Restaurante> it = restaurantes.iterator();

        while ((it.hasNext())){
            Restaurante restaurante = it.next();
            double taxaEntrega = restaurante.getTaxaEntrega().doubleValue();

            if((filter.isEntregaGratis() && taxaEntrega>0) || (!filter.isEntregaGratis() && taxaEntrega==0)){
                it.remove();//por isso estamos usando o iterator
            }


        }

        RestauranteComparator comparator = new RestauranteComparator(filter, SecurityUtils.loggedCliente().getCep());

        restaurantes.sort(comparator);

        return restaurantes;
    }
    @Transactional
    public void saveItemCardapio(ItemCardapio itemCardapio){
        itemCardapio = itemCardapioRepository.save(itemCardapio);
        itemCardapio.setImagemFileName();
        imageService.uploadComida(itemCardapio.getImagemFile(),itemCardapio.getImagem());
    }
}
