package com.controleestoque.controleestoque.Controller;

import com.controleestoque.controleestoque.DTORequest.DTOProdutoRequest;
import com.controleestoque.controleestoque.DTOResponse.DTOProdutoResponse;
import com.controleestoque.controleestoque.DTOResponse.DTOUsuarioResponse;
import com.controleestoque.controleestoque.Entidades.Empresa;
import com.controleestoque.controleestoque.Entidades.Produto;
import com.controleestoque.controleestoque.Entidades.Usuario;
import com.controleestoque.controleestoque.Repository.ProdutoRepository;
import com.controleestoque.controleestoque.Service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {

        this.produtoService = produtoService;
    }


    @PostMapping
    public DTOProdutoResponse criarProduto(@RequestBody @Valid DTOProdutoRequest request,
                                           @AuthenticationPrincipal UserDetails userDetails
    ){
        return produtoService.criarProduto(
                request, produtoService.buscarEmpresa(userDetails.getUsername()));
    }

    @GetMapping
    public List<DTOProdutoResponse> listarProdutos(@AuthenticationPrincipal Usuario usuario){
        return produtoService.getProduto(usuario.getEmpresa());
    }
}
