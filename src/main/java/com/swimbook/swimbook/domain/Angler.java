/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swimbook.swimbook.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.*;

/**
 *
 * @author luke
 */
@Entity
public class Angler extends Person {

    @OneToMany(fetch = FetchType.EAGER)
    private Collection<Booking> bookings;

    @OneToMany
    private Collection<SwimReport> swimReports;

    /**
     * check person constructor
     */
    public Angler() {
        super();
        this.bookings = new HashSet<Booking>();
        this.swimReports = new HashSet<SwimReport>();
    }

    public Collection<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Collection<Booking> bookings) {
        this.bookings = bookings;
    }

    public Collection<SwimReport> getSwimReports() {
        return swimReports;
    }

    public void setSwimReports(Collection<SwimReport> swimReports) {
        this.swimReports = swimReports;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    public void addSwimReport(SwimReport swimReport) {
        this.swimReports.add(swimReport);
    }


}
