package com.swimbook.swimbook.application.services;

import com.swimbook.swimbook.adapter.in.RequiredModel;
import com.swimbook.swimbook.application.port.in.AddSwimReportPort;
import com.swimbook.swimbook.application.port.out.CommitDomainModel;
import com.swimbook.swimbook.domain.Angler;
import com.swimbook.swimbook.domain.Booking;
import com.swimbook.swimbook.domain.SwimReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddSwimReport implements AddSwimReportPort {

    private final CommitDomainModel commitDomainModel;

    @Autowired
    public AddSwimReport(CommitDomainModel commitDomainModel) {
        this.commitDomainModel = commitDomainModel;
    }

    @Override
    public Boolean execute(String bait, String castingDistance, String castingDirection, String method,
                    String comments, RequiredModel requiredModel) throws IllegalArgumentException {

        Angler angler = requiredModel.getAnglers().get(0);
        Booking booking = requiredModel.getBookings().get(0);

        if(!angler.getBookings().contains(booking)) {
            throw new IllegalArgumentException(
                    "That booking is not associated with the current angler");
        }

        SwimReport swimReport = new SwimReport(bait, castingDistance, castingDirection, method, comments);

        angler.addSwimReport(swimReport);
        booking.addSwimReport(swimReport);

        RequiredModel returnModel = this.createModelContainer(angler, booking, swimReport);
        this.commitDomainModel.commitDomainModel(returnModel);

        return Boolean.TRUE;
    }

    /**
     * Helper method for setting up return model container
     */
    private RequiredModel createModelContainer(Angler angler, Booking booking, SwimReport swimReport) {
        List<Angler> anglers = new ArrayList<>();
        List<Booking> bookings = new ArrayList<>();
        List<SwimReport> swimReports = new ArrayList<>();

        anglers.add(angler);
        bookings.add(booking);
        swimReports.add(swimReport);

        RequiredModel returnModel = this.commitDomainModel.requestEmptyRequiredModel();
        returnModel.setAnglers(anglers);
        returnModel.setBookings(bookings);
        returnModel.setSwimReports(swimReports);

        return returnModel;

    }
}
