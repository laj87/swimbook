package com.swimbook.swimbook.domain;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class News {

    @Id
    @GeneratedValue
    private int newsID;
    private String title;
    private DateTime uploadDate;
    private String newsContent;

    public News() {

    }

    public News(String title, DateTime uploadDate, String newsContent) {
        this.title = title;
        this.uploadDate = uploadDate;
        this.newsContent = newsContent;
    }

    public News(String title, DateTime uploadDate, String newsContent, FishingVenue venue) {
        this.title = title;
        this.uploadDate = uploadDate;
        this.newsContent = newsContent;
        venue.addNews(this);
    }

}
