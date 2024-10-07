package com.github.estoque.exception.mapper;

import com.github.estoque.exception.message.ModelMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    final int status = Response.Status.BAD_REQUEST.getStatusCode();

    public Response toResponse(IllegalArgumentException exception) {
        ModelMessage errorMessage = new ModelMessage("Parâmetro inválido!",
                exception.getMessage(),
                status);

        return Response
                .status(status)
                .entity(errorMessage)
                .build();
    }
}
