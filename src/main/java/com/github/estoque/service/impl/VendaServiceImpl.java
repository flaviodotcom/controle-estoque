package com.github.estoque.service.impl;

import com.github.estoque.dto.VendaDTO;
import com.github.estoque.entity.ProdutoEntity;
import com.github.estoque.entity.VendaEntity;
import com.github.estoque.mapper.VendaMapper;
import com.github.estoque.repository.ProdutoRepository;
import com.github.estoque.repository.VendaRepository;
import com.github.estoque.service.VendaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class VendaServiceImpl implements VendaService {

    @Inject
    VendaMapper mapper;

    @Inject
    VendaRepository repository;

    @Inject
    ProdutoRepository produtoRepository;


    @Override
    public List<VendaDTO> listAll() {
        List<VendaEntity> vendas = repository.listAll();
        return mapper.toDTO(vendas);
    }

    @Override
    public VendaDTO findById(Long id) {
        VendaEntity venda = repository.findById(id);
        return mapper.toDTO(venda);
    }

    /**
     * Registra a venda no banco de dados.
     * Atualiza o estoque quando o produto é vendido.
     *
     * @param produto Produto a ser vendido.
     */
    @Override
    @Transactional
    public void save(VendaDTO produto) {
        VendaEntity venda = mapper.toEntity(produto);
        ProdutoEntity produtoEmEstoque = produtoRepository.findById(venda.getProduto().getId());

        if (produtoEmEstoque == null || produtoEmEstoque.getQuantidade() < venda.getQuantidade()) {
            throw new IllegalArgumentException("A quantidade não está disponível no estoque");
        }
        venda.setProduto(produtoEmEstoque);
        repository.persist(venda);

        if (produtoEmEstoque.getQuantidade().equals(venda.getQuantidade())) {
            produtoEmEstoque.setQuantidade(0);
            produtoEmEstoque.setAtivo(Boolean.FALSE);
        } else {
            produtoEmEstoque.setQuantidade(produtoEmEstoque.getQuantidade() - venda.getQuantidade());
        }
        produtoRepository.persist(produtoEmEstoque);
    }

}
