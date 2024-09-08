package com.github.estoque.service;

import com.github.estoque.dto.ProdutoDTO;
import com.github.estoque.entity.ProdutoEntity;
import com.github.estoque.exceptions.DataCadastroAlteracaoException;
import com.github.estoque.mapper.ProdutoMapper;
import com.github.estoque.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService {

    @Inject
    ProdutoMapper mapper;

    @Inject
    ProdutoRepository repository;


    @Override
    @Transactional
    public void save(ProdutoDTO produto) {
        if (produto.getDataCadastro() != null) {
            throw new DataCadastroAlteracaoException("Não é possível alterar a data de cadastro de um produto");
        }
        ProdutoEntity newProduto = mapper.toEntity(produto);
        repository.persist(newProduto);
    }

    @Override
    public List<ProdutoDTO> listAll() {
        List<ProdutoEntity> produtos = repository.listAll();
        return mapper.toDTO(produtos);
    }

    @Override
    public ProdutoDTO findById(Long id) {
        ProdutoEntity produto = repository.findById(id);
        return mapper.toDTO(produto);
    }

    @Override
    @Transactional
    public void update(Long id, ProdutoDTO produtoDTO) {
        ProdutoEntity produto = repository.findById(id);
        if (produto == null) {
            throw new InputMismatchException("Produto com id " + id + " não encontrado");
        } else if (produtoDTO.getDataCadastro() != null) {
            throw new DataCadastroAlteracaoException("Não é possível alterar a data de cadastro de um produto");
        }
        mapper.toEntity(produtoDTO, produto);
        repository.persist(produto);
    }

    @Override
    public List<ProdutoDTO> vencimentoChegando() {
        List<ProdutoEntity> produtos = repository.listAll();
        return produtos.stream()
                .filter(p -> p.getDataValidade() != null && verificarVencimento(p.getDataValidade(), false))
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProdutoDTO> produtosVencidos() {
        List<ProdutoEntity> produtos = repository.listAll();
        return produtos.stream()
                .filter(p -> p.getDataValidade() != null && verificarVencimento(p.getDataValidade(), true))
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
