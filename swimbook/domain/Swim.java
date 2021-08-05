/**
 * Represents a swim on a fishing venue, each object is a unique swim
 **/
package com.swimbook.swimbook.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luke
 */

@Entity
public final class Swim {

    @Id
    @GeneratedValue
    private Integer swimID;
    private String type;
    private int capacity;
    private String swimInfo;

    @OneToOne
    private Status swimStatus;

    @OneToMany
    private List<Booking> bookings;

    /**
     * Included as it has a 1 to 1 relationship with Status.  No Swim instances should exist without
     * a Status instance
     */
    public Swim() {
        this.swimStatus = new Status();
        this.bookings = new ArrayList<Booking>();
    }

    /**
     * Sets new Swim instance as a swim within venue
     * @param type
     * @param capacity
     * @param swimInfo
     */
    public Swim(String type,
                int capacity,
                String swimInfo,
                @org.jetbrains.annotations.NotNull FishingVenue venue) {
        this.type = type;
        this.capacity = capacity;
        this.swimInfo = swimInfo;
        this.swimStatus = new Status();
        this.bookings = new ArrayList<Booking>();
        venue.addSwim(this);
    }

    /**
     * For use within an assignment statement to a FishingVenue instance
     * @param swimID
     * @param type
     * @param capacity
     */
    public Swim(Integer swimID, String type, int capacity) {
        this.swimID = swimID;
        this.type = type;
        this.capacity = capacity;
        this.swimStatus = new Status();
        this.bookings = new ArrayList<Booking>();
    }

    public Integer getSwimID() {
        return swimID;
    }

    public void setSwimID(Integer swimID) {
        this.swimID = swimID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Status getSwimStatus() {
        return swimStatus;
    }

    public void setSwimStatus(){}

    /**
     * In place of setBookings
     * @param aBooking Booking the booking object associated with this swim
     * @return TRUE if successful
     */
    public Boolean addBooking(Booking aBooking) {
        return this.bookings.add(aBooking);
    }

    public List<Booking> getBookings() {
        return this.bookings;
    }

}
