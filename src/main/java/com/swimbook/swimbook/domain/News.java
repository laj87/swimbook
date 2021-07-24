package com.swimbook.swimbook.domain;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class News {

    @Id
    private int newsID;
    private String title;
    private DateTime uploadDate;
    private String newsContent;


}
