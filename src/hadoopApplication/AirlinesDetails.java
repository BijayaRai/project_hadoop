package hadoopApplication;

public class AirlinesDetails {

	private String airportName;
	private String airportCode;
	private float lat;
	private float longi;
	
	public String airlineInfo()
	{
		return "Airport Name:" +airportName+ " Airport Code:"+airportCode+" Lat:"+lat +" Longi:"+longi;
	}

	/**
	 * data from "airlines.csv" to be assigned as individual object
	 * 
	 * @param airportName
	 * @param airportCode
	 * @param lat
	 * @param longi
	 */
	public AirlinesDetails(String airportName, String airportCode, float lat,
			float longi) {
		super();
		this.airportName = airportName;
		this.airportCode = airportCode;
		this.lat = lat;
		this.longi = longi;
	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLongi() {
		return longi;
	}

	public void setLongi(float longi) {
		this.longi = longi;
	}
	
	
}
