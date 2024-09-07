package com.github.estoque.service;

import com.github.estoque.dto.ProdutoDTO;

import java.util.List;

public interface ProdutoService {

    void save(ProdutoDTO produto);

    List<ProdutoDTO> listAll();

    ProdutoDTO findById(Long id);

    void update(Long id, ProdutoDTO produto);

    List<ProdutoDTO> vencimentoChegando();
}
