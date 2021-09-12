package com.swimbook.swimbook.application.services;

import com.swimbook.swimbook.adapter.in.RequiredModel;
import com.swimbook.swimbook.application.port.in.SetSwimAvailabilityPort;
import com.swimbook.swimbook.application.port.out.CommitDomainModel;
import com.swimbook.swimbook.domain.Swim;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetSwimAvailability implements SetSwimAvailabilityPort {

    private final CommitDomainModel commitDomainModel;


    @Autowired
    public SetSwimAvailability(CommitDomainModel commitDomainModel) {
        this.commitDomainModel = commitDomainModel;
    }

    @Override
    public void execute(RequiredModel requiredModel) {
        Swim theSwim = requiredModel.getSwims().get(0);
        theSwim.getSwimStatus().setStatusOpen();

        RequiredModel returnModel = this.commitDomainModel.requestEmptyRequiredModel();
        returnModel.setSwims(theSwim);
    }

    @Override
    public void execute(String startDateTime,
                        String endDateTime,
                        String reason,
                        RequiredModel requiredModel) {

        Swim theSwim = requiredModel.getSwims().get(0);

        Interval closedInterval = new Interval(CreateDateTimeFromString.stringToDateTime(startDateTime),
                CreateDateTimeFromString.stringToDateTime(endDateTime));

        if(!closedInterval.isAfterNow()) {
            throw new IllegalArgumentException("closed duration must be in the future");
        }

        theSwim.getSwimStatus().setStatusClosed(closedInterval, reason);

        RequiredModel returnModel = this.commitDomainModel.requestEmptyRequiredModel();
        returnModel.setSwims(theSwim);
        this.commitDomainModel.commitDomainModel(returnModel);

    }
}
