package com.github.estoque.repository;

import com.github.estoque.entity.VendaEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VendaRepository implements PanacheRepository<VendaEntity> {
}
