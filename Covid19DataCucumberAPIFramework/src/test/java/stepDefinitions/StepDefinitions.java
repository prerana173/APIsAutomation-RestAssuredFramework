package stepDefinitions;

import java.io.IOException;
import java.util.Map;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.junit.Assert.*;
import resources.*;

public class StepDefinitions extends Utils{
	RequestSpecification req_spec;
	Response response;
	static Map<String, String> map_name;
	JsonPath jp;

	@Given("getCountryDataByCode API is sent with {string} parameters and {string} headers")
	public void sendCountryCodeAsQueryParam(String countrycode, String headertype) throws IOException, InterruptedException {
		Thread.sleep(1000);
		if(headertype.equalsIgnoreCase("validHeader")) 
			map_name=Payload.validHeader();
		else if(headertype.equalsIgnoreCase("invalidHeader"))
			map_name=Payload.invalidHeader();
		req_spec =RestAssured.given().spec(requestSpecification()).headers(map_name)
				.queryParam("code", countrycode);
	}

	@When("User calls {string} with {string} https request")
	public void callEndPointWithHttpMethod(String endpoint, String httpmethod) {
		if(httpmethod.equalsIgnoreCase("GET"))
			response=req_spec.when().get(endpoint);
		else if(httpmethod.equalsIgnoreCase("POST"))
			response=req_spec.when().post(endpoint);
	}

	@Then("API should return status code {int} and status line {string}")
	public void verifyResponseCodeAndStatusLine(int statuscode, String statusline) {
		assertEquals(statuscode,response.getStatusCode());
		assertTrue(response.getStatusLine().contains(statusline));
	}

	@Then("{string} in response body should be {string}")
	public void verifyResponsePayloadContent(String payloadkey, String payloadvalue) {
		String actualPayloadValue=changeToValidString(response,payloadkey);
		assertTrue(payloadvalue.equalsIgnoreCase(actualPayloadValue));    
	}

	@Then("response body should be empty array")
	public void verifyResponsePayloadIsEmpty() {
		assertTrue(response.getBody().asString().equals("[]"));
	}
	
	@Then("response body should return {string} message")
	public void validateErrorMessageInResponseBody(String errormessage) {
		jp=getJsonPath(response);
		String actualMessage=jp.getString("message");
		assertEquals(errormessage,actualMessage);
	}
	
	@Then("value returned for {string} key in response payload should not be less than zero")
	public void verifyCasesCountIsNotLessThanZero(String payloadkey) {
	    jp=getJsonPath(response);
	    assertTrue((Integer.parseInt(jp.getString(payloadkey).replaceAll("[\\[\\]]", "")))>=0);	    
	}

}
