package com.vvopaa.cassandra.tutorial;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;

public interface TutorialRepository extends CassandraRepository<Tutorial, String> {

    @AllowFiltering
    List<Tutorial> findByPublished(boolean published);

    List<Tutorial> findByTitleContaining(String title);
}