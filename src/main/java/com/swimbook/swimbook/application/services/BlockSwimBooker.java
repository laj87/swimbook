package com.swimbook.swimbook.application.services;

import com.swimbook.swimbook.adapter.in.RequiredModel;
import com.swimbook.swimbook.application.port.in.BlockSwimBookerPort;
import com.swimbook.swimbook.application.port.out.CommitDomainModel;
import com.swimbook.swimbook.domain.Bailiff;
import com.swimbook.swimbook.domain.Booking;
import com.swimbook.swimbook.domain.Swim;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlockSwimBooker implements BlockSwimBookerPort {

    private CommitDomainModel commitDomainModel;

    @Autowired
    public BlockSwimBooker(CommitDomainModel commitDomainModel) {
        this.commitDomainModel = commitDomainModel;
    }


    @Override
    public void execute(String startDateTime, String endDateTime, RequiredModel requiredModel) {
        Interval requestedInterval = new Interval(CreateDateTimeFromString.stringToDateTime(startDateTime),
                CreateDateTimeFromString.stringToDateTime(endDateTime));

        if(!requestedInterval.isAfterNow()) {
            throw new IllegalArgumentException("booking duration must be after current date and time");
        }

        Bailiff booker = requiredModel.getBailiffs().get(0);

        //NOTE TO SELF - FOR THIS USE CASE NO BOOKING SUBJECT WILL HOLD THE REFERENCE TO
        //THE BOOKING OBJECT - THIS NEEDS TO BE RECORDED SOMEWHERE
        List<Booking> newBookings = new ArrayList<>();
        List<Swim> bookingSubjects = requiredModel.getSwims();
        for(Swim eachSwim : bookingSubjects) {
            newBookings.add(new Booking(eachSwim, booker, requestedInterval));
        }

        RequiredModel returnModel = this.commitDomainModel.requestEmptyRequiredModel();
        returnModel.setBookings(newBookings);
        returnModel.setSwims(bookingSubjects);
        this.commitDomainModel.commitDomainModel(returnModel);

    }
}
