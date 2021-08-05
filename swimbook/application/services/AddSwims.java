package com.swimbook.swimbook.application.services;

import com.swimbook.swimbook.adapter.in.RequiredModel;
import com.swimbook.swimbook.application.port.in.AddSwimsPort;
import com.swimbook.swimbook.application.port.out.CommitDomainModel;
import com.swimbook.swimbook.domain.FishingVenue;
import com.swimbook.swimbook.domain.Swim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddSwims implements AddSwimsPort {

    private final CommitDomainModel commitDomainModel;

    @Autowired
    public AddSwims(CommitDomainModel commitDomainModel) {
        this.commitDomainModel = commitDomainModel;
    }

    /**
     *
     * @param type
     * @param capacity
     * @param swimInfo
     * @param requiredModel
     */
    public void execute(String type, int capacity, String swimInfo, RequiredModel requiredModel) {

        //TODO add swim name and data validation check here - for unique name
        FishingVenue venue = requiredModel.getFishingVenues().get(0);

        Swim theSwim = new Swim(type, capacity, swimInfo, venue);

        RequiredModel returnModel = this.commitDomainModel.requestEmptyRequiredModel();
        returnModel.setFishingVenues(venue);
        returnModel.setSwims(theSwim);
    }
}
