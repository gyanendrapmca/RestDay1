package me.com.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class QueryParametersTesting {

	/*	Query Parameter - Rest API example
	 * 
	 *	Endpoint			https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=2b1fd2d7f77ccf1b7de9b441571b39b8
	 *	
	 *	HTTP Method Type	GET
	 *	
	 *	Query Parameters	Key: | Value:
	 *						q: | London, UK
	 *						appid: | 2b1fd2d7f77ccf1b7de9b441571b39b8
	 *	
	 *	Body				We don’t need to pass the body for GET request in this example.
	 *
	 */
	@Test(description = "Get Request With Query Parameters")
	public void testQueryParameters() {
		RestAssured.baseURI = "https://samples.openweathermap.org/data/2.5";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.queryParam("q", "London, UK")
				.queryParam("appid", "2b1fd2d7f77ccf1b7de9b441571b39b8")
				.get("/weather");

		String jsonString = response.asString();
		System.out.println("JSON String: "+jsonString);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(jsonString.contains("London"), true);
	}

}
