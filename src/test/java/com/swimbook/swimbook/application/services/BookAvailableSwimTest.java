package com.swimbook.swimbook.application.services;

import com.swimbook.swimbook.adapter.in.RequiredModel;
import com.swimbook.swimbook.application.port.out.CommitDomainModel;
import com.swimbook.swimbook.domain.Angler;
import com.swimbook.swimbook.domain.Booking;
import com.swimbook.swimbook.domain.Swim;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookAvailableSwimTest {

    @Mock
    private CommitDomainModel commitDomainModel;
    private RequiredModel testModel = new RequiredModel();
    private BookAvailableSwim bookAvailableSwim;

    @BeforeEach
    void setUp() {
        //set up the test subject
        bookAvailableSwim = new BookAvailableSwim(commitDomainModel);
    }

    @Test
    void checkNewBookingMadeWithSuccessScenario() {
        //set up the fixture for scenario
        Swim swim = new Swim(1, "lake", 2);
        Angler angler = new Angler();
        DateTime startDateTime = new DateTime(2021,9,1,6,0);
        DateTime endDateTime = new DateTime(2021,9,1,18,0);
        Booking preBooked = new Booking(swim, angler, startDateTime, endDateTime);

        //assign fixture to the model container
        testModel.setSwims(swim);
        testModel.setAnglers(angler);
        testModel.setBookings(preBooked);

        //should be true if new booking instance made
        assert bookAvailableSwim.execute("202109070600", "202109071800", testModel);

        //ensure correct modeling has been carried out
        //swim should contain 2 booking objects, one from the fixture and the newly
        //created booking
        assert swim.getBookings().size() == 2;

        //the angler should only contain the newly created booking
        assert angler.getBookings().size() == 1;

    }
}