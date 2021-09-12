package com.swimbook.swimbook.adapter.out;

import com.swimbook.swimbook.adapter.in.RequiredModel;
import com.swimbook.swimbook.application.services.CreateFishingVenue;
import com.swimbook.swimbook.domain.News;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommitDomainAdapterTest {

    //these variables are mocked to increase testing speed
    @Mock
    private SpringDataFishingVenueRepo fishingVenueRepo;
    @Mock
    private SpringDataBookingRepo bookingRepo;
    @Mock
    private SpringDataSwimRepo swimRepo;
    @Mock
    private SpringDataAnglerRepo anglerRepo;
    @Mock
    private SpringDataSwimReportRepo swimReportRepo;
    @Mock
    private SpringDataStatusRepo statusRepo;
    @Mock
    private SpringDataNewsRepo newsRepo;
    @Mock
    private SpringDataBailiffRepo bailiffRepo;

    //these components are required in order to test the adatper
    private CommitDomainAdapter commitDomainAdapter;
    private News news;
    private RequiredModel requiredModel;

    @BeforeEach
    void setUp() {

        //create a new instance of the adapter using the mocked repo beans
        commitDomainAdapter = new CommitDomainAdapter(fishingVenueRepo,
                bookingRepo,
                swimRepo,
                anglerRepo,
                swimReportRepo,
                statusRepo,
                newsRepo,
                bailiffRepo);

        //create an entity to commit to the repository and store in RequiredModel
        news = new News(
                "aTitle",
                new DateTime(2021,
                        9,01,12,21),
                "some content");
        requiredModel = new RequiredModel();
        requiredModel.setNews(news);

    }

    @Test
    void ensureEntitySentToRepo() {

        //method under test
        commitDomainAdapter.commitDomainModel(requiredModel);

        //grabs the argument sent to the NewsRepo bean, ensures the type is News
        ArgumentCaptor<News> commitDomainArgCaptor = ArgumentCaptor.forClass(News.class);

        //verifies the correct method is called from the mocked bean
        verify(newsRepo).save(commitDomainArgCaptor.capture());

        //grabs the arguement sent to the bean
        News capturedNews = commitDomainArgCaptor.getValue();

        //asserts that the captured argument is equal to the instantiated news entity
        assert capturedNews == news;
    }
}