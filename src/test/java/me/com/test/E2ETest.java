package me.com.test;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class E2ETest {
	
	@SuppressWarnings("unchecked")
	@Test(description = "End to End Testing")
	public void e2eTest() {
		String keyId = "9b5f49ab-eea9-45f4-9d66-bcf56a531b85";
		String userName = "TOOLSQA-Test";
		String password = "Test@@123";
		String baseUri = "http://bookstore.toolsqa.com";
		
		RestAssured.baseURI = baseUri;
		RequestSpecification httpRequest = RestAssured.given();
		
		JSONObject parameter = new JSONObject();
		parameter.put("userName", userName);
		parameter.put("password", password);
		
		System.out.println(parameter.toJSONString());
		// Step - 1
		// Test will start from generating token for authorization
		httpRequest.header("Content-Type", "application/json");
		Response response = httpRequest.body(parameter.toJSONString())
				.post("/Account/v1/GenerateToken");
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		String jsonString = response.asString();
		System.out.println(jsonString);
		Assert.assertTrue(jsonString.contains("token"));
		Assert.assertTrue(jsonString.contains("expires"));
		Assert.assertTrue(jsonString.contains("status"));
		Assert.assertTrue(jsonString.contains("result"));
		Assert.assertEquals(JsonPath.from(jsonString).get("status"), "Success");
		
		// Token used later
		String token = JsonPath.from(jsonString).get("token");
		System.out.println("Token Generated: "+token);
		System.out.println("=====================================");
		// Step - 2
		// Get Books - No Auth is required for this.
		response = httpRequest.get("/BookStore/v1/Books");
		Assert.assertEquals(response.getStatusCode(), 200);
		jsonString = response.asString();
		System.out.println(jsonString);
		List<Map<String, String>> books = JsonPath.from(jsonString).get("books");
		Assert.assertTrue(books.size()>0);
		System.out.println("Books: "+books);
		System.out.println("Total Books: "+books.size());
		
		String bookId = books.get(0).get("isbn");
		
		// Step - 3
		// Add a book with - Auth
		httpRequest.header("Authorization", "Bearer " + token)
        .header("Content-Type", "application/json");
		
		response = httpRequest.body("{ \"userId\": \"" + keyId + "\", " +
                "\"collectionOfIsbns\": [ { \"isbn\": \"" + bookId + "\" } ]}")
                .post("/BookStore/v1/Books");
		
		Assert.assertEquals(response.getStatusCode(), 201);
		
		// Step - 4
		// Delete a book - with Auth
		httpRequest.header("Authorization", "Bearer " + token)
        .header("Content-Type", "application/json");
		
		response = httpRequest.body("{ \"isbn\": \"" + bookId + "\", \"userId\": \"" + keyId + "\"}")
                .delete("/BookStore/v1/Book");
		
		Assert.assertEquals(response.getStatusCode(), 204);
		
		// Step - 5
		// Get User
		httpRequest.header("Authorization", "Bearer " + token)
        .header("Content-Type", "application/json");
		
		response = httpRequest.get("/Account/v1/User/" + keyId);
        Assert.assertEquals(200, response.getStatusCode());
		
		jsonString = response.asString();
		System.out.println(jsonString);
		
		String responseUserName = JsonPath.from(jsonString).get("userName");
		Assert.assertEquals(responseUserName, userName);
	}
}
