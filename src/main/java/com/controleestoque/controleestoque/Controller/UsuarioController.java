package com.controleestoque.controleestoque.Controller;

import com.controleestoque.controleestoque.DTORequest.DTOUsuarioRequest;
import com.controleestoque.controleestoque.DTORequest.DTOUsuarioUpdateRequest;
import com.controleestoque.controleestoque.DTOResponse.DTOUsuarioResponse;
import com.controleestoque.controleestoque.Entidades.Usuario;
import com.controleestoque.controleestoque.Repository.UsuarioRepository;
import com.controleestoque.controleestoque.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;


    public UsuarioController(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public DTOUsuarioResponse criarUsuarios(@RequestBody @Valid DTOUsuarioRequest request,
                                            @AuthenticationPrincipal UserDetails userDetails){
        return usuarioService.criarUsuario(userDetails.getUsername(), request);
    }

    @GetMapping
    public List<DTOUsuarioResponse> getUsuarios(){
        return usuarioService.getUsuarios();

    }


    @PatchMapping("/{id}")
    public DTOUsuarioResponse atualizarUsuario(
            @PathVariable Long id,
            @RequestBody @Valid  DTOUsuarioUpdateRequest request,
                                                    @AuthenticationPrincipal UserDetails userDetails){
        return usuarioService.atualizarUsuario(userDetails.getUsername(), request, id);
    }

    @DeleteMapping("/{id}")
    public void deletarUsuarios(@PathVariable Long id,
                                @AuthenticationPrincipal UserDetails userDetails){
        usuarioService.deletarUsuario(userDetails.getUsername(), id);
    }
}
