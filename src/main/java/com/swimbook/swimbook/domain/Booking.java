/*
 * Association class, represents a booking of a swim by an angler or bailiff
 * bare bones class, attributes and getters and setters
 */
package com.swimbook.swimbook.domain;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author luke
 */
@Entity
public final class Booking {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long bookingID;

    @ManyToOne
    private Swim swim; //TODO this attribute should be final

    @ManyToOne
    private Person bookingSubject;


    private Interval bookingDuration;


    @OneToMany
    private Set<SwimReport> reports;


    /**
     * Constructor using Interval
     * @param swim Swim, the swim the booking is made for
     * @param bookingSubject Person, the angler the booking is made for
     * @param bookingDuration Interval, the date and time interval for the booking
     */
    public Booking(Swim swim, Person bookingSubject, Interval bookingDuration) {
        this.swim = swim;
        this.bookingSubject = bookingSubject;
        this.bookingDuration = bookingDuration;
        this.reports = new HashSet<SwimReport>();
    }

    public Booking() {
        this.reports = new HashSet<>();
    }

    /**
     * Constructor for use when interval instance has not been created
     * @param swim Swim, the swim the booking is made for
     * @param bookingSubject Person, the angler the booking is made for
     * @param start DateTime, the start date and time of the booking
     * @param end DateTime, the end date and time of the booking
     */
    public Booking(Swim swim, Person bookingSubject, DateTime start, DateTime end)
            throws IllegalArgumentException {
        this.swim = swim;
        this.bookingSubject = bookingSubject;
        this.reports = new HashSet<SwimReport>();

        if((start.isAfterNow()) & (start.isBefore(end))) {
            this.bookingDuration = new Interval(start, end);
        } else {
            throw new IllegalArgumentException("Bookings can only be made for future dates");
        }
    }

    public Collection<SwimReport> getReports() {
        return reports;
    }

    public void addSwimReport(SwimReport aReport) {
        this.reports.add(aReport);
    }

    public float getBookingID() {
        return bookingID;
    }


    public Swim getSwim() {
        return swim;
    }

    public Person getBookingSubject() {
        return this.bookingSubject;
    }

    public Interval getBookingDuration() {
        return this.bookingDuration;
    }





}
