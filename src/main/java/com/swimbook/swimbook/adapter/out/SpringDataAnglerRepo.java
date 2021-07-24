package com.swimbook.swimbook.adapter.out;

import org.springframework.data.repository.CrudRepository;
import com.swimbook.swimbook.domain.Angler;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataAnglerRepo extends CrudRepository<Angler, Integer> {

    Angler findAnglerByMembershipID(int membershipID);
}
