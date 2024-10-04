package com.github.estoque.service.impl;

import com.github.estoque.dto.ProdutoDTO;
import com.github.estoque.entity.ProdutoEntity;
import com.github.estoque.exception.DataCadastroAlteracaoException;
import com.github.estoque.mapper.ProdutoMapper;
import com.github.estoque.service.ProdutoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.estoque.entity.ProdutoEntity.findBy;

@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService {

    @Inject
    ProdutoMapper mapper;


    @Override
    @Transactional
    public void save(ProdutoDTO produto) {
        if (produto.getDataCadastro() != null) {
            throw new DataCadastroAlteracaoException("Não é possível alterar a data de cadastro de um produto");
        }
        ProdutoEntity newProduto = mapper.toEntity(produto);
        newProduto.persist();
    }

    @Override
    public List<ProdutoDTO> listAll() {
        List<ProdutoEntity> produtos = ProdutoEntity.findAll().list();
        return mapper.toDTO(produtos);
    }

    @Override
    public ProdutoDTO findById(Long id) {
        ProdutoEntity produto = findBy(id);
        return mapper.toDTO(produto);
    }

    @Override
    @Transactional
    public void update(Long id, ProdutoDTO produtoDTO) {
        ProdutoEntity produto = findBy(id);
        if (produtoDTO.getDataCadastro() != null) {
            throw new DataCadastroAlteracaoException("Não é possível alterar a data de cadastro de um produto");
        }
        ProdutoEntity produtoAtualizado = mapper.toEntity(produtoDTO, produto);
        produtoAtualizado.persist();
    }

    @Override
    public List<ProdutoDTO> vencimentoChegando() {
        List<ProdutoEntity> produtos = ProdutoEntity.listAll();
        return produtos.stream()
                .filter(p -> p.dataValidade != null && verificarVencimento(p.dataValidade, false))
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProdutoDTO> produtosVencidos() {
        List<ProdutoEntity> produtos = ProdutoEntity.listAll();
        return produtos.stream()
                .filter(p -> p.dataValidade != null && verificarVencimento(p.dataValidade, true))
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Verifica se o produto está vencido ou se está prestes a vencer em sete dias.
     *
     * @param dataValidade       Data de validade do produto.
     * @param verificarSeVencido Se true, verifica se o produto já está vencido;
     *                           caso contrário, verifica se vencerá nos próximos sete dias.
     * @return true se a condição for atendida, false caso contrário.
     */
    private boolean verificarVencimento(LocalDate dataValidade, boolean verificarSeVencido) {
        LocalDate now = LocalDate.now();
        if (verificarSeVencido) {
            return dataValidade.isBefore(now);
        }
        return !dataValidade.isBefore(now) && dataValidade.isBefore(now.plusDays(7));
    }
}
