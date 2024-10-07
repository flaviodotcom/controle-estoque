package com.github.estoque.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
class VendaResourceTest {

    private final String jsonBody = """
            {
                "quantidade": 1,
                "produto": {
                    "id": 203
                }
            }
            """;

    @Test
    void testExibirVendas() {
        given()
        .when()
                .get("/venda")
                .then().statusCode(200)
                .body("isEmpty()", is(false));
    }

    @Test
    void testExibirVendasPorId() {
        given()
        .when()
                .get("/venda/403")
                .then().statusCode(200)
                .body("id", is(403))
                .body("produto", notNullValue())
                .body("quantidade", is(1))
                .body("dataVenda", notNullValue());
    }

    @Test
    void testRegistrarVenda() {
        given()
        .when()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when().post("/venda")
                .then().statusCode(201);
    }

}
