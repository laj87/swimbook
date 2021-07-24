package com.swimbook.swimbook.adapter.out;

import com.swimbook.swimbook.domain.Swim;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataSwimRepo extends CrudRepository<Swim, Float> {

    public Swim findSwimBySwimID(long swimID);
}
