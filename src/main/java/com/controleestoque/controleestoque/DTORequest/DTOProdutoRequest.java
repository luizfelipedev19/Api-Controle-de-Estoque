package com.controleestoque.controleestoque.DTORequest;

import com.controleestoque.controleestoque.Enums.Tipo;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTOProdutoRequest {

    @NotNull
    private Integer codigoProduto;

    @NotNull
    @Size(min = 1, max = 150)
    private String nomeProduto;

    private String descricao;

    @NotNull
    private Integer quantidade;

    @NotNull
    private Float precoUnitario;

    @NotNull
    @Enumerated
    private Tipo tipoDeEntrada;

}
