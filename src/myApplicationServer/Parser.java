package myApplicationServer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Parser {
	
	public long byteArrayToLong(byte[] b) {
		Long l = new Long(0);
		
		ByteBuffer bb = ByteBuffer.wrap(b);
		
		switch(b.length) {
			case 2:
				l = (long)bb.getShort();
				break;
			case 4: 
				l = (long)bb.getInt();
				break;
			case 8: 
				l = bb.getLong();
				break;
		}
		
		return l;
	}
	
	public ArrayList<Data> parse(byte[] bytes) throws UnsupportedEncodingException {
		ArrayList<Data> dataArray = new ArrayList<Data>();
	
		int index = 8; 
		
		//DEFAULT ELEMENTS
		
		byte byteData;
		
		//codecID
		byteData = bytes[index];
		int codecID = Byte.toUnsignedInt(byteData);
		//System.out.println(index-8 + " codecid " + Byte.toUnsignedInt(bytes[index]));
		
		//numberOfData
		index++;
		byteData = bytes[index];
		int numberOfData = Byte.toUnsignedInt(byteData);
		//System.out.println(index-8 + " number of data " + Byte.toUnsignedInt(bytes[index]));
		index++;
		
		for(int br = 0; br < numberOfData; br++) {
			Data data = new Data();
			data.setCodecID(codecID);
			data.setNumberOfData(numberOfData);
			
			//System.out.println("\n" + (br+1) + ". set of data\n");
			
			if(br != 0) index += 2;
			
			//timeStamp
			byte[] timeStampBytes = new byte[8];
			for(int i = 0; i < 8; i++) {
				timeStampBytes[i] = bytes[index];
				//System.out.println(index-8 + " timestamp " + Byte.toUnsignedInt(bytes[index]));
				index++;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
			Date date = new Date(byteArrayToLong(timeStampBytes));
			data.setTimeStamp(sdf.format(date)); 
			
			//priority
			byteData = bytes[index];
			data.setPriority(Byte.toUnsignedInt(byteData));
			//System.out.println(index-8 + " priority " + Byte.toUnsignedInt(bytes[index]));
			
			//longitude + latitude
			index++;
			byte[] lon = new byte[4];
			byte[] lat = new byte[4];
			for(int i = 0; i < 8; i++) {
				if(i < 4) lon[i] = bytes[index];
				else if(i > 3) lat[i - 4] = bytes[index];
				//if(i < 4) System.out.println(index-8 + " longitude " + Byte.toUnsignedInt(bytes[index]));
				//else System.out.println(index-8 + " latitude " + Byte.toUnsignedInt(bytes[index]));
				index++;
			}
			data.setLongitude(((double)byteArrayToLong(lon)) / 10000000.0);
			data.setLatitude(((double)byteArrayToLong(lat)) / 10000000.0);
			
			//altitude 
			byte[] alt = new byte[2];
			alt[0] = bytes[index];
			//System.out.println(index-8 + " altitude " + Byte.toUnsignedInt(bytes[index]));
			index++;
			alt[1] = bytes[index];
			//System.out.println(index-8 + " altitude " + Byte.toUnsignedInt(bytes[index]));
			data.setAltitude(byteArrayToLong(alt));
			
			//angle
			index++;
			byte[] angle = new byte[2];
			angle[0] = bytes[index];
			//System.out.println(index-8 + " angle " + Byte.toUnsignedInt(bytes[index]));
			index++;
			angle[1] = bytes[index];
			//System.out.println(index-8 + " angle " + Byte.toUnsignedInt(bytes[index]));
			data.setAngle(byteArrayToLong(angle));
			
			//satellites
			index++;
			byteData = bytes[index];
			data.setSatellites(Byte.toUnsignedInt(byteData));
			//System.out.println(index-8 + " satellites " + Byte.toUnsignedInt(bytes[index]));
			
			//speed
			index++;
			byte[] speed = new byte[2];
			speed[0] = bytes[index];
			//System.out.println(index-8 + " speed " + Byte.toUnsignedInt(bytes[index]));
			index++;
			speed[1] = bytes[index];
			data.setSpeed((int)byteArrayToLong(speed));
			//System.out.println(index-8 + " speed " + Byte.toUnsignedInt(bytes[index]));
			
			//skip EventIOID
			index += 2;
			
			//total number of IO
			byteData = bytes[index];
			int numberOfTotalIO = Byte.toUnsignedInt(byteData);
			//System.out.println(index-8 + " total io " + Byte.toUnsignedInt(bytes[index]));
			index++; 
			
			//IO ELEMENTS
			int tmp = 0;
			for(int i = 0; i < numberOfTotalIO; i += tmp) {
				
				tmp = 0;
				if(i != 0) index++;
				if(br == data.getNumberOfData() - 1 && i == numberOfTotalIO - 1) index--;
				byteData = bytes[index];
				int numOfIO = Byte.toUnsignedInt(byteData);
				//System.out.println(index-8 + " num of IO " + Byte.toUnsignedInt(bytes[index]));
				
				for(int j = 0; j < numOfIO; j++) {
					
					index++;
					tmp++;
					
					//id of IO element
					byteData = bytes[index];
					Integer ioID = Byte.toUnsignedInt(byteData);
					//System.out.println(index-8 + " id of IO " + Byte.toUnsignedInt(bytes[index]));
					
					//1 byte data
					if(checkOneByte(ioID)) {
						
						index++;
						
						//value of IO element
						byte value = bytes[index];
						//System.out.println(index-8 + " one byte value " + Byte.toUnsignedInt(bytes[index]));
		
						if(ioID.equals(1)) data.setDis1(Byte.toUnsignedInt(value));
						else if(ioID.equals(2)) data.setDis2(Byte.toUnsignedInt(value));
						else if(ioID.equals(3)) data.setDis3(Byte.toUnsignedInt(value));
						else if(ioID.equals(21)) data.setGsmLvl(Byte.toUnsignedInt(value));
						else if(ioID.equals(69)) data.setGpsPwr(Byte.toUnsignedInt(value));
						else if(ioID.equals(80)) data.setDataMode(Byte.toUnsignedInt(value));
						else if(ioID.equals(179)) data.setDo1s(Byte.toUnsignedInt(value));
						else if(ioID.equals(180)) data.setDo2s(Byte.toUnsignedInt(value));
						else if(ioID.equals(200)) data.setSleep(Byte.toUnsignedInt(value));
						else if(ioID.equals(240)) data.setMovementSensor(Byte.toUnsignedInt(value));
						else if(ioID.equals(155)) data.setGz1(Byte.toUnsignedInt(value));
						else if(ioID.equals(156)) data.setGz2(Byte.toUnsignedInt(value));
						else if(ioID.equals(157)) data.setGz3(Byte.toUnsignedInt(value));
						else if(ioID.equals(158)) data.setGz4(Byte.toUnsignedInt(value));
						else if(ioID.equals(159)) data.setGz5(Byte.toUnsignedInt(value));
						else if(ioID.equals(175)) data.setAutoGeof(Byte.toUnsignedInt(value));
						else if(ioID.equals(250)) data.setTrip(Byte.toUnsignedInt(value));
						else if(ioID.equals(251)) data.setImmobilizer(Byte.toUnsignedInt(value));
						else if(ioID.equals(252)) data.setAuthorisedDriving(Byte.toUnsignedInt(value));
						else if(ioID.equals(253)) data.setGdt(Byte.toUnsignedInt(value));
						else if(ioID.equals(254)) data.setGdv(Byte.toUnsignedInt(value));
					}
					
					//2 byte data
					else if(checkTwoByte(ioID)) {
						index++;
						byte[] twoB = new byte[2];
						twoB[0] = bytes[index];
						//System.out.println(index-8 + " two bytes value " + Byte.toUnsignedInt(bytes[index]));
						index++;
						twoB[1] = bytes[index];
						//System.out.println(index-8 + " two bytes value " + Byte.toUnsignedInt(bytes[index]));
						
						if(ioID.equals(9)) data.setAnalogInput((int)byteArrayToLong(twoB));
						else if(ioID.equals(24)) data.setIoSpeed((double)byteArrayToLong(twoB));
						else if(ioID.equals(66)) data.setEpv((int)byteArrayToLong(twoB));
						else if(ioID.equals(181)) data.setPdop((int)byteArrayToLong(twoB));
						else if(ioID.equals(182)) data.setHdop((int)byteArrayToLong(twoB));
						else if(ioID.equals(205)) data.setGsmID((int)byteArrayToLong(twoB));
						else if(ioID.equals(206)) data.setAreaCode((int)byteArrayToLong(twoB));
						else if(ioID.equals(255)) data.setOs((int)byteArrayToLong(twoB));
					}
					
					//4 byte data
					else if(ioID.equals(72) || ioID.equals(199) || ioID.equals(241) || ioID.equals(70)) {
						index++;
						byte[] fourB = new byte[4];
						for(int k = 0; k < 4; k++) {
							fourB[k] = bytes[index];
							//System.out.println(index-8 + " four bytes value " + Byte.toUnsignedInt(bytes[index]));
							if(k < 3) index++;
						}
						if(ioID.equals(72) || ioID.equals(70)) data.setTemperature((int)(byteArrayToLong(fourB) / 10));
						else if(ioID.equals(199)) data.setOdometer((double)byteArrayToLong(fourB));
						else if(ioID.equals(241)) data.setGsmOpCode((int)byteArrayToLong(fourB)); 
					}
					
					//8 byte data
					else if(ioID.equals(78)) {
						index++;
						byte[] eightB = new byte[8];
						for(int k = 0; k < 8; k++) {
							eightB[i] = bytes[index];
							if(k < 7) index++;
						}
						data.setButtonID((long)byteArrayToLong(eightB));
					}
				}
			}
			dataArray.add(data);
		}
		return dataArray;
	}
	
	private boolean checkOneByte(Integer ioID) {
		return ((ioID > 0 && ioID < 4) || ioID.equals(21) || ioID.equals(22) ||
				(ioID > 154 && ioID < 160) || ioID.equals(179)  || ioID.equals(180) || 
				(ioID > 249 && ioID < 255) || ioID.equals(200) || ioID.equals(240)
				 || ioID.equals(175) || ioID.equals(69));
	}
	
	private boolean checkTwoByte(Integer ioID) {
		return (ioID.equals(9) || ioID.equals(24) || ioID.equals(255)
				|| ioID.equals(66) || ioID.equals(181) 
				|| ioID.equals(182) || ioID.equals(205) || ioID.equals(206));
	}
	
}
