package myApplicationServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
	
	private final HashMap<String, String> tablice = new HashMap<String, String>();
	private ServerSocket serverSocket = null;
	//private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	public void listen() throws Exception {
		
		serverSocket = new ServerSocket(5000);
		
		//dodati vrijednosti u hashMapu tablice
		if(tablice.isEmpty()) {
			tablice.put("123456789012345", "O78K396");
			tablice.put("356307048140403", "Testne tablice");
		}
		
		while(true) {
			System.out.println("\nCekam da se uređaj poveze...");
			Socket socket = serverSocket.accept();
			
			//ako while(true) bude radilo samo za jedan uređaj, 
			//kreirati listu runnable-sa i dodavati u listu svaki novi accept
			Runnable task = new Runnable() {
				@Override
				public void run() {
					ConnectionHandler ch = new ConnectionHandler(tablice);
					while(true) {
						System.out.println("\nUlazim u obradu...");
						boolean var = ch.handleConnection(socket);
						if(var == false) {
							System.out.println("Bacen je neki exception, idemo ponovo...");
							break;
						}
					}
				}
			};
			task.run();
		}
	}
}
