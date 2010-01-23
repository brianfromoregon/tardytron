package com.tardytron.server.jdo;

import com.google.appengine.api.users.User;
import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.LocalDate;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Event {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    @Persistent(defaultFetchGroup = "true")
    private User user;
    @Persistent
    private String name;
    @Persistent
    private boolean twoWeekReminder;
    @Persistent
    private boolean twoDayReminder;
    @Persistent
    private int year;
    @Persistent
    private int month;
    @Persistent
    private int day;
    // Denormalized for app engine query ease
    @Persistent
    private int tzOffsetMinutes;
    // Denormalized and precomputed for app engine query ease
    @Persistent
    private Date year2000UtcDateTime;

    public DateTimeZone getTimeZone() {
        return DateTimeZone.forOffsetMillis(-tzOffsetMinutes * 60 * 1000);
    }

    public DateTime getDateTime() {
        return new LocalDate(getYear(), getMonth(), getDay()).toDateTimeAtStartOfDay(getTimeZone());
    }

    public int daysLeft() {
        DateTime yesterday = new DateTime(getTimeZone()).minusDays(1);
        DateTime nextEventDate = nextAnniversary();
        return Days.daysBetween(yesterday, nextEventDate).getDays();
    }

    public DateTime nextAnniversary() {
        DateTime yesterday = new DateTime(getTimeZone()).minusDays(1);
        DateTime nextEventDate = getDateTime().withYear(yesterday.getYear());
        while (nextEventDate.isBefore(yesterday)) {
            nextEventDate = nextEventDate.plusYears(1);
        }
        return nextEventDate;
    }

    public int nextAnniversaryAge() {
        return nextAnniversary().getYear() - getDateTime().getYear();
    }

    // To be called by DAO only
    void updateTzOffset(int tzOffsetMinutes) {
        this.tzOffsetMinutes = tzOffsetMinutes;
        year2000UtcDateTime = new LocalDate(getYear(), getMonth(), getDay()).withYear(2000).toDateMidnight(getTimeZone()).toDate();
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Long getId() {
        return id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
