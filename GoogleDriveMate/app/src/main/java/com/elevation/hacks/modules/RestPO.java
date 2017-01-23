package com.elevation.hacks.modules;


public class RestPO {

    public String place_name;
    public String lat;
    public String lng;
    public String vicinity;
    public double rating;
    public String breakDesc;

    public String getGoogleBreakDesc() {
        return googleBreakDesc;
    }

    public void setGoogleBreakDesc(String googleBreakDesc) {
        this.googleBreakDesc = googleBreakDesc;
    }

    public String googleBreakDesc;

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public void setRating(double rating){this.rating = rating;}

    public double getRating(){return this.rating;}

    public String getBreakDesc() {
        return breakDesc;
    }

    public void setBreakDesc(String breakDesc) {
        this.breakDesc = breakDesc;
    }
}
