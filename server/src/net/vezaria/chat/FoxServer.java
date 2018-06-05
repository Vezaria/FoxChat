package net.vezaria.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FoxServer {

	public static final int PORT = 2131;
	
	private ServerSocket socket;
	private ExecutorService threadPool;
	
	public FoxServer() {
		threadPool = Executors.newCachedThreadPool();
	}
	
	public void start() {
		try {
			socket = new ServerSocket(PORT);
			System.out.println("Server started on port " + PORT + "...");
			while(true) {
				Socket incoming = socket.accept();
				System.out.println("Received connection.");
				threadPool.submit(new Connection(this, incoming));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		FoxServer server = new FoxServer();
		server.start();
	}
}
