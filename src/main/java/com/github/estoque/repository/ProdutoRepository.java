package com.github.estoque.repository;

import com.github.estoque.entity.ProdutoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<ProdutoEntity> {
}
