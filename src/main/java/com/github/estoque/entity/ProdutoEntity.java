package com.github.estoque.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "produto")
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Insira o nome do produto")
    @NotBlank(message = "Insira o nome do produto")
    @Column(nullable = false)
    private String nome;

    @DecimalMin(value = "0.01", message = "O preço do produto precisa ser maior que 0")
    @NotNull(message = "Insira o preço do produto")
    @Column(nullable = false)
    private BigDecimal preco;

    @NotNull(message = "Insira a quantidade do produto")
    @Column(nullable = false)
    @DecimalMin(value = "0.01", message = "A quantidade de produtos precisa ser maior que 0")
    private Integer quantidade;

    @Column(name = "data_validade")
    private LocalDate dataValidade;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @PrePersist
    public void prePersist() {
        if (this.dataCadastro == null) {
            this.dataCadastro = LocalDate.now();
        }
    }
}
