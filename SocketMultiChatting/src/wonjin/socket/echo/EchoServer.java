package wonjin.socket.echo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket(9999);
			System.out.println("Server ready...");
		} catch (Exception e) {

		}
		while(true) {
			try {
				Socket socket = serverSocket.accept();
				System.out.println("Client was successfully connected!");
				
				// input
				InputStream inputStream = socket.getInputStream();
				DataInputStream dataInputStream = new DataInputStream(inputStream);
				String message = dataInputStream.readUTF();
				
				// output
				OutputStream outputStream = socket.getOutputStream();
				DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
				dataOutputStream.writeUTF("[ECHO]" + message + "(from Server!)");
				
				// close
				dataOutputStream.close();
				dataInputStream.close();
				socket.close();
				
				System.out.println("Client Socket closed...");
				
			} catch (Exception e) {

			}
		}
	}

}
