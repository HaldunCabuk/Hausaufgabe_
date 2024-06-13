package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTests {

    Response response;


    @Test
    public void getPrettyPreek(){


        response = given()
                .contentType(ContentType.JSON)
                .get("https://reqres.in/api/users")
                .prettyPeek();
    }

    @Test
    public void test(){
        response = given()
                .contentType(ContentType.JSON)
                .get("https://reqres.in/api/users");


    }

    @Test
    public void jsonPath_AllBodyToMap(){
        response = given()
                .contentType(ContentType.JSON)
                .get("https://reqres.in/api/users");

        response.then().body("page",equalTo(1));
       Map<String,?>  map = response.jsonPath().get("");

            map.forEach((k,v) -> System.out.println(k +" : " + v));

    }

    @Test
    public void jsonPath_ObjectToMap() {

        response = given()
                .contentType(ContentType.JSON)
                .get("https://reqres.in/api/users");

        List<String> emails = response.jsonPath().getList("data.findAll{it.id<3}.email");
        System.out.println("emails = " + emails);

        System.out.println("--------------------------------------------------------------------");

        String email = response.jsonPath().get("data.find{it.id==3}.avatar");
        System.out.println("email = " + email);

        System.out.println("***************************************************");

        Map<String, ?> map = response.jsonPath().getMap("data.find{it.first_name=='Charles'}");
        map.forEach((k,v) -> System.out.println(k + " : " + v ));

    }

    @Test
    public void jsonPath_ObjectsToMaps() {

        response = given()
                .contentType(ContentType.JSON)
                .get("https://reqres.in/api/users")

        ;
        List<Map<String, ?>> maps = response.jsonPath().get("data");
        for (Map<String, ?> map : maps) {
            map.forEach((k, v) -> System.out.println(k + " : " + v));
            System.out.println("//////////////////////////////////////////");


        }

    }

    @Test
    public void jsonpath_ObjectsToMaps() {
        response = get("https://jsonplaceholder.typicode.com/comments");
        response.then().body("[0].id", equalTo(1));
        ArrayList<Map<String, ?>> maps = response.jsonPath().get("");
        System.out.println("maps.size() = " + maps.size());

    }


}
