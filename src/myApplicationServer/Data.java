package myApplicationServer;

public class Data {
	
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	private String tablice;
	
	public String getTablice() {
		return tablice;
	}
	public void setTablice(String tablice) {
		this.tablice = tablice;
	}
	
	private int codecID; //1B
	private int numberOfData; //1B
	private String timeStamp; //8B
	private int priority; //1B
	private double longitude; //4B
	private double latitude; //4B
	private double altitude; //2B
	private double angle; //2B
	private int satellites; //1B
	private double speed;//2B
	
	private Integer dis1; 
	private Integer dis2; 
	private Integer dis3;
	private Integer analogInput;
	private Integer gsmLvl;
	private Double ioSpeed;
	private Integer epv;
	private Integer gpsPwr;
	private Integer temperature;
	private Long buttonID;
	private Integer dataMode;
	private Integer do1s;
	private Integer do2s;
	private Integer pdop;
	private Integer hdop;
	private Double odometer;
	private Integer sleep;
	private Integer gsmID;
	private Integer areaCode;
	private Integer movementSensor;
	private Integer gsmOpCode;
	private Integer gz1;
	private Integer gz2;
	private Integer gz3;
	private Integer gz4;
	private Integer gz5;
	private Integer autoGeof;
	private Integer trip;
	private Integer immobilizer;
	private Integer authorisedDriving;
	private Integer gdt;
	private Integer gdv;
	private Integer os;
	
	public int getCodecID() {
		return codecID;
	}
	public void setCodecID(int codecID) {
		this.codecID = codecID;
	}
	public int getNumberOfData() {
		return numberOfData;
	}
	public void setNumberOfData(int numberOfData) {
		this.numberOfData = numberOfData;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
	public int getSatellites() {
		return satellites;
	}
	public void setSatellites(int satellites) {
		this.satellites = satellites;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public Integer getDis1() {
		return dis1;
	}
	public void setDis1(Integer dis1) {
		this.dis1 = dis1;
	}
	public Integer getDis2() {
		return dis2;
	}
	public void setDis2(Integer dis2) {
		this.dis2 = dis2;
	}
	public Integer getDis3() {
		return dis3;
	}
	public void setDis3(Integer dis3) {
		this.dis3 = dis3;
	}
	public Integer getAnalogInput() {
		return analogInput;
	}
	public void setAnalogInput(Integer analogInput) {
		this.analogInput = analogInput;
	}
	public Integer getGsmLvl() {
		return gsmLvl;
	}
	public void setGsmLvl(Integer gsmLvl) {
		this.gsmLvl = gsmLvl;
	}
	public Double getIoSpeed() {
		return ioSpeed;
	}
	public void setIoSpeed(Double ioSpeed) {
		this.ioSpeed = ioSpeed;
	}
	public Integer getEpv() {
		return epv;
	}
	public void setEpv(Integer epv) {
		this.epv = epv;
	}
	public Integer getGpsPwr() {
		return gpsPwr;
	}
	public void setGpsPwr(Integer gpsPwr) {
		this.gpsPwr = gpsPwr;
	}
	public Integer getTemperature() {
		return temperature;
	}
	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}
	public Long getButtonID() {
		return buttonID;
	}
	public void setButtonID(Long buttonID) {
		this.buttonID = buttonID;
	}
	public Integer getDataMode() {
		return dataMode;
	}
	public void setDataMode(Integer dataMode) {
		this.dataMode = dataMode;
	}
	public Integer getDo1s() {
		return do1s;
	}
	public void setDo1s(Integer do1s) {
		this.do1s = do1s;
	}
	public Integer getDo2s() {
		return do2s;
	}
	public void setDo2s(Integer do2s) {
		this.do2s = do2s;
	}
	public Integer getPdop() {
		return pdop;
	}
	public void setPdop(Integer pdop) {
		this.pdop = pdop;
	}
	public Integer getHdop() {
		return hdop;
	}
	public void setHdop(Integer hdop) {
		this.hdop = hdop;
	}
	public Double getOdometer() {
		return odometer;
	}
	public void setOdometer(Double odometer) {
		this.odometer = odometer;
	}
	public Integer getSleep() {
		return sleep;
	}
	public void setSleep(Integer sleep) {
		this.sleep = sleep;
	}
	public Integer getGsmID() {
		return gsmID;
	}
	public void setGsmID(Integer gsmID) {
		this.gsmID = gsmID;
	}
	public Integer getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(Integer areaCode) {
		this.areaCode = areaCode;
	}
	public Integer getMovementSensor() {
		return movementSensor;
	}
	public void setMovementSensor(Integer movementSensor) {
		this.movementSensor = movementSensor;
	}
	public Integer getGsmOpCode() {
		return gsmOpCode;
	}
	public void setGsmOpCode(Integer gsmOpCode) {
		this.gsmOpCode = gsmOpCode;
	}
	public Integer getGz1() {
		return gz1;
	}
	public void setGz1(Integer gz1) {
		this.gz1 = gz1;
	}
	public Integer getGz2() {
		return gz2;
	}
	public void setGz2(Integer gz2) {
		this.gz2 = gz2;
	}
	public Integer getGz3() {
		return gz3;
	}
	public void setGz3(Integer gz3) {
		this.gz3 = gz3;
	}
	public Integer getGz4() {
		return gz4;
	}
	public void setGz4(Integer gz4) {
		this.gz4 = gz4;
	}
	public Integer getGz5() {
		return gz5;
	}
	public void setGz5(Integer gz5) {
		this.gz5 = gz5;
	}
	public Integer getAutoGeof() {
		return autoGeof;
	}
	public void setAutoGeof(Integer autoGeof) {
		this.autoGeof = autoGeof;
	}
	public Integer getTrip() {
		return trip;
	}
	public void setTrip(Integer trip) {
		this.trip = trip;
	}
	public Integer getImmobilizer() {
		return immobilizer;
	}
	public void setImmobilizer(Integer immobilizer) {
		this.immobilizer = immobilizer;
	}
	public Integer getAuthorisedDriving() {
		return authorisedDriving;
	}
	public void setAuthorisedDriving(Integer authorisedDriving) {
		this.authorisedDriving = authorisedDriving;
	}
	public Integer getGdt() {
		return gdt;
	}
	public void setGdt(Integer gdt) {
		this.gdt = gdt;
	}
	public Integer getGdv() {
		return gdv;
	}
	public void setGdv(Integer gdv) {
		this.gdv = gdv;
	}
	public Integer getOs() {
		return os;
	}
	public void setOs(Integer os) {
		this.os = os;
	}

}
