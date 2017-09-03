package com.odayny.telegram.papugabot.repository;

import com.odayny.telegram.papugabot.model.db.WhitelistItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WhitelistItemRepo extends MongoRepository<WhitelistItem, String> {
    void deleteByUserId(String userId);

    WhitelistItem findFirstByUserId(String userId);
}
