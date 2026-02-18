package com.controleestoque.controleestoque.Infra;

import com.controleestoque.controleestoque.Entidades.Usuario;
import com.controleestoque.controleestoque.Repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {




    private TokenService tokenService;

    public SecurityFilter(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @Autowired
    private UsuarioRepository usuarioRepository;







    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = recoverToken(request);

        if (token != null) {
            String subject = tokenService.validarToken(token); // normalmente o email

            if (subject != null) {
                usuarioRepository.findByEmail(subject).ifPresent(usuario -> {
                    var authentication = new UsernamePasswordAuthenticationToken(
                            usuario, // ✅ principal é o Usuario (UserDetails)
                            null,
                            usuario.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });
            }
        }

        filterChain.doFilter(request, response);
    }

    public String recoverToken(HttpServletRequest request){

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;

        String prefix = "Bearer ";

        if(!authHeader.startsWith(prefix)) return null;

        return authHeader.substring(prefix.length()).trim();
    }
}
