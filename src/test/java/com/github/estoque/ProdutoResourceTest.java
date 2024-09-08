package com.github.estoque;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

@QuarkusTest
class ProdutoResourceTest {

    @BeforeAll
    static void setup() {
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    void testProdutoEndpoint() {
        given()
                .when().get("/estoque/203")
                .then()
                .statusCode(200)
                .body("id", is(203))
                .body("nome", is("Pão"))
                .body("preco", is(1.00f))
                .body("quantidade", is(1))
                .body("dataValidade", notNullValue());
    }
}