package com.swimbook.swimbook.application.services;

import com.swimbook.swimbook.adapter.in.RequiredModel;
import com.swimbook.swimbook.application.port.in.EditVenuePort;
import com.swimbook.swimbook.application.port.out.CommitDomainModel;
import com.swimbook.swimbook.domain.FishingVenue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditVenue implements EditVenuePort {

    private final CommitDomainModel commitDomainModel;

    @Autowired
    public EditVenue(CommitDomainModel commitDomainModel) {
        this.commitDomainModel = commitDomainModel;
    }

    public void execute(String newName, String newType, String newRules, RequiredModel requiredModel) {

        FishingVenue theVenue = requiredModel.getFishingVenues().get(0);

        if(!newName.equals(theVenue.getVenueName())) {
            FishingVenue newVenue = new FishingVenue(newName, newType, newRules);
            newVenue.setSwims(theVenue.getSwims());
            newVenue.setStatus(theVenue.getStatus());
            this.commitChanges(newVenue);
        } else {
            theVenue.setVenueType(newType);
            theVenue.setVenueSpecificRules(newRules);
            this.commitChanges(theVenue);
        }
    }

    private void commitChanges(FishingVenue venue) {
        RequiredModel returnModel = this.commitDomainModel.requestEmptyRequiredModel();
        returnModel.setFishingVenues(venue);
        this.commitDomainModel.commitDomainModel(returnModel);
    }
}
