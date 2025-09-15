/**
 * 
 */
package com.navgo.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

/**
 * @author Akash Bais
 *
 */
@Entity
public class Stop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int stopId;

	private String stopName;

	@ManyToMany(mappedBy = "stops",fetch = FetchType.EAGER)
	private List<Route> routes;
	
	
	public Stop() {
		super();
	}

	public int getStopId() {
		return stopId;
	}

	public void setStopId(int stopId) {
		this.stopId = stopId;
	}

	public String getStopName() {
		return stopName;
	}

	public void setStopName(String stopName) {
		this.stopName = stopName;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	@Override
	public String toString() {
		return "Stop [stopId=" + stopId + ", stopName=" + stopName + ", routes=" + routes + "]";
	}

	
}
