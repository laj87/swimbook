package com.swimbook.swimbook.adapter.out;

import com.swimbook.swimbook.domain.Bailiff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataBailiffRepo extends CrudRepository<Bailiff, Integer> {

    List<Bailiff> findBailiffByStaffID(int staffID);


}
