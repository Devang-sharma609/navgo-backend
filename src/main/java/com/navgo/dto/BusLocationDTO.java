/**
 * 
 */
package com.navgo.dto;

/**
 * @author Akash Bais
 *
 */

import com.fasterxml.jackson.annotation.JsonProperty;

public class BusLocationDTO {

    @JsonProperty("busNumber")
    private String busId;

    @JsonProperty("latitude")
    private double lat;

    @JsonProperty("longitude")
    private double lon;

    @JsonProperty("accuracy")
    private double accuracy;

    @JsonProperty("speed")
    private double speed;

    @JsonProperty("bearing")
    private double bearing;

    @JsonProperty("timestamp")
    private long timestamp;

    public BusLocationDTO() {}

    public BusLocationDTO(String busId, double lat, double lon, double accuracy, double speed, double bearing, long timestamp) {
        this.busId = busId;
        this.lat = lat;
        this.lon = lon;
        this.accuracy = accuracy;
        this.speed = speed;
        this.bearing = bearing;
        this.timestamp = timestamp;
    }

    public String getBusId() { return busId; }
    public double getLat() { return lat; }
    public double getLon() { return lon; }
    public double getAccuracy() { return accuracy; }
    public double getSpeed() { return speed; }
    public double getBearing() { return bearing; }
    public long getTimestamp() { return timestamp; }
}

