package wonjin.socket.multichat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class MultiChatServer {
	
	HashMap<String, DataOutputStream> clientList;
	
	MultiChatServer() {
		clientList = new HashMap<String, DataOutputStream>();
		Collections.synchronizedMap(clientList);
	}
	
	public void start() {
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		try {
			serverSocket = new ServerSocket(9999);

			System.out.println("Start Server...");
			while(true) {
				socket = serverSocket.accept();
				
				System.out.println(socket.getInetAddress() + " : " + socket.getPort() + " was connected!");
				
				ServerReceiver thread = new ServerReceiver(socket);
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendToAll(String message) {
		Iterator<String> iterator = clientList.keySet().iterator();
		
		while(iterator.hasNext()) {
			try {
				DataOutputStream out = (DataOutputStream) clientList.get(iterator.next());
				out.writeUTF(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new MultiChatServer().start();
	}
	
	class ServerReceiver extends Thread {
		
		Socket socket; DataInputStream in; DataOutputStream out;
		
		ServerReceiver(Socket socket) {
			this.socket = socket;
			
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void run() {
			String name = "";
			
			try {
				name = in.readUTF();
				
				if(clientList.get(name) != null) {
					out.writeUTF("Already exist name : " + name);
					out.writeUTF("Please reconnect by other name");
					System.out.println(socket.getInetAddress() + " : " + socket.getPort() + " was disconnected!");
					in.close();
					out.close();
					socket.close();
					socket = null;
				} else {
					sendToAll(name + "님이 대화방에 들어오셨습니다.");
					clientList.put(name, out);
					
					while(in != null) {
						sendToAll(in.readUTF());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(socket != null) {
					sendToAll(name + "님이 대화방을 나가셨습니다.");
					clientList.remove(name);

					System.out.println(socket.getInetAddress() + " : " + socket.getPort() + " was disconnected!");
				}
			}
		}
	}
}