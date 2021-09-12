package com.swimbook.swimbook.adapter.in;

import com.google.gson.Gson;
import com.swimbook.swimbook.application.port.in.*;
import com.swimbook.swimbook.domain.Swim;
import com.swimbook.swimbook.domain.SwimReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO add ResponseEntity returns to all these methods with correct HTTP status codes and messages

@RestController
public class RESTAdapter {

    private final Gson jsonAdapter;
    private final RequiredModelAdapter requiredModelAdapter;
    private final CreateFishingVenuePort createFishingVenuePort;
    private final GetAvailableSwimsPort getAvailableSwimsPort;
    private final BookAvailableSwimPort bookAvailableSwimPort;
    private final AddSwimReportPort addSwimReportPort;
    private final AddSwimsPort addSwimsPort;
    private final EditVenuePort editVenuePort;
    private final SetSwimAvailabilityPort setSwimAvailabilityPort;
    private final SetVenueAvailabilityPort setVenueAvailabilityPort;
    private final BlockSwimBookerPort blockSwimBookerPort;
    private final AddNewsPort addNewsPort;
    private final GetSwimReportsPort getSwimReportsPort;

    @Autowired
    public RESTAdapter(Gson jsonAdapter,
                       RequiredModelAdapter requiredModelAdapter,
                       CreateFishingVenuePort createFishingVenuePort,
                       GetAvailableSwimsPort getAvailableSwimsPort,
                       BookAvailableSwimPort bookAvailableSwimPort,
                       AddSwimReportPort addSwimReportPort,
                       AddSwimsPort addSwimsPort,
                       EditVenuePort editVenuePort,
                       SetSwimAvailabilityPort setSwimAvailabilityPort,
                       SetVenueAvailabilityPort setVenueAvailabilityPort,
                       BlockSwimBookerPort blockSwimBookerPort,
                       AddNewsPort addNewsPort,
                       GetSwimReportsPort getSwimReportsPort) {
        this.jsonAdapter = jsonAdapter;
        this.requiredModelAdapter = requiredModelAdapter;
        this.createFishingVenuePort = createFishingVenuePort;
        this.getAvailableSwimsPort = getAvailableSwimsPort;
        this.bookAvailableSwimPort = bookAvailableSwimPort;
        this.addSwimReportPort = addSwimReportPort;
        this.addSwimsPort = addSwimsPort;
        this.editVenuePort = editVenuePort;
        this.setSwimAvailabilityPort = setSwimAvailabilityPort;
        this.setVenueAvailabilityPort = setVenueAvailabilityPort;
        this.blockSwimBookerPort = blockSwimBookerPort;
        this.addNewsPort = addNewsPort;
        this.getSwimReportsPort = getSwimReportsPort;
    }

    //TODO add instantiate method

    @GetMapping("/instantiate/{membershipid}") ResponseEntity instantiate(
            @PathVariable("membershipid") int memberID) {
        try {
            RequiredModel requiredModel = this.requiredModelAdapter.getInstantiateModel(memberID);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(requiredModel.getFishingVenues());
        } catch(Exception e) {
            return ResponseEntity
                    .status(HttpStatus.PRECONDITION_FAILED)
                    .body("membership id unknown");
        }
    }

