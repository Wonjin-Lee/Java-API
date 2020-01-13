package wonjin.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gson.JsonObject;

public class NaverMapsAPI {
	// 지도 이미지 생성 메서드
	static void mapService(String point_x, String point_y, String address) {
		// NAVER Static Map API REQUEST URL
		String URL_STATICMAP = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
		try {
			// 좌표
			String pos = URLEncoder.encode(point_x + " " + point_y, "UTF-8");
			
			String url = URL_STATICMAP;
			url += "center=" + point_x + "," + point_y;
			url += "&level=16&w=700&h=500";
			url += "&markers=type:t|size:mid|pos:" + pos + "|label:" + URLEncoder.encode(address, "UTF-8");
			
			URL u = new URL(url);
			
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "otjbrhkd7x");
			conn.setRequestProperty("X-NCP-APIGW-API-KEY", "Sp6ihJdaRjEoohZeM5JQkneVrWL86RXEt9mhv1KQ");
			
			int responseCode = conn.getResponseCode();
			
			BufferedReader br;
			
			if(responseCode == 200) {
				InputStream is = conn.getInputStream();
				int read = 0;
				// Static Map - raster는 PNG, JPG를 반환하기 때문에 byte 배열에 저장
				byte[] bytes = new byte[1024];
				
				// 받아온 이미지의 이름이 중복이 되지 않도록 랜덤으로 파일명을 생성
				String tempname = Long.valueOf(new Date().getTime()).toString();
				
				File f = new File("./src/" + tempname + ".jpg");
				f.createNewFile();
				
				OutputStream outputStream = new FileOutputStream(f);
				while((read = is.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
				is.close();
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while((inputLine = br.readLine()) != null) {
					response.append(inputLine);
				}
				br.close();
				System.out.println(response.toString());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
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
			
			String x = "", y = "", z = "";
			
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
				
				x = (String) temp.get("x");
				y = (String) temp.get("y");
				z = (String) temp.get("roadAddress");
			}
			// mapService() : 이미지로 표현
			mapService(x, y, z);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
