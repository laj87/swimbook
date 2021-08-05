package com.swimbook.swimbook.application.services;

import com.swimbook.swimbook.adapter.in.RequiredModel;
import com.swimbook.swimbook.application.port.in.SetVenueAvailabilityPort;
import com.swimbook.swimbook.application.port.out.CommitDomainModel;
import com.swimbook.swimbook.domain.FishingVenue;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetVenueAvailability implements SetVenueAvailabilityPort {

    private final CommitDomainModel commitDomainModel;

    @Autowired
    public SetVenueAvailability(CommitDomainModel commitDomainModel) {
        this.commitDomainModel = commitDomainModel;
    }

    @Override
    public void execute(RequiredModel requiredModel) {
        FishingVenue theVenue = requiredModel.getFishingVenues().get(0);

        theVenue.getStatus().setStatusOpen();

        RequiredModel returnModel = this.commitDomainModel.requestEmptyRequiredModel();
        returnModel.setFishingVenues(theVenue);
        this.commitDomainModel.commitDomainModel(returnModel);

    }

    //TODO - change this method to boolean return - easier for REST to check and act accordingly
    @Override
    public void execute(String startDateTime, String endDateTime, String reason, RequiredModel requiredModel) {

        FishingVenue theVenue = requiredModel.getFishingVenues().get(0);
        Interval duration = new Interval(CreateDateTimeFromString.stringToDateTime(startDateTime),
                CreateDateTimeFromString.stringToDateTime(endDateTime));

        if(!duration.isAfterNow()) {
            throw new IllegalArgumentException("closed duration must be in the future");
        }

        theVenue.getStatus().setStatusClosed(duration, reason);

        RequiredModel returnModel = this.commitDomainModel.requestEmptyRequiredModel();
        returnModel.setFishingVenues(theVenue);
        this.commitDomainModel.commitDomainModel(returnModel);


    }
}
