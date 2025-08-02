package com.pawansingh.journalApp.entity;

// POJO = plain old java object

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JournalEntry {
    private long id;
    private String title;
    private String content;


}
