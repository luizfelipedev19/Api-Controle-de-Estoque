package com.controleestoque.controleestoque.DTOResponse;

import com.controleestoque.controleestoque.Enums.Tipo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTOProdutoResponse {

    private Long id_produto;
    private String nomeProduto;
    private Integer codigoProduto;
    private Integer quantidade;
    private String descricao;
    private Tipo tipoDeEntrada;
    private Float preco_unitario;
}
