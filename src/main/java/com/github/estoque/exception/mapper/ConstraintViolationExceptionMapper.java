package com.github.estoque.exception.mapper;

import com.github.estoque.exception.mapper.interpolate.ConstraintViolationExceptionInterpolateMessage;
import com.github.estoque.exception.message.ModelMessage;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;
import java.util.Objects;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private final int status = 422;

    public Response toResponse(ConstraintViolationException exception) {
        Map<String, String> mapErrorMessage = ConstraintViolationExceptionInterpolateMessage.mapErrorMessage(exception);

        ModelMessage errorMessage = new ModelMessage("Ops... Parece algo está faltando!",
                exception.getMessage().length() > 100
                        ? Objects.requireNonNull(mapErrorMessage).get("message")
                        : exception.getMessage(),
                status);

        return Response
                .status(status)
                .entity(errorMessage)
                .build();
    }
}
