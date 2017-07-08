package com.westerdals.gard.hotel;

import android.graphics.Bitmap;

/**
 * Created by Gard on 25.05.2017.
 */

public class Weather {
    private Double temperature;
    private String description;
    private Double rain;
    private Bitmap image;

    public Weather() {
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
