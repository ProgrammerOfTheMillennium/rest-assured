package org.example;

/**
 * Created by Bulat Latypov.
 *

 I use Lombok library for Getters, Setters. So install the plugin into Intellij IDEA:
         1. In the Settings/Preferences dialog (Ctrl+Alt+S), select Plugins
         2. Write Lombok
         3. Install the plugin

 If you have 404, 505 HTTP status codes then restart the Docker:
     1. sudo docker ps
     2. Copy the <container_id>
     3. sudo docker stop <container_id>
     4. Check your path to docker folder with <pwd> command
     5. sudo docker run -d -p 65535:80 -v /home/<YOUR_PATH_TO_PROJECT>/rest-assured/docker-json-server/db.json:/data/db.json clue/json-server
     6. Run tests
 */

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import org.example.json.Post;
import org.example.json.User;

import java.util.HashMap;
import java.util.Map;


public class AppTest {

    @BeforeTest
    public void setup() {
//        RestAssured.baseURI = "http://localhost";
//        RestAssured.basePath = "/posts/";
//        RestAssured.port = 65535;
    }



    @Test
    public void shouldValidatePostObject() {
        String postsEndpoint = "http://localhost:65535/posts/{id}";

        Response response =
                given().contentType(ContentType.JSON).accept(ContentType.JSON).
                when().
                    pathParams("id", 5).
                    get(postsEndpoint).
                then().
                    assertThat().statusCode(200).
                    extract().response();

        Post pst = response.getBody().jsonPath().getObject("$", Post.class);

        assertNotNull(pst.getTitle());
    }


    @Test
    public void shouldGetUsersObject() {
        String usersEndpoint = "http://localhost:65535/users/{id}";

        Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).
                when().
                    pathParams("id", 7).
                    get(usersEndpoint).
                then().
                    assertThat().statusCode(200).
                    extract().response();


        User usr = response.getBody().jsonPath().getObject("$", User.class);

        assertNotNull(usr.getUsername());
        assertNotNull(usr.getEmail());
        assertNotNull(usr.getPhone());
        assertNotNull(usr.getWebsite() + "\n");

        assertNotNull(usr.getAddress().getStreet());
        assertNotNull(usr.getAddress().getCity());
        assertNotNull(usr.getAddress().getZipcode()+"\n");

        assertNotNull(usr.getAddress().getGeo().getLat());
        assertNotNull(usr.getAddress().getGeo().getLng());
    }

    @Test
    public void shouldPostUsersObject() {
        String usersEndpoint = "http://localhost:65535/users";

        Map<String, Object> postGeo = new HashMap<>();
        postGeo.put("lat", "-43.9509");
        postGeo.put("lng", "-43.9509");

        Map<String, Object> postAddress = new HashMap<>();
        postAddress.put("street", "Prospect");
        postAddress.put("suit", "121/3");
        postAddress.put("city", "Ufa");
        postAddress.put("zipcode", "580089");
        postAddress.put("geo", postGeo);

        Map<String, Object> postCompany = new HashMap<>();
        postCompany.put("name", "WRTech");
        postCompany.put("catchPhrase", "IPTV");
        postCompany.put("bs", "Software");

        Map<String, Object> postUser = new HashMap<>();
        postUser.put("id", 11);
        postUser.put("name", "Bulat Latypov");
        postUser.put("username", "Bulat");
        postUser.put("email", "wrtech@ya.ru");
        postUser.put("phone", "+7 777 777 77 77");
        postUser.put("website", "https://www.website.com");

        postUser.put("address", postAddress);
        postUser.put("company", postCompany);

        Response response =
                given().
                    contentType(ContentType.JSON).accept(ContentType.JSON).
                    body(postUser).
                when().
                    post(usersEndpoint).
                then().
                    assertThat().statusCode(201). // if 505 you already created the User JSON Object  - restart Docker
                    extract().response();

        User usr = response.getBody().jsonPath().getObject("$", User.class);

        assertEquals(postUser.get("username"), usr.getUsername());
        assertEquals(postUser.get("name"), usr.getName());
        assertEquals(postUser.get("email"),usr.getEmail());
        assertEquals(postUser.get("phone"),usr.getPhone());
        assertEquals(postUser.get("website"),usr.getWebsite());
    }

    @Test
    public void shouldPutPostObjectValue() {
        int postId = 1;
        String postEndpoint = "http://localhost:65535/posts/{id}";

        Map<String, Object> putPost = new HashMap<>();
        putPost.put("userId", postId);
        putPost.put("id", postId);
        putPost.put("title", "Exploring REST Assured API.");
        putPost.put("body", "This is a put REST API request.");

        Response response =
                given().
                    contentType(ContentType.JSON).accept(ContentType.JSON).
                    body(putPost).
                when().
                    pathParams("id", postId).
                    put(postEndpoint).
                then().
                    assertThat().statusCode(200).
                    extract().response();

        Post pst = response.getBody().jsonPath().getObject("$", Post.class);

        assertEquals(putPost.get("title"), pst.getTitle());
        assertEquals(putPost.get("body"), pst.getBody());
    }

    @Test
    public void shouldDeletePostObject() {
        int postId = 3;
        String postEndpoint = "http://localhost:65535/posts/{id}";

        Response response =
                given().
                    contentType(ContentType.JSON).accept(ContentType.JSON).
                when().
                    pathParams("id", postId).
                    delete(postEndpoint).
                then().
                    assertThat().statusCode(200). //if 404 you already deleted the Post JSON Object - restart Docker
                    extract().response();

        Post pst = response.getBody().jsonPath().getObject("$", Post.class);

        assertNull(pst.getBody());
        assertNull(pst.getTitle());
    }
}



