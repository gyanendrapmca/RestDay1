package me.com.test;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class RegistrationTesting {
	
	@SuppressWarnings("unchecked")
	@Test(description = "POST Request using Rest Assured")
	public void registrationPost() {
		RestAssured.baseURI = "http://restapi.demoqa.com/customer";
		RequestSpecification httpRequest = RestAssured.given();
		
		JSONObject requestParams = new JSONObject();
		requestParams.put("FirstName", "Rahul3");
		requestParams.put("LastName", "Roy3");
		requestParams.put("UserName", "rr13");
		requestParams.put("Password", "123455");
		requestParams.put("Email", "rr13@yopmail.com");
		
		System.out.println("Details In JSON String");
		System.out.println(requestParams.toJSONString());
		System.out.println("=============================");
		httpRequest.body(requestParams.toJSONString());
		Response response = httpRequest.post("/register");
		Assert.assertEquals(response.getStatusCode(), 201, "Successfully Registered!");
		String successCode = response.jsonPath().get("SuccessCode");
		System.out.println("Success Code: "+successCode);
		Assert.assertEquals(successCode, "OPERATION_SUCCESS", "Get Operation Successful Message!");
	}

}
