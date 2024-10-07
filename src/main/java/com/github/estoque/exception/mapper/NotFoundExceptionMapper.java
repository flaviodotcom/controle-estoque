package com.github.estoque.exception.mapper;

import com.github.estoque.exception.message.ModelMessage;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    final int status = Response.Status.NOT_FOUND.getStatusCode();

    public Response toResponse(NotFoundException exception) {
        ModelMessage errorMessage = new ModelMessage("Produto não encontrado!",
                exception.getMessage(),
                status);

        return Response
                .status(status)
                .entity(errorMessage)
                .build();
    }
}
