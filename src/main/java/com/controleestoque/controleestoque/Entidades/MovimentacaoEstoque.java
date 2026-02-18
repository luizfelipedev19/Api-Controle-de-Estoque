package com.controleestoque.controleestoque.Entidades;

import com.controleestoque.controleestoque.Enums.Tipo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacao_estoque",

indexes = {
        @Index(
                name = "idx_mov_empresa",
                columnList = "empresa_id"
        ),
        @Index(
                name = "idx_mov_produto",
                columnList = "produto_id"
        ),
        @Index(
                name = "idx_mov_usuario",
                columnList = "usuario_id"
        ),
})
@Check(constraints = "quantidade > 0")
@Getter
@Setter
public class MovimentacaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movimentacao_id")
    private Long movimentacao_id;
    @ManyToOne
    @JoinColumn(name = "empresa_id" , nullable = false)
    private Empresa empresa;
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private Tipo tipo;

    @Column(name = "observacao", nullable = false)
    private String observacao;

    @Column(name = "quantidade_do_produto", nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unitario", nullable = false)
    private Float precoUnitario;

    @Column(name = "preco_total", nullable = false)
    private Float precoTotal;


    @CreationTimestamp
    @Column(name = "create_at", updatable = false)
    private LocalDateTime create_at;




    /**
     * create table movimentacao_estoque(
     *     id bigserial primary key,
     *     empresa_id bigint references empresa(id),
     *     produto_id bigint references produto(id),
     *     usuario_id bigint references usuario(id),
     *     tipo varchar(20) not null,
     *     quantidade integer not null,
     *     observacao text,
     *     create_at timestamp default now(),
     *     constraint ck_mov_qtd check ( quantidade > 0 )
     * );
     *
     * create index idx_mov_empresa on movimentacao_estoque(empresa_id);
     * create index idx_mov_produto on movimentacao_estoque(produto_id);
     * create index idx_mov_usuario on movimentacao_estoque(usuario_id);
     */
}
