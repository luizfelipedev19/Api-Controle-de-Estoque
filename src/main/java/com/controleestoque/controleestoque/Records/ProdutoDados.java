package com.controleestoque.controleestoque.Records;

import com.controleestoque.controleestoque.Enums.Tipo;

public record ProdutoDados(Integer codigoProduto, String nomeProduto, String descricao, Integer quantidade, Float preco_unitario, Tipo tipoDeEntrada) {
}
