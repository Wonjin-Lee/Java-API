package wonjin.socket.echo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

	public static void main(String[] args) {
		try {
			// accept
			Socket socket = new Socket("127.0.0.1", 9999);
			System.out.println("Successfully Connected!");
			
			Scanner scanner = new Scanner(System.in);
			String message = scanner.nextLine();
			
			// output
			OutputStream out = socket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(out);
			dataOutputStream.writeUTF(message);
			
			// input
			InputStream inputStream = socket.getInputStream();
			DataInputStream dataInputStream = new DataInputStream(inputStream);
			System.out.println("Message(Received) :  " + dataInputStream.readUTF());

			dataInputStream.close();
			dataOutputStream.close();
			socket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
