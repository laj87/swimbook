package com.swimbook.swimbook.domain;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.joda.time.Interval;
import org.joda.time.DateTime;
import javax.persistence.*;



@Getter
@Setter
@Entity
public final class Status {


    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer statusID;

    @Column
    private Boolean status;

    @Column
    private Interval closedDuration;

    @Column
    private String reason;


    /**
     * Status constructor to be used when a new Swim or FishingVenue instance is created.  Status must always
     * be true for a newly created Status instance
     */
    public Status() {
        this.status = Boolean.TRUE;
    }

    /**
     * To set status to FALSE closedDuration and reason must also be set
     * @param start DateTime indicates the start of the closed period. Must be before end and after current Date
     * @param end DateTime indicates the end of the closed period. Must be after start
     * @param reason String explains reason for closure
     * @return Boolean TRUE if successful, FALSE otherwise
     */
    @NotNull
    public void setStatusClosed(DateTime start, DateTime end, String reason)
    throws IllegalArgumentException {
        //check start is before end before setting
        if((start.isAfterNow()) & (start.isBefore(end))) {
            this.reason = reason;
            this.closedDuration = new Interval(start, end);
        } else {
            throw new IllegalArgumentException("Closed duration cannot be in the past");
        }
    }

    /**
     * Sets status to closed, uses an interval instance rather than datetimes
     *
     * @throws IllegalArgumentException
     */
    public void setStatusClosed(Interval duration, String reason) throws IllegalArgumentException {
        if(duration.isAfterNow()) {
            this.status = Boolean.FALSE;
            this.closedDuration = duration;
            this.reason = reason;
        } else {
            throw new IllegalArgumentException("Closed duration cannot be in the past");
        }
    }

    /**
     * sets open status to TRUE and sets closedDuration and reason to null
     */
    public void setStatusOpen(){
        this.status = Boolean.TRUE;
        this.closedDuration = null;
        this.reason = null;
    }

}
