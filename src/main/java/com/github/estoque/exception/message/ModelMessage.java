package com.github.estoque.exception.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelMessage {
    private String title;
    private String message;
    private int status;
}
