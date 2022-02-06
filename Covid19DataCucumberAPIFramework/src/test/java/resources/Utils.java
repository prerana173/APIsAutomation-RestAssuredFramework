package resources;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {
	public static RequestSpecification req_spec;
	public RequestSpecification requestSpecification() throws IOException {	
		if(req_spec==null) {
			PrintStream log =new PrintStream(new FileOutputStream("logging.txt"));
			req_spec =RestAssured.given().baseUri("https://covid-19-data.p.rapidapi.com")
					.filter(RequestLoggingFilter.logRequestTo(log))
					.filter(ResponseLoggingFilter.logResponseTo(log));
			return req_spec;
			
		}
		return req_spec;		
	}

	public static JsonPath getJsonPath(Response response)
	{
		String resp=response.asString();
		JsonPath js = new JsonPath(resp);
		return js;
	}

	public static String changeToValidString(Response response, String payloadkey) {
		JsonPath js=getJsonPath(response);
		String temp=js.getString(payloadkey);
		return temp.replaceAll("[\\[\\]]", "");
	}


}
