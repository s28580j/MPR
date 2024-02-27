package com.pjatk.MPR;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;




public class MyRestAssureControllerTest {
    private static final String URI = "http://localhost:8999";

    @Test
    public void getCatWhenExist(){
        when()
                .get(URI+"/cat/bubi")
                .then()
                .statusCode(200)
                .assertThat()
                .body("name", equalTo("bubi"))
                .body("age", equalTo(1))
                .log()
                .body();

    }

    @Test
    public void getCatWhenNotExist(){
        when()
                .get(URI+"/cat/bub")
                .then()
                .statusCode(400)
                .assertThat()
                .log()
                .body();

    }

    @Test
    public void addCatWhenExist(){
        with().body(new Cat("addsd", 1))
                .contentType("application/json")
                .post(URI+"/cat/add")
                .then()
                .assertThat()
                .body("age", equalTo(1))
                .statusCode(200);

    }

    @Test
    public void addCatWhenNotExist(){
        with().body(new Cat("bubi", 1))
                .contentType("application/json")
                .post(URI+"/cat/add")
                .then()
                .assertThat()
                .statusCode(400);

    }

    @Test
    public void getAllCats(){
        when()
                .get(URI+"/cat/all")
                .then()
                .statusCode(400)
                .assertThat()
                .log()
                .body();

    }

    @Test
    public void deleteCatWhenExist(){
        with()
                .delete(URI+"/cat/delete/bubi")
                .then()
                .assertThat()
                .statusCode(200);

    }

    @Test
    public void deleteUserWhenNotExist(){
        with()
                .delete(URI+"/cat/delete/abc")
                .then()
                .assertThat()
                .statusCode(400);

    }

    @Test
    public void updateCatWhenExist(){
        with()
                .body(new Cat("lala", 4))
                .contentType("application/json")
                .put(URI + "/cat/update/lala")
                .then()
                .statusCode(200);
    }

    @Test
    public void updateCatWhenIsNotExist(){
        with()
                .body(new Cat("fdsfds", 4))
                .contentType("application/json")
                .put(URI + "/cat/update/fdsfds")
                .then()
                .statusCode(400);
    }


}