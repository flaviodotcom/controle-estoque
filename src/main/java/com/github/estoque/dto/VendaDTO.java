package com.github.estoque.dto;

import com.github.estoque.entity.ProdutoEntity;
import jakarta.json.bind.annotation.JsonbDateFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaDTO {
    private Long id;
    private ProdutoEntity produto;
    private Integer quantidade;
    @JsonbDateFormat(value = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataVenda;
    private BigDecimal total;
}
