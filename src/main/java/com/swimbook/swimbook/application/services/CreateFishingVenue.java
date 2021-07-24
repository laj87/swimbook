package com.swimbook.swimbook.application.services;

import com.swimbook.swimbook.application.port.in.CreateFishingVenuePort;
import com.swimbook.swimbook.adapter.in.RequiredModel;
import com.swimbook.swimbook.application.port.out.CommitDomainModel;
import com.swimbook.swimbook.domain.FishingVenue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateFishingVenue implements CreateFishingVenuePort {

    private final CommitDomainModel commitDomainModel;

    @Autowired
    public CreateFishingVenue(CommitDomainModel commitDomainModel) {
        this.commitDomainModel = commitDomainModel;
    }

    @Override
    public Boolean execute(String name, String type, String rules, RequiredModel requiredModel) throws IllegalArgumentException {

        List<FishingVenue> venues = requiredModel.getFishingVenues();

        //Check for name uniqueness
        for(FishingVenue venue : venues) {
            if (venue.getVenueName().equals(name)) {
                throw new IllegalArgumentException("That venue name is not unique");
            }
        }

        //Set up new model container
        RequiredModel returnModel = this.commitDomainModel.requestEmptyRequiredModel();
        ArrayList<FishingVenue> newVenue = new ArrayList<>();
        newVenue.add(new FishingVenue(name, type, rules));
        returnModel.setFishingVenues(newVenue);

        this.commitDomainModel.commitDomainModel(returnModel);

        return Boolean.TRUE;

    }
}
