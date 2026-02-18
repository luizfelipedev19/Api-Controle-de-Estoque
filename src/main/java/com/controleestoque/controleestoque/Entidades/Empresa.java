package com.controleestoque.controleestoque.Entidades;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Table(name = "empresa")
@Getter
@Setter
public class Empresa {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empresa_id;

    @Column(name = "nome_empresa", nullable = false, length = 100)
    private String nomeEmpresa;

    @CNPJ
    @Column(name = "cnpj", nullable = false)
    private String cnpj;
}
