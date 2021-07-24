package com.swimbook.swimbook.adapter.out;

import com.swimbook.swimbook.domain.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataStatusRepo extends CrudRepository<Status, Long> {

    Status  getStatusByStatusID(Long statusID);
}
