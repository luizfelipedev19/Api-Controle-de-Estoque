package com.controleestoque.controleestoque.DTORequest;

import com.controleestoque.controleestoque.Entidades.Empresa;
import com.controleestoque.controleestoque.Enums.Role;
import com.controleestoque.controleestoque.Enums.Tipo;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

@Getter
@Setter
public class DTOUsuarioRequest {
    @NotNull
    @Size(min = 3, max = 50)
    private String nomeUsuario;

    @NotNull
    @Size(min = 6, max = 100)
    @Email
    private String email;

    @NotNull
    @Size(min = 8, max = 12)
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&._-]).+$",
            message = "A senha deve conter letra, número e caractere especial"
    )
    private String senhaHash;

    @NotNull
    private Role role;

}
