package com.github.estoque.service;

import com.github.estoque.dto.ProdutoDTO;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ProdutoService {

    void save(ProdutoDTO produto);

    Uni<List<ProdutoDTO>> listAll();

    Uni<ProdutoDTO> findById(Long id);

    void update(Long id, ProdutoDTO produto);

    List<ProdutoDTO> vencimentoChegando();

    List<ProdutoDTO> produtosVencidos();
}
