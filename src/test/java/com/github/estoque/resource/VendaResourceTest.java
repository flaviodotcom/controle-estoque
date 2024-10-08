package com.github.estoque.resource;

import com.github.estoque.entity.VendaEntity;
import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
@TestHTTPEndpoint(VendaResource.class)
class VendaResourceTest {

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testExibirVendas() {
        given()
                .when()
                .get()
                .then().statusCode(200)
                .body("isEmpty()", is(false));
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testExibirListaDeVendasVazia() {
        PanacheMock.mock(VendaEntity.class);
        Mockito.when(VendaEntity.listAll()).thenReturn(Collections.emptyList());

        given()
                .when()
                .get()
                .then().statusCode(200)
                .body("$.size()", is(0));
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testExibirVendasPorId() {
        given()
                .when()
                .get("/403")
                .then().statusCode(200)
                .body("id", is(403))
                .body("produto", notNullValue())
                .body("quantidade", is(1))
                .body("dataVenda", notNullValue());
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testRegistrarVenda() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(generateVendaJson(2, 203))
                .when().post()
                .then().statusCode(201);
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testNaoRegistrarVendaQuandoOIdDoProdutoNaoEhEncontrado() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(generateVendaJson(1, 888))
                .when().post()
                .then().statusCode(404)
                .body("message", equalTo("Produto não encontrado"))
                .body("status", is(404))
                .body("title", equalTo("Produto não encontrado!"));
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testNaoRegistrarVendaComQuantidadeInvalida() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(generateVendaJson(-1, 204))
                .when().post()
                .then().statusCode(422)
                .body("message", equalTo("A quantidade de produtos não pode ser negativa"))
                .body("status", is(422))
                .body("title", equalTo("Ops... Parece algo está faltando!"));
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testNaoRegistrarVendaQuandoQuantidadeDeProdutosEhMenorQueAQuantidadeDeVendas() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(generateVendaJson(100, 205))
                .when().post()
                .then().statusCode(400);
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testOrdenarVendasComBaseNoValorTotal() {
        List<String> order = Arrays.asList("DESC", "ASC");

        for (String ordenacao : order) {
            List<BigDecimal> totalVendas = given()
                    .when()
                    .queryParam("order", ordenacao)
                    .when().get("/list")
                    .then().statusCode(200)
                    .extract().jsonPath().getList("total", BigDecimal.class);

            if (ordenacao.equals("DESC")) {
                Assertions.assertEquals(1, totalVendas.get(0).compareTo(totalVendas.get(1)));
            } else {
                Assertions.assertEquals(-1, totalVendas.get(0).compareTo(totalVendas.get(1)));
            }
        }
    }

    String generateVendaJson(Integer quantidade, Integer idProduto) {
        return String.format("""
                {
                    "quantidade": %d,
                    "produto": {
                        "id": %d
                    }
                }
                """, quantidade, idProduto);
    }
}
