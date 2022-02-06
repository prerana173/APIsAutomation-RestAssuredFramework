package resources;

import java.util.HashMap;
import java.util.Map;

public class Payload {

	public static Map<String, String> validHeader() {
		Map<String, String> map=new HashMap<String, String>();
		map.put("x-rapidapi-host","covid-19-data.p.rapidapi.com");
		map.put("x-rapidapi-key","8741e73cf9mshd0d82a7acf7b2c0p153754jsneb9e2cc9d0c7");
		return map;
	}

	public static Map<String, String> invalidHeader() {
		Map<String, String> map=new HashMap<String, String>();
		map.put("x-rapidapi-host"," ");
		map.put("x-rapidapi-key"," ");
		return map;
	}

}
