package com.swimbook.swimbook.adapter.out;

import com.swimbook.swimbook.adapter.in.RequiredModel;
import com.swimbook.swimbook.application.port.out.CommitDomainModel;
import com.swimbook.swimbook.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CommitDomainAdapter implements CommitDomainModel {

    private final SpringDataFishingVenueRepo fishingVenueRepo;
    private final SpringDataBookingRepo bookingRepo;
    private final SpringDataSwimRepo swimRepo;
    private final SpringDataAnglerRepo anglerRepo;
    private final SpringDataSwimReportRepo swimReportRepo;
    private final SpringDataStatusRepo statusRepo;
    private final SpringDataNewsRepo newsRepo;
    private final SpringDataBailiffRepo bailiffRepo;


    @Autowired
    public CommitDomainAdapter(SpringDataFishingVenueRepo fishingVenueRepo,
                               SpringDataBookingRepo bookingRepo,
                               SpringDataSwimRepo swimRepo,
                               SpringDataAnglerRepo anglerRepo,
                               SpringDataSwimReportRepo swimReportRepo,
                               SpringDataStatusRepo statusRepo,
                               SpringDataNewsRepo newsRepo,
                               SpringDataBailiffRepo bailiffRepo) {
        this.fishingVenueRepo = fishingVenueRepo;
        this.bookingRepo = bookingRepo;
        this.swimRepo = swimRepo;
        this.anglerRepo = anglerRepo;
        this.swimReportRepo = swimReportRepo;
        this.statusRepo = statusRepo;
        this.newsRepo = newsRepo;
        this.bailiffRepo = bailiffRepo;
    }

    /**
     * NOTE TO SELF - there must be a better way of dealing with this!
     * Checks each instance variable within requiredModel and commits to repo if not null
     */
    @Override
    public void commitDomainModel(RequiredModel requiredModel) {

        //check for FishingVenues
        if(requiredModel.getFishingVenues() != null) {
            for(FishingVenue venue: requiredModel.getFishingVenues()) {
                fishingVenueRepo.save(venue);
            }
        }

        //Check for Swims
        if(requiredModel.getSwims() != null) {
            for(Swim swim: requiredModel.getSwims()) {
                swimRepo.save(swim);
            }
        }

        //Check for Bookings
        if(requiredModel.getBookings() != null) {
            for(Booking booking: requiredModel.getBookings()) {
                bookingRepo.save(booking);
            }
        }

        //Check for Status
        if(requiredModel.getStatuses() != null) {
            for(Status status: requiredModel.getStatuses()) {
                statusRepo.save(status);
            }
        }

        //Check for News
        if(requiredModel.getNews() != null) {
            for(News news: requiredModel.getNews()) {
                newsRepo.save(news);
            }
        }

        //Check for Angler
        if(requiredModel.getAnglers() != null) {
            for(Angler angler: requiredModel.getAnglers()) {
                anglerRepo.save(angler);
            }
        }

        //Check for Bailiff
        if(requiredModel.getBailiffs() != null) {
            for(Bailiff bailiff: requiredModel.getBailiffs()) {
                bailiffRepo.save(bailiff);
            }
        }

        //Check for SwimReport
        if(requiredModel.getSwimReports() != null) {
            for(SwimReport swimReport: requiredModel.getSwimReports()) {
                swimReportRepo.save(swimReport);
            }
        }

    }

    /**
     * creates a new empty RequiredModel instance
     * @return RequiredModel instance
     */
    public RequiredModel requestEmptyRequiredModel() {
        return new RequiredModel();
    }

}
