/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swimbook.swimbook.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.*;

/**
 *
 * @author luke
 */

@Entity
public class Bailiff extends Person {

    @ManyToMany
    private Collection<FishingVenue> venues;
    private int staffID;

    public Bailiff(){
        super();
        this.venues = new HashSet<FishingVenue>();
    }

    public Collection<FishingVenue> getVenues() {
        return venues;
    }

    public void setVenues(Collection<FishingVenue> venues) {
        this.venues = venues;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

}
