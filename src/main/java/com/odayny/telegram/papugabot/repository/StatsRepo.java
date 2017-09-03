package com.odayny.telegram.papugabot.repository;

import com.odayny.telegram.papugabot.model.db.Stats;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatsRepo extends MongoRepository<Stats, String> {
}
