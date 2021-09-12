package com.swimbook.swimbook.application.services;

import com.swimbook.swimbook.adapter.in.RequiredModel;
import com.swimbook.swimbook.application.port.in.AddNewsPort;
import com.swimbook.swimbook.application.port.out.CommitDomainModel;
import com.swimbook.swimbook.domain.FishingVenue;
import com.swimbook.swimbook.domain.News;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;


@Service
public class AddNews implements AddNewsPort {

    private final CommitDomainModel commitDomainModel;

    @Autowired
    public AddNews(CommitDomainModel commitDomainModel) {
        this.commitDomainModel = commitDomainModel;
    }

    @Override
    public void execute(String title, String uploadDate,
                        String content, RequiredModel requiredmodel) {

        FishingVenue venue = requiredmodel.getFishingVenues().get(0);
        org.joda.time.DateTime newsDate = CreateDateTimeFromString.stringToDateTime(uploadDate);

        News newsItem = new News(title, newsDate, content, venue);

        RequiredModel returnModel = this.commitDomainModel.requestEmptyRequiredModel();
        returnModel.setFishingVenues(venue);
        returnModel.setNews(newsItem);
        this.commitDomainModel.commitDomainModel(returnModel);
    }
}
