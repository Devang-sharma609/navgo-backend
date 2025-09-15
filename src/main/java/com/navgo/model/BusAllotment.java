/**
 * 
 */
package com.navgo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

/**
 * @author Akash Bais
 *
 */
@Entity
public class BusAllotment {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int busAllotmentId;

	    @ManyToOne
	    @JoinColumn(name = "busDetailId")
	    private BusDetail busDetail;

	    @ManyToOne
	    @JoinColumn(name = "routeId")
	    private Route route;
	    
	    @ManyToOne
	    @JoinColumn(name= "driverId")
	    private Driver driver;

	    private LocalDate allotmentDate;

		public int getBusAllotmentId() {
			return busAllotmentId;
		}

		public void setBusAllotmentId(int busAllotmentId) {
			this.busAllotmentId = busAllotmentId;
		}

		public BusDetail getBusDetail() {
			return busDetail;
		}

		public void setBusDetail(BusDetail busDetail) {
			this.busDetail = busDetail;
		}

		public Route getRoute() {
			return route;
		}

		public void setRoute(Route route) {
			this.route = route;
		}

		public Driver getDriver() {
			return driver;
		}

		public void setDriver(Driver driver) {
			this.driver = driver;
		}

		public LocalDate getAllotmentDate() {
			return allotmentDate;
		}

		public void setAllotmentDate(LocalDate allotmentDate) {
			this.allotmentDate = allotmentDate;
		}
	    
	    
	    
	    
}
