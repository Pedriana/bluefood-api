package com.castro.bluefood.domain.usuario;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

//toda entidade JPA (Cliente) tem que implementar Serializable
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuppressWarnings("serial")
@MappedSuperclass
public class Usuario implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O nome não pode ser vazio")
    @Size(max=80,message = "O nome é muito grande")
    private String nome;

    @NotBlank(message = "O email não pode ser vazio")
    @Size(max=60,message = "O email é muito grande")
    @Email(message = "O email é inválido")
    private String email;

    @NotBlank(message = "A senha não pode ser vazio")
    @Size(max=80,message = "A senha é muito grande")
    private String senha;


    @NotBlank(message = "O telefone não pode ser vazio")
    @Pattern(regexp = "[0-9]{10,11}",message = "O telefone possui formato inválido")
    @Column(length = 11,nullable = false)
    private String telefone;
}
