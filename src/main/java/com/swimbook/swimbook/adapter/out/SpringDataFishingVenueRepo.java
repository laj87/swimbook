package com.swimbook.swimbook.adapter.out;

import com.swimbook.swimbook.domain.FishingVenue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface SpringDataFishingVenueRepo extends CrudRepository<FishingVenue, Long> {

    public List<FishingVenue> findFishingVenueByVenueName(String name);
}
