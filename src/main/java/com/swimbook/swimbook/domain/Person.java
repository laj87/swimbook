
package com.swimbook.swimbook.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author luke
 */
@Entity
public abstract class Person {

    @Id
    private int membershipID;
    private String name;
    private String address;
    private String email;
    private int telephoneNumber;

    public int getMembershipID() {
        return membershipID;
    }

    public void setMembershipID(int membershipID) {
        this.membershipID = membershipID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setTelephoneNumber(int telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public int getTelephoneNumber() {
        return this.telephoneNumber;
    }
}


