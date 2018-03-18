package com.makethisbot.bot;

import lombok.*;
import org.bson.types.ObjectId;

import javax.persistence.Entity;
import javax.persistence.Table;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "user")
public class User {

    @Getter
    @Setter
    private ObjectId userID;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;
}
