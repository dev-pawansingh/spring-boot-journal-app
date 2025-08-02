package com.pawansingh.journalApp.controller;

import com.pawansingh.journalApp.entity.JournalEntryV2;
import com.pawansingh.journalApp.entity.User;
import com.pawansingh.journalApp.service.JournalEntryService;
import com.pawansingh.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journalV2")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    //    @GetMapping("/get-all") // can be used
    @GetMapping("/{userName}") // if nothing is written after journal this will run in GET request
    public ResponseEntity<List<JournalEntryV2>> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntryV2> all = user.getJournalEntries();
        if (all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/{userName}") // if nothing is written after journal this will run in POST request
    public boolean createEntry(@RequestBody JournalEntryV2 myEntry, @PathVariable String userName) {
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry,userName);
            return true;
    }

    /// /////////////////////////////// post mapping using Response Entity /////////////////////////////////////////

//    @PostMapping
//    public ResponseEntity<JournalEntryV2> createEntry(@RequestBody JournalEntryV2 myEntry) {
//        try {
// //           myEntry.setDate(date.now());
//            journalEntryService.saveEntry(myEntry);
//            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntryV2> getEntryById(@PathVariable ObjectId myId) {
//        return journalEntryService.findById(myId).orElse(null); if Optional is returned

        Optional<JournalEntryV2> journalEntryV2 = journalEntryService.findById(myId);
        if (journalEntryV2.isPresent()) {
            return new ResponseEntity<>(journalEntryV2.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{myId}")
    public void deleteEntryById(@PathVariable ObjectId myId, @PathVariable String userName) {
        journalEntryService.deleteById(myId,userName);
    }

    @PutMapping("id/{userName}/{myId}")
    public JournalEntryV2 putEntryById(@PathVariable ObjectId myId,
                                       @RequestBody JournalEntryV2 myEntry,
                                       @PathVariable String userName) {
        JournalEntryV2 old = journalEntryService.findById(myId).orElse(null);
        if (old != null) {
//            debug below line                         |||
//            old.setTitle(myEntry.getTitle() != null && myEntry.getTitle().equals("") ? myEntry.getTitle() : old.getTitle());
            old.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().equals("") ? myEntry.getTitle() : old.getTitle());
            old.setContent(myEntry.getContent() != null && !myEntry.getContent().equals("") ? myEntry.getContent() : old.getContent());
        }
        journalEntryService.saveEntry(old);
        return old;
    }

}
