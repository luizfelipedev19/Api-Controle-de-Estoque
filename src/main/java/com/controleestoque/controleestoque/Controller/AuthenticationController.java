package com.controleestoque.controleestoque.Controller;

import com.controleestoque.controleestoque.Enums.Role;
import com.controleestoque.controleestoque.Entidades.*;
import com.controleestoque.controleestoque.Infra.TokenService;
import com.controleestoque.controleestoque.Records.AuthenticationDados;
import com.controleestoque.controleestoque.Records.LoginResponseDTO;
import com.controleestoque.controleestoque.Records.RegisterDados;
import com.controleestoque.controleestoque.Repository.EmpresaRepository;
import com.controleestoque.controleestoque.Repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDados data){

        var usernamePassword =
                new UsernamePasswordAuthenticationToken(
                        data.email(),
                        data.senha()
                );

        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDados dados){

        if(usuarioRepository.findByEmail(dados.email()).isPresent()){
            return ResponseEntity.status(403).build();
        }

        Empresa empresa = new Empresa();
        empresa.setNomeEmpresa(dados.nomeEmpresa());
        empresa.setCnpj(dados.cnpj());
        empresaRepository.save(empresa);

        String encryptedPassword = passwordEncoder.encode(dados.senha());


        Usuario usuario = new Usuario();
                usuario.setNome(dados.nomeUsuario());
                usuario.setEmpresa(empresa);
                usuario.setEmail(dados.email());
                usuario.setSenhaHash(encryptedPassword);
                usuario.setRole(Role.ADMIN);
                usuario.setAtivo(true);


        usuarioRepository.save(usuario);

        return ResponseEntity.ok().build();
    }

}
