package com.controleestoque.controleestoque.Service;

import com.controleestoque.controleestoque.DTORequest.DTOProdutoRequest;
import com.controleestoque.controleestoque.DTOResponse.DTOProdutoResponse;
import com.controleestoque.controleestoque.DTOResponse.DTOUsuarioResponse;
import com.controleestoque.controleestoque.Entidades.Empresa;
import com.controleestoque.controleestoque.Entidades.Produto;
import com.controleestoque.controleestoque.Entidades.Usuario;
import com.controleestoque.controleestoque.Repository.ProdutoRepository;
import com.controleestoque.controleestoque.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    public ProdutoService(ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository){
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
    }


    public Produto toEntity(DTOProdutoRequest request, Empresa empresa){
        Produto produto = new Produto();

        produto.setCodigoProduto(request.getCodigoProduto());
        produto.setEmpresa(empresa);
        produto.setNomeProduto(request.getNomeProduto());
        produto.setDescricao(request.getDescricao());
        produto.setQuantidade(request.getQuantidade());
        produto.setTipoDeEntrada(request.getTipoDeEntrada());
        produto.setPreco_unitario(request.getPrecoUnitario());

        return produto;
    }

    public DTOProdutoResponse toResponse(Produto produto){

        DTOProdutoResponse response = new DTOProdutoResponse();

        response.setCodigoProduto(produto.getCodigoProduto());
        response.setNomeProduto(produto.getNomeProduto());
        response.setQuantidade(produto.getQuantidade());
        response.setTipoDeEntrada(produto.getTipoDeEntrada());
        response.setDescricao(produto.getDescricao());
        response.setPreco_unitario(produto.getPreco_unitario());


        return response;
    }


    public DTOProdutoResponse criarProduto(DTOProdutoRequest produtoRequest, Empresa empresa){
        Produto produto = toEntity(produtoRequest, empresa);

       Produto salva = produtoRepository.save(produto);

       return toResponse(salva);
    }

    public Empresa buscarEmpresa(String email){
        Optional<Usuario> usuario =  usuarioRepository.findByEmail(email);

        if (usuario == null){
            throw new RuntimeException("Usuario não encontrado");
        }

        return usuario.get().getEmpresa();
    }

    public List<DTOProdutoResponse> getProduto(Empresa empresa) {
       return produtoRepository.findByEmpresa(empresa).stream().map(this::toResponse).toList();
    }

    public void deletarProduto(Long id){

         Produto produto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Não foi possivel deletar o produto. Produto não localizado"));

          produtoRepository.delete(produto);
    }


}
