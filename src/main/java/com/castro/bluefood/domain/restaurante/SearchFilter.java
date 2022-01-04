package com.castro.bluefood.domain.restaurante;

import com.castro.bluefood.util.StringUtils;
import lombok.Data;

//@Data - Equivale a colocar várias anotations
@Data
public class SearchFilter {
    public enum SearchType{
        Texto, Categoria
    }

    public enum Order{
        Taxa,Tempo
    }

    public enum Command{
        EntregaGratis, MaiorTaxa, MenorTaxa, MaiorTempo, MenorTempo;
    }

    //Texto de busca
    private String texto;
    private SearchType searchType;
    private Integer categoriaId;
    private  Order order = Order.Taxa;
    private boolean asc;
    private boolean entregaGratis;

    public void processFilter(String cmdString){
        if(!StringUtils.isEmpty(cmdString)){
            Command cmd = Command.valueOf(cmdString);
            if(cmd==Command.EntregaGratis){
                entregaGratis=!entregaGratis;
            }else if (cmd==Command.MaiorTaxa){
                order=Order.Taxa;
                asc=false;
            }else if (cmd==Command.MenorTaxa){
                order=Order.Taxa;
                asc=true;
            }else if (cmd==Command.MaiorTempo){
                order=Order.Tempo;
                asc=false;
            }else if (cmd==Command.MenorTempo) {
                order = Order.Tempo;
                asc = true;
            }
        }

        if(searchType==SearchType.Texto){
            categoriaId=null;
        }else if(searchType==SearchType.Categoria){
            texto=null;
        }
    }
}
