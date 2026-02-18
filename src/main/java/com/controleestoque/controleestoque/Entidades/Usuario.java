package com.controleestoque.controleestoque.Entidades;

import com.controleestoque.controleestoque.Enums.Role;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario implements UserDetails {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "usuario_id")
        private Long id_usuario;

        @ManyToOne
        @JoinColumn(name = "empresa_id", nullable = false)
        private Empresa empresa;

        @Column(name = "nome_do_usuario", nullable = false, length = 100)
        private String nome;


        @Column(name = "email", unique = true, nullable = false, length = 100)
        private String email;

        @Column(name = "senha_hash", nullable = false, length = 255)
        private String senhaHash;

        @Enumerated(EnumType.STRING)
        @Column(name = "role", nullable = false, length = 20)
        private Role role = Role.ADMIN;

        @Column(name = "ativo", nullable = false)
        private Boolean ativo = true;

        @Timestamp
        @Column(name = "create_at", nullable = false)
        private LocalDateTime create_at = LocalDateTime.now();

        @Timestamp
        @Column( name = "update_at", nullable = false)
        private LocalDateTime update_at = LocalDateTime.now();

        public Usuario(String s, String nome, String email, String senha) {
        }

        public Usuario(Long id_usuario, Empresa empresa, String nome, String email, String senhaHash, Role role, Boolean ativo, LocalDateTime create_at) {
                this.id_usuario = id_usuario;
                this.empresa = empresa;
                this.nome = nome;
                this.email = email;
                this.senhaHash = senhaHash;
                this.role = role;
                this.ativo = ativo;
                this.create_at = create_at;
        }

        public Usuario(Empresa empresa, String nome, String email, String senhaHash) {

                this.empresa = empresa;
                this.nome = nome;
                this.email = email;
                this.senhaHash = senhaHash;
        }

        public Usuario() {

        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
        }

        @Override
        public @Nullable String getPassword () {
                return getSenhaHash();
        }

        @Override
        public String getUsername() {
                return getEmail();
        }

        /**
     *     id bigserial primary key,
     *     empresa_id bigint not null references empresa(id),
     *     nome varchar(100) not null,
     *     email varchar(100) not null,
     *     senha_hash varchar(255) not null,
     *     role varchar (20) not null default 'ADMIN',
     *     ativo boolean not null default true,
     *     create_at timestamp not null default now(),
     */
}
