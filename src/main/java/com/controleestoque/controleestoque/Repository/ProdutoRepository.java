package com.controleestoque.controleestoque.Repository;

import com.controleestoque.controleestoque.Entidades.Empresa;
import com.controleestoque.controleestoque.Entidades.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository  extends JpaRepository<Produto, Long> {

    List<Produto> findByEmpresa(Empresa empresa);

}
