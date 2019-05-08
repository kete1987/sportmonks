package com.kete.sportmonks.library.model.venue;

public class VenueDetail {
    private Long id;
    private String name;
    private String surface;
    private String address;
    private String city;
    private int capacity = -1;
    private String image_path;

    public VenueDetail(){

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurface() {
        return surface;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getImage_path() {
        return image_path;
    }
}
