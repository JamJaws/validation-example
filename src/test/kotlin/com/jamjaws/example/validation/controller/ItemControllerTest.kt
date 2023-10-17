package com.jamjaws.example.validation.controller

import io.kotest.assertions.json.shouldEqualJson
import io.restassured.module.webtestclient.RestAssuredWebTestClient.given
import org.junit.jupiter.api.Test


class ItemControllerTest {

    @Test
    fun `GET should return a list of items`() {
        val responseBody = given().standaloneSetup(ItemController())
            .`when`()
            .get("/api/items")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        responseBody shouldEqualJson """[{"id":1,"name":"item-1","description":"item-1"},{"id":1,"name":"item-1","description":"item-1"}]"""
    }

    @Test
    fun `POST item should return 200`() {
        val responseBody = given().standaloneSetup(ItemController())
            .body("""{"name":"new-item","description":"cool new item"}""")
            .contentType("application/json")
            .`when`()
            .post("/api/items")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        responseBody shouldEqualJson """{"id":1,"name":"new-item","description":"cool new item"}"""
    }

    @Test
    fun `POST item with id should return 400`() {
        given().standaloneSetup(ItemController())
            .body("""{"id":1,"name":"new-item","description":"cool new item"}""")
            .contentType("application/json")
            .`when`()
            .post("/api/items")
            .then()
            .statusCode(400)
    }

    @Test
    fun `POST item with blank name should return 400`() {
        given().standaloneSetup(ItemController())
            .body("""{"name":"","description":"cool new item"}""")
            .contentType("application/json")
            .`when`()
            .post("/api/items")
            .then()
            .statusCode(400)
    }

    @Test
    fun `POST invalid item should return 400`() {
        given().standaloneSetup(ItemController())
            .body("""{"invalid":"field"}""")
            .contentType("application/json")
            .`when`()
            .post("/api/items")
            .then()
            .statusCode(400)
    }

    @Test
    fun `PUT item should return 200`() {
        given().standaloneSetup(ItemController())
            .body("""{"id":1,"name":"new-item","description":"cool new item"}""")
            .contentType("application/json")
            .`when`()
            .put("/api/items")
            .then()
            .statusCode(200)
    }

    @Test
    fun `PUT item without id should return 400`() {
        given().standaloneSetup(ItemController())
            .body("""{"name":"new-item","description":"cool new item"}""")
            .contentType("application/json")
            .`when`()
            .put("/api/items")
            .then()
            .statusCode(400)
    }

    @Test
    fun `PUT item with blank name should return 400`() {
        given().standaloneSetup(ItemController())
            .body("""{"id":1,"name":"","description":"cool new item"}""")
            .contentType("application/json")
            .`when`()
            .put("/api/items")
            .then()
            .statusCode(400)
    }

    @Test
    fun `PUT item with negative id should return 400`() {
        given().standaloneSetup(ItemController())
            .body("""{"id":-1,"name":"new-item","description":"cool new item"}""")
            .contentType("application/json")
            .`when`()
            .put("/api/items")
            .then()
            .statusCode(400)
    }
}
