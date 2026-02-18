package com.controleestoque.controleestoque.DTOResponse;

import com.controleestoque.controleestoque.Enums.Tipo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTOMovimentacaoEstoqueResponse {


    private Long id_movimentacao;
    private String nomeProdutoMovimentado;
    private Tipo tipo;
    private Integer quantidade;
    private String Obersacao;
}
