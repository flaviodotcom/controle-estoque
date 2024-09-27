package com.github.estoque.exception.mapper;

import com.github.estoque.exception.DataCadastroAlteracaoException;
import com.github.estoque.exception.message.ModelMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DataCadastroAlteracaoExceptionMapper implements ExceptionMapper<DataCadastroAlteracaoException> {

    private final int status = Response.Status.BAD_REQUEST.getStatusCode();

    public Response toResponse(DataCadastroAlteracaoException exception) {
        ModelMessage errorMessage = new ModelMessage("Ops... Ocorreu um erro!",
                exception.getMessage(),
                status);

        return Response
                .status(status)
                .entity(errorMessage)
                .build();
    }
}
