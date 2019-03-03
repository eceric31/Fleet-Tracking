package myApplicationServer;

public class Program {

	public static void main(String[] args) {
		
		Server server = new Server();
		while(true) {
			try {
				server.listen();
			} catch (Exception e) {
				continue;
			}
		}
	}

}


