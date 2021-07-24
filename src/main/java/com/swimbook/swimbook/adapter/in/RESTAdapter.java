package com.swimbook.swimbook.adapter.in;

import com.google.gson.Gson;
import com.swimbook.swimbook.application.port.in.*;
import com.swimbook.swimbook.domain.Swim;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RESTAdapter {

    private Gson jsonAdapter;
    private RequiredModelAdapter requiredModelAdapter;
    private CreateFishingVenuePort createFishingVenuePort;
    private GetAvailableSwimsPort getAvailableSwimsPort;
    private BookAvailableSwimPort bookAvailableSwimPort;
    private AddSwimReportPort addSwimReportPort;

    @Autowired
    public RESTAdapter(Gson jsonAdapter,
                       RequiredModelAdapter requiredModelAdapter,
                       CreateFishingVenuePort createFishingVenuePort,
                       GetAvailableSwimsPort getAvailableSwimsPort,
                       BookAvailableSwimPort bookAvailableSwimPort,
                       AddSwimReportPort addSwimReportPort) {
        this.jsonAdapter = jsonAdapter;
        this.requiredModelAdapter = requiredModelAdapter;
        this.createFishingVenuePort = createFishingVenuePort;
        this.getAvailableSwimsPort = getAvailableSwimsPort;
        this.bookAvailableSwimPort = bookAvailableSwimPort;
        this.addSwimReportPort = addSwimReportPort;
    }

    //TODO add instantiate method

    /**
     * Creates a new fishing venue within the societies system
     * @param name the venue name - must be unique
     * @param type the type of venue //TODO this should be an enum
     * @param rules the rules of the venue
     */
    @PostMapping("/addvenue/{venuename}/{venuetype}/{venuerules")
    void addVenue(
            @PathVariable("venuename") String name,
            @PathVariable("venuetype") String type,
            @PathVariable("venuerules") String rules) {

        createFishingVenuePort.execute(name, type, rules, new RequiredModel(requiredModelAdapter.
                findFishingVenueByVenueName(name)));
    }

    /**
     * Returns collection of available swims for a given venue and a given date and time
     * @param venueName unique venue name
     * @param date in format YYYYMMDD
     * @param time in format HHMM
     */
    @GetMapping("/getAvailableSwims/{venuename}/{date}/{time}")
    String browseAvailableSwims(
            @PathVariable("venuename") String venueName,
            @PathVariable("date") String date,
            @PathVariable("time") String time) {
        List<Swim> swims = getAvailableSwimsPort.execute(
                date, time, requiredModelAdapter.getAvailableSwims(venueName));

        return this.jsonAdapter.toJson(swims);
    }

    @PostMapping("/bookswim/{anglerid}/{swim}/{startdatetime}/{enddatetime}")
    void bookSwim(
            @PathVariable("anglerid") int anglerID,
            @PathVariable("swim") int swimID,
            @PathVariable("startdatetime") String startDateTime,
            @PathVariable("enddatetime") String endDateTime) {
        bookAvailableSwimPort.execute(startDateTime,endDateTime,
                requiredModelAdapter.getBookSwimModel(anglerID, swimID));
    }

    @PostMapping("/addswimreport/{personid}/{bookingid}/{bait}/{castingdistance}/{castingdirection}/" +
            "{method}/{comments}") void addSwimReport(
                    @PathVariable("personid") int personID,
                    @PathVariable("bookingid") int bookingID,
                    @PathVariable("bait") String bait,
                    @PathVariable("castingdistance") String castingDistance,
                    @PathVariable("castingdirection") String castingDirection,
                    @PathVariable("method") String method,
                    @PathVariable("comments") String comments) {
        addSwimReportPort.execute(bait, castingDistance, castingDirection, method, comments,
                requiredModelAdapter.getSwimReportModel(personID, bookingID));
    }


}
