package wonjin.socket.multichat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MultiChatClient {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1", 9999);
			Scanner scanner = new Scanner(System.in);
			
			String name = scanner.nextLine();

			Thread sender = new ClientSender(socket, name);
			Thread receiver = new ClientReceiver(socket);
			
			sender.start();
			receiver.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static class ClientSender extends Thread {
		Socket socket; DataOutputStream out; String name;
		ClientSender(Socket socket, String name) {
			this.socket = socket;
			this.name = name;
			
			try {
				out = new DataOutputStream(socket.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void run() {
			try {
				Scanner scanner = new Scanner(System.in);
				
				if(out != null) 
					out.writeUTF(name);
				
				while(out != null) {
					String message = scanner.nextLine();
					
					if(message.equals("quit"))
						break;
					
					out.writeUTF("[" + name + "]" + message);
				}
				
				out.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	static class ClientReceiver extends Thread {
		Socket socket; DataInputStream in;
		
		ClientReceiver(Socket socket) {
			this.socket = socket;
			
			try {
				in = new DataInputStream(socket.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void run() {
			while(in != null) {
				try {
					System.out.println(in.readUTF());
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
			
			try {
				in.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