    /**
     * Creates a new fishing venue within the societies system
     * @param name the venue name - must be unique
     * @param type the type of venue //TODO this should be an enum
     * @param rules the rules of the venue
     */
    @PostMapping("/addvenue/{venuename}/{venuetype}/{venuerules}")
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
    ResponseEntity bookSwim(
            @PathVariable("anglerid") int anglerID,
            @PathVariable("swim") int swimID,
            @PathVariable("startdatetime") String startDateTime,
            @PathVariable("enddatetime") String endDateTime) {
        try {
            //make call within try statement to ensure a successful method call
            bookAvailableSwimPort.execute(startDateTime, endDateTime,
                    requiredModelAdapter.getBookSwimModel(anglerID, swimID));

            //create ReponseEntity informing user that a booking has been made
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Booking Successfully added to the system");
        } catch (Exception e) {
            //create a ResponseEntity informing user that a problem has occurred
            return ResponseEntity
                    .status(HttpStatus.PRECONDITION_FAILED)
                    .body(e.getMessage());
        }
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

    /**
     *
     * @param staffID
     * @param venueName
     * @param type
     * @param capacity
     * @param swimInfo
     * @return
     */
    @PostMapping("/addswim/{staffid}/{venuename}/{type}/{capacity}/{info}/")
    ResponseEntity addSwim(
            @PathVariable("staffid") int staffID,
            @PathVariable("venuename") String venueName,
            @PathVariable("type") String type,
            @PathVariable("capacity") int capacity,
            @PathVariable("info") String swimInfo) {

        try {
            RequiredModel requiredModel = requiredModelAdapter.getAddSwimsModel(staffID, venueName);
            addSwimsPort.execute(type, capacity, swimInfo, requiredModel);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("new swim added to the system");
        } catch(IllegalArgumentException exception) {
            return ResponseEntity
                    .status(HttpStatus.PRECONDITION_FAILED)
                    .body("staffID does not match any within the records");
        }

    }

    /**
     *Allows a bailiff to edit the attributes of a fishing venue instance.  Include
     * original values if no change is input by the user
     * @param staffID staffID of user
     * @param venueName current name of the fishing venue
     * @param newName new name of the fishing venue
     * @param newType new type of the fishing venue
     * @param newRules new rules of the fishing venue
     * @return status 200 if successful
     */
    @PutMapping("/editvenue/{staffid}/{venuename}/{newname}/{newtype}/{newrules}")
    ResponseEntity editVenue(
            @PathVariable("staffid") int staffID,
            @PathVariable("venuename") String venueName,
            @PathVariable("newname") String newName,
            @PathVariable("newtype") String newType,
            @PathVariable("newrules") String newRules) {
        try {
            RequiredModel requiredModel = requiredModelAdapter.getAddSwimsModel(staffID, venueName);
            editVenuePort.execute(newName, newType, newRules, requiredModel);
            //TODO- check for response from editVenuePort before constructing return
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Venue successfully updated");
        } catch(Exception e) {
            return ResponseEntity
                    .status(HttpStatus.PRECONDITION_FAILED)
                    .body("staffID is not in the system records");
        }

    }

    /**
     * Set swim availability
     * @param staffID
     * @param swimID
     * @param startDateTime
     * @param endDateTime
     * @param reason
     * @return
     */
    @PutMapping("/setswimavailability/{status}/{staffid}/{swimid}/{startdatetime}/" +
            "{enddatetime}/{reason}") ResponseEntity setSwimAvailabilityClosed(
                    @PathVariable("status") boolean status,
                    @PathVariable("staffid") int staffID,
                    @PathVariable("swimid") int swimID,
                    @PathVariable("startdatetime") String startDateTime,
                    @PathVariable("enddatetime") String endDateTime,
                    @PathVariable("reason") String reason) {
        try {
            if(status) {
                setSwimAvailabilityPort.execute(
                        requiredModelAdapter.getSetSwimAvailabilityModel(staffID, swimID));
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("swim availability set to open");
            }
            setSwimAvailabilityPort.execute(startDateTime, endDateTime, reason,
                    requiredModelAdapter.getSetSwimAvailabilityModel(staffID, swimID));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("swim availability set to closed beginning " + startDateTime);
        } catch(Exception e) {

            return ResponseEntity
                    .status(HttpStatus.PRECONDITION_FAILED)
                    .body("staffID is ont in the system records");
        }
    }

    /**
     * Change the availability of the venue to open or closed.  If changing to closed, the duration and
     * reason will also be added to the status
     * @param status
     * @param staffID
     * @param venueName
     * @param startDateTime
     * @param endDateTime
     * @param reason
     * @return
     */
    @PutMapping("/setvenueavailability/{status}/{staffid}/{venuename}/{startdatetime/" +
            "{enddatetime}/{reason}") ResponseEntity setVenueAvailability(
                    @PathVariable("status") boolean status,
                    @PathVariable("staffid") int staffID,
                    @PathVariable("venuename") String venueName,
                    @PathVariable("startdatetime") String startDateTime,
                    @PathVariable("enddatetime") String endDateTime,
                    @PathVariable("reason") String reason) {
        try {
            RequiredModel requiredModel = requiredModelAdapter.getAddSwimsModel(staffID, venueName);

            if(status) {
                this.setVenueAvailabilityPort.execute(requiredModel);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(venueName + " set to open");
            }
            this.setVenueAvailabilityPort.execute(startDateTime, endDateTime, reason, requiredModel);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(venueName + " set to closed");
        } catch(Exception e) {
            return ResponseEntity
                    .status(HttpStatus.PRECONDITION_FAILED)
                    .body("staffID not found in records");
        }
    }

    /**
     * Makes a block booking of identified swims for the same date and times
     * @param staffID staff ID for the bailiff making the booking
     * @param swimIDs ID codes for the booked swims
     * @param startDateTime start date and time of the bookings (YYYYMMDDhhmm)
     * @param endDateTime end date and time of the bookings (YYYYMMDDhhmm)
     * @return
     */
    @PostMapping("/blockbooking/{staffid}/{swimids}/{startdatetime}/{enddatetime}") ResponseEntity
    makeBlockBooking(
            @PathVariable("staffid") int staffID,
            @PathVariable("swimids") List<Integer> swimIDs,
            @PathVariable("startdatetime") String startDateTime,
            @PathVariable("enddatetime") String endDateTime) {
        try {
            this.blockSwimBookerPort.execute(startDateTime, endDateTime,
                    requiredModelAdapter.getBlockSwimBookerModel(staffID, swimIDs));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("block booking successful");
        } catch(Exception e) {
            return ResponseEntity
                    .status(HttpStatus.PRECONDITION_FAILED)
                    .body("staffID not found.");
        }
    }

    @PostMapping("/addnews/{staffid}/{venuename}/{title}/{uploaddate}/{content}") ResponseEntity
    addNews(
            @PathVariable("staffid") int staffID,
            @PathVariable("venuename") String venueName,
            @PathVariable("title") String title,
            @PathVariable("uploaddate") String uploadDate,
            @PathVariable("content") String content) {
        try {
            this.addNewsPort.execute(title, uploadDate, content,
                    this.requiredModelAdapter.getAddSwimsModel(staffID, venueName));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("news uploaded to " + venueName);
        } catch(Exception e) {
            return ResponseEntity
                    .status(HttpStatus.PRECONDITION_FAILED)
                    .body("staffID not found");
        }
    }

    /**
     *
     * @param memberID
     * @param swimID
     * @param startDateTime
     * @param endDateTime
     * @return
     */
    @GetMapping("/getswimreports/{membershipid}/{swimid}/{startdatetime}/{enddatetime}") List<SwimReport> getSwimReports(
            @PathVariable("membershipid") int memberID,
            @PathVariable("swimid") int swimID,
            @PathVariable("startdatetime") String startDateTime,
            @PathVariable("enddatetime") String endDateTime) {
        return this.getSwimReportsPort.execute(startDateTime, endDateTime,
                requiredModelAdapter.getSwimReportsModel(memberID, swimID));
    }





}
