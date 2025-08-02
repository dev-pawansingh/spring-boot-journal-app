package com.pawansingh.journalApp.controller;

import com.pawansingh.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journalV1")
public class JournalEntryController {

    private Map<Long, JournalEntry> map = new HashMap<>();

//    @GetMapping("/get-all") // can be used
    @GetMapping // if nothing is written after journal this will run in GET request
    public List<JournalEntry> getAll(){
        List<JournalEntry> list = new ArrayList<>(map.values());
//        return new ArrayList<>(map.values());
        return list;
    }

    @PostMapping // if nothing is written after journal this will run in POST request
    public boolean createEntry(@RequestBody JournalEntry myEntry){
        map.put(myEntry.getId(), myEntry);
        return true;
    }

    // path variable e.g localhost:8080/journal/id/pawan ,, here pawan is path variable
    // Here we want to see details of journal with id 2 => journal/id/2, so check below method

    // request variable e.g localhost:8080/journal/id?name = pawan ,, here name is request parameter

    @GetMapping("id/{myId}")
    public JournalEntry getEntryById(@PathVariable Long myId){
        return map.get(myId);
    }

    @DeleteMapping("id/{myId}")
    public JournalEntry deleteEntryById(@PathVariable Long myId){
        return map.remove(myId);
    }

    @PutMapping("id/{myId}")
    public JournalEntry putEntryById(@PathVariable Long myId, @RequestBody JournalEntry myEntry){
        return map.put(myId,myEntry);
    }

}
