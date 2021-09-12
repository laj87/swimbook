package com.swimbook.swimbook.application.services;

import com.swimbook.swimbook.adapter.in.RequiredModel;
import com.swimbook.swimbook.application.port.out.CommitDomainModel;
import com.swimbook.swimbook.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class CreateFishingVenueTest {


    private CreateFishingVenue createFishingVenue;
    private RequiredModel testModel = new RequiredModel();
    private FishingVenue testVenue = new FishingVenue("testVenue", "testType", "testRules");
    @Mock private CommitDomainModel commitDomainModel;

    @BeforeEach
    void setUp() {
        createFishingVenue = new CreateFishingVenue(commitDomainModel);
        testModel.setFishingVenues(testVenue);
    }


    @Test
    void checkNewVenueMadeWhenNameIsUnique() {

        //when
        createFishingVenue.execute("blackwater", "river", "astring", testModel);

        //then
        verify(commitDomainModel).commitDomainModel(testModel);
    }

    @Test
    void checkNewVenueNotMadeWhenNameIsTaken() {
        Exception e  = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            createFishingVenue.execute("testVenue", "testType", "testRules", testModel);
        });

        String expectedMessage = "That venue name is not unique";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}