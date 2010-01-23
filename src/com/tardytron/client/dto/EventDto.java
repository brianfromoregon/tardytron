package com.tardytron.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.Date;

public class EventDto implements IsSerializable {
    private long id;
    private boolean twoDayReminder, twoWeekReminder;
    private int year, month, day;
    private int daysLeft;
    private String name;

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasTwoDayReminder() {
        return twoDayReminder;
    }

    public void setTwoDayReminder(boolean twoDayReminder) {
        this.twoDayReminder = twoDayReminder;
    }

    public boolean hasTwoWeekReminder() {
        return twoWeekReminder;
    }

    public void setTwoWeekReminder(boolean twoWeekReminder) {
        this.twoWeekReminder = twoWeekReminder;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
