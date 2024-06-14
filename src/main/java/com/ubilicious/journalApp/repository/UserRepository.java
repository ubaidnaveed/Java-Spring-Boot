package com.ubilicious.journalApp.repository;

import com.ubilicious.journalApp.entity.JournalEntry;
import com.ubilicious.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);
}
