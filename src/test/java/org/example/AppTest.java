package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.example.json.Post;
import org.example.json.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/*
Routes

All HTTP methods are supported.
GET 	/posts
GET 	/posts/1
GET 	/posts/1/comments
GET 	/comments?postId=1
GET 	/posts?userId=1
POST 	/posts
PUT 	/posts/1
PATCH 	/posts/1
DELETE 	/posts/1

Note: you can view detailed examples here - https://jsonplaceholder.typicode.com/guide.html.

git show --pretty="" --name-only <commit_hash>

JsonPATH info:
                https://goessner.net/articles/JsonPath/
                https://support.smartbear.com/alertsite/docs/monitors/api/endpoint/jsonpath.html

                https://www.baeldung.com/guide-to-jayway-jsonpath
                https://www.programcreek.com/java-api-examples/index.php?api=io.restassured.path.json.JsonPath


Lombok:
            https://habr.com/ru/post/438870/

Gson:
            https://futurestud.io/tutorials/gson-mapping-of-nested-objects
            https://www.mkyong.com/java/how-do-convert-java-object-to-from-json-format-gson-api/



REST Assured best practice:
                    https://habr.com/ru/post/421005/
                    https://github.com/rest-assured/rest-assured/wiki/usage
                    https://github.com/rest-assured/rest-assured/wiki/Usage#json-using-jsonpath
                    https://stackoverflow.com/questions/21725093/rest-assured-deserialize-response-json-as-listpojo

                    https://github.com/rest-assured/rest-assured/wiki/usage#multi-value-parameter
                    https://github.com/rest-assured/rest-assured/wiki/usage#verifying-response-data

                                given().
                                pathParam("userId", 2).
                                pathParam("id", 6).
                                when().
                                post("/posts/{userId}/{id}").
                                then().

                                given().
                                pathParam("userId", 1).
                                when().
                                post("/reserve/{userId}/{id}", 54).
                                then().
 */


public class AppTest {

    @BeforeMethod
    public void setup() {
//        jsonPath

    }
//    String endpoint = "http://localhost:65535/posts/{id}";
//    http://localhost:65535/db/$/posts








//    String endpoint = "http://localhost:65535/posts/1";
//    String endpoint = "http://localhost:65535/db/$/";

    @Test
    public void shouldAnswerWithTrue() {
        String endpoint = "http://localhost:65535/posts/1";

        Response response = RestAssured.
                given().contentType(ContentType.JSON).accept(ContentType.JSON).
                when().
                get(endpoint).
                andReturn();

        JsonPath jsonPath = new JsonPath(response.body().asString());

        System.out.println(jsonPath.toString() + "\n");
        System.out.println(jsonPath.get("userId").toString());
        System.out.println(jsonPath.get("id").toString());
        System.out.println(jsonPath.get("title").toString());
        System.out.println(jsonPath.get("body").toString());




//                then().
//                assertThat().
//                statusCode(200);

//                jsonPath().
//                getObject("posts/1", JsonPost.class);


        assertTrue( true );
    }



    @Test
    public void shouldValidatePostObject() {
        String basePath = "http://localhost:65535";

        String postsEndpoint = "http://localhost:65535/posts/{id}";

        Response response = RestAssured.
                given().contentType(ContentType.JSON).accept(ContentType.JSON).
                basePath(basePath).
                when().
                pathParams("id", 5).get(postsEndpoint).andReturn();

        System.out.println(response.getBody().jsonPath().get("userId").toString());
        System.out.println(response.getBody().jsonPath().get("id").toString());
        System.out.println(response.getBody().jsonPath().get("title").toString() + "\n\n\n\n");

        System.out.println(response.getBody().jsonPath().getObject("$", Post.class).body);
        System.out.println(response.getBody().jsonPath().getObject("$", Post.class).id);

        assertTrue( true );
    }


    @Test
    public void shouldValidateUsersObject() {
        String postsEndpoint = "http://localhost:65535/users/{id}";

        Response response = RestAssured.
                given().contentType(ContentType.JSON).accept(ContentType.JSON).
                when().
                pathParams("id", 7).get(postsEndpoint).
                andReturn();

        User usr = response.getBody().jsonPath().getObject("$", User.class);

        System.out.println(usr.getUsername());
        System.out.println(usr.getEmail());
        System.out.println(usr.getPhone());
        System.out.println(usr.getWebsite() + "\n");

        System.out.println(usr.getAddress().getStreet());
        System.out.println(usr.getAddress().getCity());
        System.out.println(usr.getAddress().getZipcode());
        System.out.println(usr.getAddress().getSuit()+"\n");

        System.out.println(usr.getAddress().getGeo().getLat());
        System.out.println(usr.getAddress().getGeo().getLng());

        assertTrue( true );
    }
}






