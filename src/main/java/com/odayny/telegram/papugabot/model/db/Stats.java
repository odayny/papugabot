package com.odayny.telegram.papugabot.model.db;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Stats {
    @Id
    private String id;
    private String userId;
    private Date date;

    public Stats(String userId) {
        this.userId = userId;
        this.date = new Date();
    }

    public Stats() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
