package com.github.estoque.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    public LocalDateTime dataVenda;

    @Column(name = "total", nullable = false)
    public BigDecimal total;

    @PrePersist
    public void prePersist() {
        if (this.dataVenda == null) {
            this.dataVenda = LocalDateTime.now();
        }
        if (this.total == null) {
            this.total = produto.preco.multiply(BigDecimal.valueOf(quantidade));
        }
    }

}
