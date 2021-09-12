package com.swimbook.swimbook.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Fishing_Venue")
public final class FishingVenue {

    //Primary key attribute for the data persistence
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String venueName;

    @Column
    private String venueType;

    @Column
    private String venueSpecificRules;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) //ensures all the associated swim instances are received
    @JoinColumn(name = "status_id", referencedColumnName = "statusID")
    private Status status;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<News> news;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Swim> swims;


    /**
     * Main constructor.  Instantiates a Status object to be held within Status
     */
    public FishingVenue(String name, String type, String rules) {
        this.venueName = name;
        this.venueType = type;
        this.venueSpecificRules = rules;
        this.status = new Status();
        this.swims = new ArrayList<>();
        this.news = new HashSet<>();
    }

    /**
     * Used by JPA to create instances
     */
    public FishingVenue() {}

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueType() {
        return venueType;
    }

    public void setVenueType(String venueType) {
        this.venueType = venueType;
    }

    public String getVenueSpecificRules() {
        return venueSpecificRules;
    }

    public void setVenueSpecificRules(String venueSpecificRules) {
        this.venueSpecificRules = venueSpecificRules;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addSwim(Swim aSwim) {
        this.swims.add(aSwim);
    }

    public void setSwims(List<Swim> theSwims) {
        this.swims = theSwims;
    }

    public List<Swim> getSwims(){
        return this.swims;
    }

    public boolean isVenueOpen() {
        return this.getStatus().getStatus();
    }

    public void addNews(News news) {
        this.news.add(news);
    }
}
