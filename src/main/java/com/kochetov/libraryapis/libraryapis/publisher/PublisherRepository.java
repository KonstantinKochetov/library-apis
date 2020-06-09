package com.kochetov.libraryapis.libraryapis.publisher;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends CrudRepository<PublisherEntity, Integer> { // Integer is publisherId
    List<PublisherEntity> findByNameContaining(String name); // Spring will automatically implement it
}
