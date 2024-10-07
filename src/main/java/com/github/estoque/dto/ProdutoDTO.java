package com.github.estoque.dto;

import jakarta.json.bind.annotation.JsonbDateFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {
    private Long id;
    private String nome;
    private BigDecimal preco;
    private Integer quantidade;
    @JsonbDateFormat(value = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataValidade;
    @JsonbDateFormat(value = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataCadastro;
}
