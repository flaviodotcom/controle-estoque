package com.github.estoque.service;

import com.github.estoque.dto.ProdutoDTO;
import com.github.estoque.entity.ProdutoEntity;
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
        }
        mapper.toEntity(produtoDTO, produto);
        repository.persist(produto);
    }

    @Override
    public List<ProdutoDTO> vencimentoChegando() {
        List<ProdutoEntity> produtos = repository.listAll();
        return produtos.stream()
                .filter(p -> p.getDataValidade() != null && isProdutoVencendo(p.getDataValidade()))
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Verifica se o produto estará vencido no prazo de sete dias.
     *
     * @param dataValidade Data de validade do produto.
     * @return Se o produto está no prazo de vencimento.
     */
    private boolean isProdutoVencendo(LocalDate dataValidade) {
        if (dataValidade == null) {
            return false;
        }
        LocalDate now = LocalDate.now();
        return !dataValidade.isBefore(now) && dataValidade.isBefore(now.plusDays(7));
    }
}
