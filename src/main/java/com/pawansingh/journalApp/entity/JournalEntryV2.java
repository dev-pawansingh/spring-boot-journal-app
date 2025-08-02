package com.pawansingh.journalApp.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

//@Document // this tells mongoDB that use this class as document in the database
@Document(collection = "journal_entries") // just giving name to the collection, if not given
// it will be known by its class name
//In Spring Data MongoDB, a datatype class is the document, and it must be annotated with @Document to tell Spring it should be persisted.
//So when you say it’s “just a structure of the column” think of that as exactly what MongoDB needs — the structure of the document.
@Getter // lambok
@Setter // lambok
@Data  // lambok
public class JournalEntryV2 {
    @Id // to make id as primary key
    private ObjectId id;
//    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;


}
