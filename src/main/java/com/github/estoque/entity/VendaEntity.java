package com.github.estoque.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.time.LocalDate;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "venda")
public class VendaEntity extends PanacheEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    public ProdutoEntity produto;

    @Column(nullable = false)
    @DecimalMin(value = "0", message = "A quantidade de produtos não pode ser negativa")
    public Integer quantidade;

    @Column(name = "data_venda", nullable = false)
    public LocalDate dataVenda;

    @PrePersist
    public void prePersist() {
        if (this.dataVenda == null) {
            this.dataVenda = LocalDate.now();
        }
    }
}
