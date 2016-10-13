package model;

/**
 * Created by alexona on 10/13/16.
 */

public class Place {
    private float lon;
    private float lat;
    private long sunset;
    private long sunrise;
    private String country;
    private String city;
    private long lastupdate;

    public float getLon() {
        return lon;
    }
    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }
    public void setLat(float lat) {
        this.lat = lat;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {

        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getLastupdate() {

        return lastupdate;
    }

    public void setLastupdate(long lastupdate) {
        this.lastupdate = lastupdate;
    }

    public long getSunrise() {

        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {

        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }
}
