import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Project01_B {
	public static void main(String[] args) {
		// JSON-Java(org.json)
		
		JSONArray students = new JSONArray();
		
		Scanner sc = new Scanner(System.in);
		
		String command = null;
		
		while(true) {
			System.out.println("if you want to exit, enter 'exit'.");
			command = sc.nextLine();
			
			if(command.equals("exit")) {
				break;
			}

			String name = null;
			String phone = null;
			String address = null;
			
			System.out.println("what's your name? : ");
			name = sc.nextLine();
			
			System.out.println("what's your phone number? : ");
			phone = sc.nextLine();
			
			System.out.println("where do you live? : ");
			address = sc.nextLine();
			
			JSONObject student = new JSONObject();
			student.put("name", name);
			student.put("phone", phone);
			student.put("address", address);
			
			students.put(student);
		}
		
		JSONObject object = new JSONObject();
		object.put("students", students);
		
		System.out.println(object.toString(1));
	}
}
