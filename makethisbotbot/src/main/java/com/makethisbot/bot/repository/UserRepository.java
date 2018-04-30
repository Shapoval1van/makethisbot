package com.makethisbot.bot.repository;

import com.makethisbot.bot.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {
}
