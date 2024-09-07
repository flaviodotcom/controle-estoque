package com.github.estoque.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@Entity
@Table(name = "produto")
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private BigDecimal preco;

    private Integer quantidade;

    @Column(name = "data_validade")
    private LocalDate dataValidade;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;
}
