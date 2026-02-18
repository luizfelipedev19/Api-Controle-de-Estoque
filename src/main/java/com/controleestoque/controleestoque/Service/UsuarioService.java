package com.controleestoque.controleestoque.Service;

import com.controleestoque.controleestoque.DTORequest.DTOUsuarioRequest;
import com.controleestoque.controleestoque.DTORequest.DTOUsuarioUpdateRequest;
import com.controleestoque.controleestoque.DTOResponse.DTOUsuarioResponse;
import com.controleestoque.controleestoque.Entidades.Empresa;
import com.controleestoque.controleestoque.Entidades.Usuario;
import com.controleestoque.controleestoque.Enums.Role;
import com.controleestoque.controleestoque.Repository.EmpresaRepository;
import com.controleestoque.controleestoque.Repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.EnumSet;
import java.util.List;

import static com.controleestoque.controleestoque.Enums.Role.ADMIN;


@Service
public class UsuarioService  implements UserDetailsService {



    private final UsuarioRepository usuarioRepository;

    private final EmpresaRepository empresaRepository;

    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, EmpresaRepository empresaRepository, PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario toEntity (DTOUsuarioRequest request){
        Usuario usuario = new Usuario();

        usuario.setNome(request.getNomeUsuario());
        usuario.setEmail(request.getEmail());
        usuario.setSenhaHash(passwordEncoder.encode(request.getSenhaHash()));

        return usuario;
    }

    public DTOUsuarioResponse toResponse(Usuario usuario){
        DTOUsuarioResponse response = new DTOUsuarioResponse();

        response.setId_usuario(usuario.getId_usuario());
        response.setId_empresa(usuario.getEmpresa().getEmpresa_id());
        response.setNomeUsuario(usuario.getNome());
        response.setEmail(usuario.getEmail());

        return response;
    }

    public DTOUsuarioResponse criarUsuario(String emailAdmin, DTOUsuarioRequest request){
        Usuario adminLogado = usuarioRepository.findByEmail(emailAdmin).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (adminLogado.getRole() != Role.ADMIN){
            throw new  ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Role roleRequest = request.getRole();

        if (roleRequest == null){
            roleRequest = Role.OPERADOR;
        }

        if (!EnumSet.of(Role.ADMIN, Role.OPERADOR).contains(roleRequest)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Role inválida"
            );
        }


        Usuario novoUsuario = toEntity(request);
        novoUsuario.setRole(roleRequest);
        novoUsuario.setEmpresa(adminLogado.getEmpresa());
        Usuario salva = usuarioRepository.save(novoUsuario);

        return toResponse(salva);
    }

    public void deletarUsuario( String emailAdmin, Long id) {
        Usuario adminLogado = usuarioRepository.findByEmail(emailAdmin).orElseThrow(() -> new RuntimeException("Usuário não localizado"));


        if (adminLogado.getRole() != ADMIN){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Somente ADMIN pode excluir usuários");

        }

        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não localizado no banco"));

        if (!usuario.getEmpresa().equals(adminLogado.getEmpresa())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        if (usuario.getId_usuario().equals(adminLogado.getId_usuario())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Admin não pode excluir o próprio usuário");
        }

        usuarioRepository.delete(usuario);
    }

    public List<DTOUsuarioResponse> getUsuarios(){
        return usuarioRepository.findAll().stream().map(this::toResponse).toList();
    }

    public DTOUsuarioResponse atualizarUsuario(String emailAdmin, DTOUsuarioUpdateRequest updateRequest, Long id)
    {
        Usuario adminLogado = usuarioRepository.findByEmail(emailAdmin).orElseThrow(() -> new RuntimeException("Usuário não localizado"));
        if (adminLogado.getRole() != ADMIN){
        throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "Somente ADMIN pode atualizar usuários");
        }


        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado no banco"));
        Role roleUpdateRequest = updateRequest.getRole();
        if (!usuario.getEmpresa().equals(adminLogado.getEmpresa())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        if (updateRequest.getNomeUsuario() != null){
            usuario.setNome(updateRequest.getNomeUsuario());
        }
        if (updateRequest.getEmail() != null){
            usuario.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getSenhaHash() != null) {
            usuario.setSenhaHash(passwordEncoder.encode(updateRequest.getSenhaHash()));
        }
        if (roleUpdateRequest != null){
            if (!EnumSet.of(Role.ADMIN, Role.OPERADOR).contains(roleUpdateRequest)){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Role inválida"
                );
            }
            usuario.setRole(roleUpdateRequest);
        }



        usuarioRepository.save(usuario);

        return toResponse(usuario);
    }





    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
                "Usuário não encontrado com e-mail: " + email
        ));
    }

}
