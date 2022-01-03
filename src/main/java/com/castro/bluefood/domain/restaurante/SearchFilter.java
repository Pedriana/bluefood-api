package com.castro.bluefood.domain.restaurante;

import lombok.Data;

//@Data - Equivale a colocar várias anotations
@Data
public class SearchFilter {
    public enum SearchType{
        Texto, Categoria
    }

    //Texto de busca
    private String texto;
    private SearchType searchType;
    private Integer categoriaId;

    public void processFilter(){
        if(searchType==SearchType.Texto){
            categoriaId=null;
        }else if(searchType==SearchType.Categoria){
            texto=null;
        }
    }
}
