/**
 * 
 */
package com.navgo.model;

import java.util.List;

/**
 * @author Akash Bais
 *
 */

public class BusBoardInfo {

	private String busUniversityNumber;
	private String routeName;
	private String driverName;
	private String driverNumber;
	private List<Stop> stops;

	public BusBoardInfo() {
		super();
	}

	public BusBoardInfo(String busUniversityNumber, String routeName, String driverName, String driverNumber, List<Stop> stops) {
	    this.busUniversityNumber = busUniversityNumber;
	    this.routeName = routeName;
	    this.driverName = driverName;
	    this.driverNumber = driverNumber;
	    this.stops = stops;
	}
	
    public BusBoardInfo(String busUniversityNumber, String routeName, String driverName, String driverNumber) {
        this.busUniversityNumber = busUniversityNumber;
        this.routeName = routeName;
        this.driverName = driverName;
        this.driverNumber = driverNumber;
    }


	public String getBusUniversityNumber() {
		return busUniversityNumber;
	}

	public void setBusUniversityNumber(String busUniversityNumber) {
		this.busUniversityNumber = busUniversityNumber;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverNumber() {
		return driverNumber;
	}

	public void setDriverNumber(String driverNumber) {
		this.driverNumber = driverNumber;
	}

	public List<Stop> getStops() {
		return stops;
	}

	public void setStops(List<Stop> stops) {
		this.stops = stops;
	}

	@Override
	public String toString() {
		return "BusBoardInfo [busUniversityNumber=" + busUniversityNumber + ", routeName=" + routeName + ", driverName="
				+ driverName + ", driverNumber=" + driverNumber + ", stops=" + stops + "]";
	}

}
