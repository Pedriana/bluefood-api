package com.castro.bluefood.domain.cliente;

import com.castro.bluefood.domain.usuario.Usuario;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true,callSuper = true)
@Entity
@SuppressWarnings("serial")
public class Cliente extends Usuario {

    private String cpf;

    private String cep;


}
