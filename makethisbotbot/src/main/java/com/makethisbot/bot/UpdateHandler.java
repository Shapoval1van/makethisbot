package com.makethisbot.bot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public class UpdateHandler {

    public SendMessage processUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

//
//            // Creating a Mongo client
//            MongoClient mongo = new MongoClient( "localhost" , 27017 );
//
//            // Creating Credentials
//            MongoCredential credential;
//            credential = MongoCredential.createCredential("sampleUser", "myDb",
//                    "password".toCharArray());
//            System.out.println("Connected to the database successfully");
//
//            // Accessing the database
//            MongoDatabase database = mongo.getDatabase("myDb");
//            MongoCollection mongoCollection = database.getCollection("test");
//            BasicDBObject searchQuery = new BasicDBObject();
//                    searchQuery.put("sdfsf", "sdfsdf");
//            FindIterable<BasicDBObject> findIterable =  mongoCollection.find(searchQuery);
//            MongoCursor<BasicDBObject> it = findIterable.iterator();
//            while (it.hasNext()) {
//                BasicDBObject dbObject = it.next();
//                System.out.println(dbObject);
//            }
            long chat_id = update.getMessage().getChatId();

            SendMessage message = new SendMessage() // Create a message object object
                    .setChatId(chat_id)
                    .setText("Шо хотіла залупа??");
            return message; // Sending our message object to user
        }
        return null;
    }
}
