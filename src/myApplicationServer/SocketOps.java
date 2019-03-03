package myApplicationServer;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketOps {
	
	private Socket socket;
	private DataOutputStream dos;
	private DataInputStream dis;
	private BufferedWriter bw; 

	public SocketOps() {
	
	}
	
	public void generateResources(Socket socket) throws IOException {
		try {
			this.socket = socket;
			socket.setSoTimeout(60000);
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void purgeResources() throws IOException {
		socket.close();
		bw.close();
		dis.close();	
		dos.close();
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public DataOutputStream getDos() {
		return dos;
	}

	public void setDos(DataOutputStream dos) {
		this.dos = dos;
	}

	public DataInputStream getDis() {
		return dis;
	}

	public void setDis(DataInputStream dis) {
		this.dis = dis;
	}

	public BufferedWriter getBw() {
		return bw;
	}

	public void setBw(BufferedWriter bw) {
		this.bw = bw;
	}
}
