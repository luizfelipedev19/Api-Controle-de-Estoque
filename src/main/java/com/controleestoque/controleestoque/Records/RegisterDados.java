package com.controleestoque.controleestoque.Records;

import com.controleestoque.controleestoque.Enums.Role;

public record RegisterDados(String nomeUsuario, String nomeEmpresa, String cnpj, String email, String senha, Role role) {
}
