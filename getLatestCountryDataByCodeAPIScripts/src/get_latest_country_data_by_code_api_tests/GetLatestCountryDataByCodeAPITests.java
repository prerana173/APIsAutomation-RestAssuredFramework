package get_latest_country_data_by_code_api_tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import org.testng.Assert;

public class GetLatestCountryDataByCodeAPITests {

	static String countryCode="in"; 
	static String response;

	public static void main(String[] args) throws InterruptedException {

		//verifyHappyPathScenario();
		verifyNonExistingCountryCodeScenario();
		verifyResponsePayloadForBlankMandatoryHeaders();
		verifyResponseByChangingGetMethodToPost();
		//verifyHappyPathScenario() method is commented as it is used in verifyCasesCountIsNeverLessThanZero() to retrieve response 
		//and then do validations on the confirmed, recovered, critical and death counts
		verifyCasesCountIsNeverLessThanZero();
	}

	public static void verifyHappyPathScenario() {
		//Execute getLatestCountryDataByCode API with valid headers 
		//and valid required parameters - where valid country code is sent as 2 chars(String) in code

		RestAssured.baseURI="https://covid-19-data.p.rapidapi.com";
		response=given()
				.header("x-rapidapi-host","covid-19-data.p.rapidapi.com")
				.header("x-rapidapi-key","8741e73cf9mshd0d82a7acf7b2c0p153754jsneb9e2cc9d0c7")
				.queryParam("code", countryCode)
				.when().log().all().get("country/code/")
				.then().log().all().assertThat()
				.statusCode(200)
				.statusLine(containsString("OK"))
				.header("content-type", "application/json")
				.body("code", hasItem(countryCode.toUpperCase()))
				.body("confirmed",is(notNullValue()))
				.body("recovered",is(notNullValue()))
				.body("critical",is(notNullValue()))
				.body("deaths",is(notNullValue()))
				.extract().response().asString();
	}

	public static void verifyNonExistingCountryCodeScenario() {
		//Execute getLatestCountryDataByCode API with valid headers,non-existing  
		//country code which is sent as 3 chars(String) in code parameter and valid optional parameters
		//Data - Non-existing/invalid Country Code: XYZ

		String threeCharCountryCode="xyz";
		RestAssured.baseURI="https://covid-19-data.p.rapidapi.com";
		String res=given()
				.header("x-rapidapi-host","covid-19-data.p.rapidapi.com")
				.header("x-rapidapi-key","8741e73cf9mshd0d82a7acf7b2c0p153754jsneb9e2cc9d0c7")
				.queryParam("code", threeCharCountryCode)
				.when().log().all().get("country/code/")
				.then().log().all().assertThat()
				.statusCode(200)
				.statusLine(containsString("OK"))
				.header("content-type", "application/json")
				.extract().response().asString();
		Assert.assertTrue(res.toString().equals("[]"));
	}

	public static void verifyResponsePayloadForBlankMandatoryHeaders() {
		/*
		 Execute getLatestCountryDataByCode API with blank mandatory headers and valid 
		 country code (using below datasets)
		 Data - 
		 Country Code: in
		 x-rapidapi-host: blank
		 x-rapidapi-key: blank	
		 */
		RestAssured.baseURI="https://covid-19-data.p.rapidapi.com";
		String res=given()
				.header("x-rapidapi-host"," ")
				.header("x-rapidapi-key"," ")
				.queryParam("code", countryCode)
				.when().log().all().get("country/code/")
				.then().log().all().assertThat()
				.statusCode(401)
				.statusLine(containsString("Unauthorized"))
				.body("message", equalTo("Invalid API key. Go to https://docs.rapidapi.com/docs/keys for more info."))
				.header("content-type", "application/json")
				.extract().response().asString();
		Assert.assertFalse(res.toString().equals("[]"));	
	}

	public static void verifyResponseByChangingGetMethodToPost() {
		//Execute getLatestCountryDataByCode API by changing GET Method to PUT/ POST/ DELETE

		RestAssured.baseURI="https://covid-19-data.p.rapidapi.com";
		given()
		.header("x-rapidapi-host","covid-19-data.p.rapidapi.com")
		.header("x-rapidapi-key","8741e73cf9mshd0d82a7acf7b2c0p153754jsneb9e2cc9d0c7")
		.queryParam("code", countryCode)
		.when().log().all().post("country/code/")
		.then().log().all().assertThat()
		.statusCode(404)
		.statusLine(containsString("Not Found"))
		.header("content-type", "application/json")
		.body("message", equalTo("Endpoint/country/code/ does not exist"));
	}

	public static void verifyCasesCountIsNeverLessThanZero() throws InterruptedException {
		//To verify that confirmed, recovered,critical and deaths key values are never less than 0 - Business Scenario
		Thread.sleep(1000);
		verifyHappyPathScenario();
		JsonPath js=new JsonPath(response);
		String confirmedCount=js.getString("confirmed");
		//Below line of code first removes the square bracket displayed around confirmedCount, then parses it into Integer and 
		//then verifies whether confirmedCount is >=0
		Assert.assertTrue((Integer.parseInt(confirmedCount.replaceAll("[\\[\\]]", "")))>=0);
		String recoveredCount=js.getString("recovered");
		Assert.assertTrue((Integer.parseInt(recoveredCount.replaceAll("[\\[\\]]", "")))>=0);
		String criticalCount=js.getString("critical");
		Assert.assertTrue((Integer.parseInt(criticalCount.replaceAll("[\\[\\]]", "")))>=0);
		String deathsCount=js.getString("deaths");
		Assert.assertTrue((Integer.parseInt(deathsCount.replaceAll("[\\[\\]]", "")))>=0);
	}


}
