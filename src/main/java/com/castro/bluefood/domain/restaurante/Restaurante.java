package com.castro.bluefood.domain.restaurante;

import com.castro.bluefood.domain.usuario.Usuario;
import com.castro.bluefood.infrasctructure.web.validator.UploadConstraint;
import com.castro.bluefood.util.FileType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true,callSuper = true)
@Entity
@Table(name = "restaurante")
public class Restaurante extends Usuario {

    @NotBlank(message = "O CNPJ não pode ser vazio") //usado com string
    @Pattern(regexp = "[0-9]{14}",message = "O CNPJ possui formato inválido")
    @Column(length = 14,nullable = false)
    private String cnpj;

    @Size(max = 80)
    private String logotipo;

    //não iremos persistir em tabela (fica só em memória): transiente
//    @UploadConstraint(acceptedTypes = {FileType.PNG,FileType.JPG})
    @UploadConstraint(acceptedTypes = FileType.JPG,message = "O arquivo não é válido")
    private transient MultipartFile logotipoFile;

    @NotNull(message = "A taxa de entrega não pode ser vazia.")
    @Min(0)
    @Max(99)
    private BigDecimal taxaEntrega;

    @NotNull(message = "O tempo de entrega não pode ser vazio.")
    @Min(0)
    @Max(120)
    private Integer tempoEntregaBase;

    //manyToMany - Restaurantexcategorias
    @ManyToMany
    //tabela extra
    @JoinTable(
            name = "restaurante_has_categoria",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")

    )


    @Size(min = 1,message = "O restaurante precisa ter pelo menos uma categoria.")
    @ToString.Exclude
    private Set<CategoriaRestaurante> categorias=new HashSet<>(0);

    @OneToMany(mappedBy = "restaurante")
    private Set<ItemCardapio>itensCardapio = new HashSet<>();

    public void setLogotipoFileName(){
        if(getId()==null){
            throw new IllegalStateException("É necessário primeiro gravar o registro.");
        }
        //getContentType() o navegador fornece
        this.logotipo=String.format("%04d-logo.%s",getId(), FileType.of(logotipoFile.getContentType()).getExtension());
    }
}
