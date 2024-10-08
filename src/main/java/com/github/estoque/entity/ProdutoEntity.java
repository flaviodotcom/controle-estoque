package com.github.estoque.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.NotFoundException;
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
@Table(name = "produto")
public class ProdutoEntity extends PanacheEntity {

    @NotNull(message = "Insira o nome do produto")
    @NotBlank(message = "Insira o nome do produto")
    @Column(nullable = false)
    public String nome;

    @DecimalMin(value = "0.01", message = "O preço do produto precisa ser maior que 0")
    @NotNull(message = "Insira o preço do produto")
    @Column(nullable = false)
    public BigDecimal preco;

    @NotNull(message = "Insira a quantidade do produto")
    @Column(nullable = false)
    @DecimalMin(value = "0", message = "A quantidade de produtos não pode ser negativa")
    public Integer quantidade;

    @Column(name = "data_validade")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    public LocalDateTime dataValidade;

    @Column(name = "data_cadastro")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    public LocalDateTime dataCadastro;

    @PrePersist
    public void prePersist() {
        if (this.dataCadastro == null) {
            this.dataCadastro = LocalDateTime.now();
        }
    }

    public static ProdutoEntity findBy(Long id) {
        return (ProdutoEntity) ProdutoEntity.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
    }
}
