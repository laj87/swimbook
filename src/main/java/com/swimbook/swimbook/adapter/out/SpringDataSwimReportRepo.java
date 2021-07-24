package com.swimbook.swimbook.adapter.out;

import com.swimbook.swimbook.domain.SwimReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataSwimReportRepo extends CrudRepository<SwimReport, Integer> {
}
