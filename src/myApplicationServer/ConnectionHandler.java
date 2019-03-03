package myApplicationServer;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class ConnectionHandler {
	private HashMap<String, String> tablice = new HashMap<String, String>();
	private ArrayList<Data> podaci = new ArrayList<Data>(5000);
	private String result = "";
	
	public ConnectionHandler(HashMap<String, String> tablice) {
		this.tablice = tablice;
	}
	
	int br = 1; 
	public boolean handleConnection(Socket socket) {
		System.out.println("\nU petlji " + br++ + ". put...");
		
		SocketOps socketOps = new SocketOps();
		try {
			socketOps.generateResources(socket);
		} catch(IOException e) {
			System.out.println("Desio se IOException 1...");
			e.printStackTrace();
			return false;
		}
		
		byte[] imeiByte = new byte[4096];
		byte[] avlDataArray = new byte[4096];
		byte[] addedBytes, concatArray;
		
		DataInputStream dis = socketOps.getDis();
		DataOutputStream dos = socketOps.getDos();
		BufferedWriter bw = socketOps.getBw();
		System.out.println("Generisani resursi...");
		
		try {
			dis.read(imeiByte);
		}
		catch(SocketTimeoutException e) {
			System.out.println("Desio se socket timeout na IMEI...");
			System.out.println("Vrijeme je: " + ZonedDateTime.now());
			try {
				socketOps.purgeResources();
				return false;
			} catch(IOException ex) {
				System.out.println("Desio se IOException 2...");
				e.printStackTrace();
				return false;
			}
	    } catch (IOException e) {
	    	System.out.println("Desio se IOException 3...");
	    	e.printStackTrace();
	    	return false;
		}
		Result r = new Result();
		
		//ako ponovo salje IMEI
		if(imeiByte[1] != 0 || imeiByte.length == 17) {
			System.out.println("Vrijeme primanja imei-a: " + ZonedDateTime.now());
			
			String imei = "";
			for(int i = 2; i < 17; i++) {
				imei += String.valueOf(lookup(imeiByte[i]));
			}
			System.out.println("Imei je: " + imei);
			
			try {
				if(tablice.containsKey(imei)) { 
					bw.write(0x01);
					System.out.println("Vrijeme vracanja KECA (0x01): " + ZonedDateTime.now());
					bw.flush();
				} else {
					bw.write(0x00);
					System.out.println("Vrijeme vracanja NULE (0x00): " + ZonedDateTime.now());
					bw.flush();
				}
			} catch(IOException e) {
				System.out.println("Desio se IOException 4...");
				e.printStackTrace();
				return false;
			}
			
			//tablice vozila koje je poslalo imei
			result = tablice.get(imei);
			if(result == null) result = "";
			System.out.println("Tablice su: " + result);
			
			if(result.equals("")) {
				System.out.println("Vracena je nula, idemo ponovo...");
				try {
					socketOps.purgeResources();
					return false;
				} catch(IOException e) {
					System.out.println("Desio se IOException 5...");
					e.printStackTrace();
					return false;
				}
			}
		
			try {
				dis.read(avlDataArray);
			}
			catch(SocketTimeoutException e) {
				System.out.println("Desio se socket timeout na Data...");
				try {
					socketOps.purgeResources();
					return false;
				} catch(IOException ex) {
					System.out.println("Desio se IOException 6...");
					e.printStackTrace();
					return false;
				}
			} 
			catch (IOException e) {
		    	System.out.println("Desio se IOException 7...");
		    	e.printStackTrace();
		    	return false;
			}

			System.out.println("Vrijeme primanja paketa: " + ZonedDateTime.now());		
		}
		else {
			System.out.println("Vrijeme primanja paketa (nestandardno): " + ZonedDateTime.now());
			System.arraycopy(imeiByte, 0, avlDataArray, 0, imeiByte.length);
		}
		
		r = checkPacket(avlDataArray);
		if(!r.var) {
			System.out.println("Nije poslan kompletan packet...");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			}
			addedBytes = new byte[100];
			try {
				dis.read(addedBytes);
			}
			catch(SocketTimeoutException e) {
				System.out.println("Desio se socket timeout (kod dodatnih bytea)...");
				try {
					socketOps.purgeResources();
					return false;
				} catch(IOException ex) {
					System.out.println("Desio se IOException 8...");
					e.printStackTrace();
					return false;
				}
			}
			catch (IOException e) {
		    	System.out.println("Desio se IOException 9...");
		    	e.printStackTrace();
		    	return false;
			}
			concatArray = new byte[r.length + 100];
			System.arraycopy(avlDataArray, 0, concatArray, 0, r.length);
			System.arraycopy(addedBytes, 0, concatArray, r.length, 100);
		}
		else {
			concatArray = new byte[r.length];
			System.arraycopy(avlDataArray, 0, concatArray, 0, r.length);
			System.out.println("Poslan je kompletan packet...");
		}
		
		if(concatArray.length < 50) {
			System.out.println("Poslan je packet koji nije ni IMEI ni data...");
			System.out.println("Vrijeme je: " + ZonedDateTime.now());
			return false;
		}
		Parser parser = new Parser();
	
		//parsirani podaci koje je poslao uredjaj
		ArrayList<Data> parsedData = null;
		try {
			parsedData = parser.parse(concatArray);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
		System.out.println("Pa sam i parsirao podatke");
		
		for(int i = 0; i < parsedData.size(); i++) {
			parsedData.get(i).setTablice(result);
			System.out.println((i+1) + ". timestamp = " + parsedData.get(i).getTimeStamp());
			podaci.add(parsedData.get(i));
		}
		
		//vratiti response uredjaju
		try {
			int numOfData = parsedData.get(0).getNumberOfData();
			dos.writeInt(numOfData);
			System.out.println("Vrijeme slanja responsea: " + ZonedDateTime.now());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}	
        
		try {
			if(podaci.size() > 0) {
			    saveToDataBase(podaci);
			    System.out.println("Spaseno u bazu podataka...");
			    podaci.clear();
			}
		}
		catch(HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
    private void saveToDataBase(ArrayList<Data> data) throws HibernateException {
		
		Locale.setDefault(Locale.US);
		
		Session session = DataBaseOps.getSessionFactory().openSession();
		session.beginTransaction();
		
		int size = data.size();
		for(int i = 0; i < size; i++) {
			session.save(data.get(i));
			//batch writing
			if(i % 20 == 0) {
				session.flush();
				session.clear();
			}
		}
		session.getTransaction().commit();
		session.close();
	}
	
	private int lookup(byte b) {
		return (int)b - 48;
	}
	
	private class Result {
		Integer numOfData;
		Integer length;
		boolean var;
		
		public Result() {}
	}
	
	private Result checkPacket(byte[] data) {
		Result r = new Result();
		
		long size = 0;
		if(Byte.toUnsignedInt(data[6]) == 0) {
			size = Byte.toUnsignedLong(data[7]);
		}
		else {
			byte[] arr = new byte[2];
			arr[0] = (byte)Byte.toUnsignedInt(data[6]);
			arr[1] = (byte)Byte.toUnsignedInt(data[7]);
			Parser p = new Parser();
			size = p.byteArrayToLong(arr);
		}
		r.numOfData = Byte.toUnsignedInt(data[9]);
		r.var = false;
		r.length = (int)size + 7;
		
		if(r.numOfData.equals(Byte.toUnsignedInt(data[(int)size + 7]))) r.var = true;
		
		return r;
	}
}
