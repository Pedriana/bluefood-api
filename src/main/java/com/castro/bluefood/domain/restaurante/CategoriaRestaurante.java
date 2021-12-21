package com.castro.bluefood.domain.restaurante;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "categoria_restaurante")
public class CategoriaRestaurante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(max=20)
    private String nome;

    @NotNull
    @Size(max = 50)
    private String imagem;

    //só precisa configurar de um lado (Restaurante)
    @ManyToMany(mappedBy = "categorias")
    private Set<Restaurante> restaurantes=new HashSet<>(0);


}
