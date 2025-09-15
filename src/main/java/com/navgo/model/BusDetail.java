/**
 * 
 */
package com.navgo.model;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/**
 * @author Akash Bais
 *
 */
@Entity
public class BusDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int busDetailId;
	public String busUniversityNumber;
	public String busRTONumber;
	public String busHealth;

	public int getBusDetailId() {
		return busDetailId;
	}

	public void setBusDetailId(int busDetailId) {
		this.busDetailId = busDetailId;
	}

	public String getBusUniversityNumber() {
		return busUniversityNumber;
	}

	public void setBusUniversityNumber(String busUniversityNumber) {
		this.busUniversityNumber = busUniversityNumber;
	}

	public String getBusRTONumber() {
		return busRTONumber;
	}

	public void setBusRTONumber(String busRTONumber) {
		this.busRTONumber = busRTONumber;
	}

	public String getBusHealth() {
		return busHealth;
	}

	public void setBusHealth(String busHealth) {
		this.busHealth = busHealth;
	}

}
