package com.pawansingh.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true) // will allow unique value in userName for an Index
    // / the index will be unique through application.properties
    @NonNull // when getter setter called cannot be left null
    private String userName;
    @NonNull
    private String password;

    @DBRef  // linked journal entry v2 with this(single) user // will keep the reference of entries in Journal Entries V2
    private List<JournalEntryV2> journalEntries = new ArrayList<>();

    private List<String> roles = new ArrayList<>();
}
