package com.github.estoque.service;

import com.github.estoque.dto.ProdutoDTO;
import com.github.estoque.entity.ProdutoEntity;
import com.github.estoque.mapper.ProdutoMapper;
import com.github.estoque.repository.ProdutoRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.Period;
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
    @Transactional
    public void update(Long id, ProdutoDTO produtoDTO) {
        ProdutoEntity produto = repository.findById(id);
        if (produto == null) {
            throw new InputMismatchException("Produto com id " + id + " não encontrado");
        }
        repository.persist(produto);
    }

    @Override
    public List<ProdutoDTO> vencimentoChegando() {
        PanacheQuery<ProdutoEntity> produtos = repository.findAll();
        return produtos.list().stream()
                .filter(p -> p.getDataValidade() != null && isProdutoVencendo(p.getDataValidade()))
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    private boolean isProdutoVencendo(LocalDate dataValidade) {
        LocalDate now = LocalDate.now();

        if (dataValidade == null) {
            return false;
        }

        Period period = Period.between(now, dataValidade);
        int diff = Math.abs(period.getDays());
        return diff < 7;
    }
}
