package com.controleestoque.controleestoque.DTOResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTOUsuarioResponse {

    private Long id_usuario;
    private Long id_empresa;
    private String nomeUsuario;
    private String email;
}
