package com.controleestoque.controleestoque.DTORequest;

import com.controleestoque.controleestoque.Enums.Tipo;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTOMovimentacaoEstoqueRequest {


    @NotNull
    @Size(min = 1, max = 150)
    private String nomeProduto;
    @NotNull
    private Integer quantidade;
    @NotNull
    private String Observacao;

    @NotNull
    @Size(min = 1, max = 50)
    @Enumerated
    private Tipo tipo;

    /**
     *
     *  @Id
     *     @GeneratedValue(strategy = GenerationType.IDENTITY)
     *     @Column(name = "id")
     *     private long movimentacao_id;
     *     @ManyToOne
     *     @JoinColumn(name = "empresa_id" , nullable = false)
     *     private Empresa empresa;
     *     @ManyToOne
     *     @JoinColumn(name = "produto_id", nullable = false)
     *     private Produto produto;
     *     @ManyToOne
     *     @JoinColumn(name = "usuario_id", nullable = false)
     *     private Usuario usuario;
     *
     *     @Enumerated(EnumType.STRING)
     *     @Column(name = "tipo", nullable = false, length = 20)
     *     private Tipo tipo;
     *
     *     @Column(name = "quantidade", nullable = false)
     *     private Integer quantidade;
     *
     *     @Column(name = "observacao", columnDefinition = "text")
     *     private String observacao;
     *
     *     @CreationTimestamp
     *     @Column(name = "create_at", updatable = false)
     *     private LocalDateTime create_at;
     */
}
