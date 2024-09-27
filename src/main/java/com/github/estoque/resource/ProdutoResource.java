package com.github.estoque.resource;

import com.github.estoque.dto.ProdutoDTO;
import com.github.estoque.exception.ConstraintViolationExceptionMapper;
import com.github.estoque.exception.DataCadastroAlteracaoException;
import com.github.estoque.service.ProdutoService;
import io.quarkus.arc.ArcUndeclaredThrowableException;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;
import java.util.Map;

@Path("/estoque")
@Authenticated
public class ProdutoResource {
    @Inject
    ProdutoService service;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin", "user"})
    public Uni<RestResponse<List<ProdutoDTO>>> exibirProdutos() {
        return service.listAll()
                .onItem()
                .transform(RestResponse::ok)
                .onFailure().recoverWithItem(throwable ->
                        RestResponse.status(500, "Erro ao buscar produtos"));
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<RestResponse<ProdutoDTO>> exibirProdutoPorId(@PathParam("id") Long id) {
        return service.findById(id)
                .onItem()
                .transform(RestResponse::ok)
                .onFailure().recoverWithItem(throwable ->
                        RestResponse.status(404, "O produto com id " + id + " não foi encontrado"));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response cadastrarProduto(@Valid ProdutoDTO produtoDTO) {
        try {
            service.save(produtoDTO);
            return Response.status(Response.Status.CREATED).build();
        } catch (DataCadastroAlteracaoException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();
        } catch (ConstraintViolationException exception) {
            Map<String, String> errorDetails = ConstraintViolationExceptionMapper.mapErrorMessage(exception);
            return Response.status(422)
                    .entity(errorDetails)
                    .build();
        } catch (UnexpectedTypeException exception) {
            Map<String, String> message = Map.of("message", "Preencha corretamente todos os campos");
            return Response.status(422)
                    .entity(message)
                    .build();
        } catch (Exception exception) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response atualizarProduto(@PathParam("id") Long id, @Valid ProdutoDTO produto) {
        try {
            service.update(id, produto);
            return Response.noContent().build();
        } catch (DataCadastroAlteracaoException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();
        } catch (ArcUndeclaredThrowableException exception) {
            Map<String, String> errorDetails = ConstraintViolationExceptionMapper.mapErrorMessage(exception);
            return Response.status(422)
                    .entity(errorDetails)
                    .build();
        } catch (Exception exception) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/vencimento")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin", "user"})
    public Response produtosPertoDoVencimento() {
        List<ProdutoDTO> produtos = service.vencimentoChegando();
        if (produtos.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Nenhum produto com vencimento próximo").build();
        }
        return Response.ok().entity(produtos).build();
    }

    @GET
    @Path("/vencidos")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin", "user"})
    public Response produtosVencidos() {
        List<ProdutoDTO> produtos = service.produtosVencidos();
        if (produtos.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Nenhum produto vencido em estoque").build();
        }
        return Response.ok().entity(produtos).build();
    }
}
