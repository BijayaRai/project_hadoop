package hadoopApplication;

public class PassangerDetails {

private String passangerId;
private String flighId;
private String departAirportCode;
private String destinationAirportCode;
private String localArrivalTime;
private float flightTime;


/**
 * these values are assigned from file
 * data from "passenger.csv" to be assigned as individual object
 * @param passangerId
 * @param flighId
 * @param departAirportCode
 * @param destinationAirportCode
 * @param localArrivalTime
 * @param flightTime
 */
public PassangerDetails(String passangerId, String flighId,
		String departAirportCode, String destinationAirportCode,
		String localArrivalTime, float flightTime) {
	super();
	this.passangerId = passangerId;
	this.flighId = flighId;
	this.departAirportCode = departAirportCode;
	this.destinationAirportCode = destinationAirportCode;
	this.localArrivalTime = localArrivalTime;
	this.flightTime = flightTime;
}


public String passInfo()
{
return "Passanger id:"+ passangerId +" Flight Id:"+flighId+" departAirportCode:"+departAirportCode + " destionationAirpot:" +destinationAirportCode+ " Time:"+localArrivalTime; 
}

public String getPassangerId() {
	return passangerId;
}
public void setPassangerId(String passangerId) {
	this.passangerId = passangerId;
}
public String getFlighId() {
	return flighId;
}
public void setFlighId(String flighId) {
	this.flighId = flighId;
}
public String getDepartAirportCode() {
	return departAirportCode;
}
public void setDepartAirportCode(String departAirportCode) {
	this.departAirportCode = departAirportCode;
}
public String getDestinationAirportCode() {
	return destinationAirportCode;
}
public void setDestinationAirportCode(String destinationAirportCode) {
	this.destinationAirportCode = destinationAirportCode;
}
public String getLocalArrivalTime() {
	return localArrivalTime;
}
public void setLocalArrivalTime(String localArrivalTime) {
	this.localArrivalTime = localArrivalTime;
}
public float getFlightTime() {
	return flightTime;
}
public void setFlightTime(float flightTime) {
	this.flightTime = flightTime;
}


}
