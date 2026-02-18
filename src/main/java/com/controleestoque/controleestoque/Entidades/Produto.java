package com.controleestoque.controleestoque.Entidades;

import com.controleestoque.controleestoque.Enums.Tipo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "produto",

    uniqueConstraints = {
        @UniqueConstraint(name = "uk_produto_empresa_sku", columnNames = {"empresa_id, sku"})
    },

    indexes = {
        @Index(
                name = "idx_produto_empresa",
                columnList = "empresa_id"
        ),

            @Index(
                    name = "idx_produto_alerta",
                    columnList = "empresa_id, quantidade_min, quantidade_max"
            )
    }

)

@Check(constraints = "quantidade_max >= 0 AND quantidade_min >= 0")
@Getter
@Setter
public class Produto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "produto_id")
    private Long id_produto;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;


    @Column(name = "codigo_do_produto", nullable = false)
    private Integer codigoProduto;


    @Column(name = "nome_do_produto", nullable = false, length = 150)
    private String nomeProduto;

    @Column(name = "descricao_do_produto", columnDefinition = "text")
    private String descricao;

    @Column(name = "tipo_de_entrada", nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipoDeEntrada;

    @Column(name = "quantidade_do_produto", nullable = false)
    private Integer quantidade = 0;

    @Column(name = "preco_unitario", nullable = false)
    private Float preco_unitario;


    @CreationTimestamp
    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime create_at;

    @UpdateTimestamp
    @Column(name = "update_at", nullable = false)
    private LocalDateTime update_at;



    /**
     * create table produto(
     *     id bigserial primary key,
     *     empresa_id bigint not null references empresa(id),
     *     nome varchar(150) not null,
     *     sku varchar(80) not null,
     *     descricao text,
     *     quantidade_atual integer not null default 0,
     *     quantidade_min integer not null default 0,
     *     ativo boolean not null default true,
     *     create_at timestamp not null default now(),
     *     update_at timestamp not null default now(),
     *     constraint uk_produto_empresa_sku unique (empresa_id, sku),
     *     constraint ck_produto_qtd check ( quantidade_atual >= 0 ),
     *     constraint ck_produto_min check ( quantidade_min >= 0 )
     * );
     *
     * create index idx_produto_empresa on produto(empresa_id);
     * create index idx_produto_alerta on produto(empresa_id, quantidade_atual, quantidade_min);
     */
}
