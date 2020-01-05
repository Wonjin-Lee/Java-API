package wonjin.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gson.JsonObject;

public class NaverMapsAPI {
	public static void main(String[] args) {
		// Naver Maps API URL
		String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=";
		String clientId = "otjbrhkd7x";
		String clientSecret = "Sp6ihJdaRjEoohZeM5JQkneVrWL86RXEt9mhv1KQ";
		
		/*
		 * System.in - 바이트 스트림
		 * BufferedReader - 문자 스트림
		 * InputStreamReader - 브릿지 스트림
		 */
		BufferedReader io = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			System.out.print("주소를 입력하세요 : ");
			String address = io.readLine();
			
			// 공백 제거(%20)
			String addr = URLEncoder.encode(address, "UTF-8");
			
			// API에 요청할 URL
			String requestUrl = apiURL + addr;
			
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
			conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
			
			BufferedReader br;
			
			// HTTP Request Code
			int responseCode = conn.getResponseCode(); // 200
			
			// get JSON
			if(responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			
			String line;
			StringBuffer response = new StringBuffer();
			
			while((line = br.readLine()) != null) {
				response.append(line);
			}
			
			br.close();
			
			JSONTokener tokener = new JSONTokener(response.toString());
			JSONObject object = new JSONObject(tokener);
			System.out.println(object.toString(2));
			
			JSONArray array = object.getJSONArray("addresses");
			
			for(int i=0; i < array.length(); i++) {
				JSONObject temp = (JSONObject) array.get(i);
				System.out.println("roadAddress : " + temp.get("roadAddress"));
				System.out.println("jibunAddress : " + temp.get("jibunAddress"));
				System.out.println("경도 : " + temp.get("x"));
				System.out.println("위도 : " + temp.get("y"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
