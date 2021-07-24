package com.swimbook.swimbook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class SwimReport {

    @Id
    @GeneratedValue
    private int reportNum;
    private String bait;
    private String castingDistance;
    private String castingDirection;
    private String method;
    private String comments;

    public SwimReport(String bait, String castingDistance, String castingDirection, String method, String comments) {
        this.bait = bait;
        this.castingDirection = castingDirection;
        this.castingDistance = castingDistance;
        this.method = method;
        this.comments = comments;
    }
}
