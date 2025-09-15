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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
/**
 * @author Akash Bais
 *
 */
@Entity
public class Route {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int routeId;

	private String routeName;

	@ManyToMany(fetch = FetchType.EAGER)
	 @JoinTable(
		        name = "route_stop",
		        joinColumns = @JoinColumn(name = "routeId"),
		        inverseJoinColumns = @JoinColumn(name = "stopId")
		    )
	private List<Stop> stops;
	
	public Route() {
		super();
	}

	public int getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public List<Stop> getStops() {
		return stops;
	}

	public void setStops(List<Stop> stops) {
		this.stops = stops;
	}

	@Override
	public String toString() {
		return "Route [routeId=" + routeId + ", routeName=" + routeName + ", stops=" + stops + "]";
	}

	
}
