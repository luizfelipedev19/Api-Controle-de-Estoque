package com.controleestoque.controleestoque.DTORequest;

import com.controleestoque.controleestoque.Enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DTOUsuarioUpdateRequest {


    @Size(min = 3, max = 50)
    private String nomeUsuario;

    @Size(min = 6, max = 100)
    @Email
    private String email;

    @Size(min = 8, max = 12)
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\\\d)(?=.*[@$!%*#?&._-]).+$",
            message = "A senha deve conter letra, número e caractere especial"
    )
    private String senhaHash;

    private Role role;
}
