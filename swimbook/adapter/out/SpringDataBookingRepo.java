package com.swimbook.swimbook.adapter.out;

import com.swimbook.swimbook.domain.Booking;
import org.joda.time.DateTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataBookingRepo extends CrudRepository<Booking, Long> {

    Booking findBookingByBookingID(long bookingID);

    List<Booking> findBookingsByBookingDurationAfterAndBookingID(DateTime currentDate, long bookingID);
}
