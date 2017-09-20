package com.colpencil.tenement.Bean;

import java.io.Serializable;

public class Feedback implements Serializable {

    private int rating;
    private String descrpition;
    private String rateTime;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescrpition() {
        return descrpition;
    }

    public void setDescrpition(String descrpition) {
        this.descrpition = descrpition;
    }

    public String getRateTime() {
        return rateTime;
    }

    public void setRateTime(String rateTime) {
        this.rateTime = rateTime;
    }
}
