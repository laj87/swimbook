package com.swimbook.swimbook.adapter.out;

import com.swimbook.swimbook.domain.News;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataNewsRepo extends CrudRepository<News, Integer> {
}
