package com.pawansingh.journalApp.service;

import com.pawansingh.journalApp.entity.JournalEntryV2;
import com.pawansingh.journalApp.entity.User;
import com.pawansingh.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// to write business logic we create service class/package
// as we need to create object of this class in controller so we make it component for dependency injection
@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public boolean saveEntry(JournalEntryV2 journalEntryV2, String userName){
        try{
            User user = userService.findByUserName(userName);
            JournalEntryV2 saved = journalEntryRepository.save(journalEntryV2);
            user.getJournalEntries().add(saved);
            /// if failed at this point,it creates inconsistency
            /// hence use @Transactional which will treat whole fun as single transaction
            /// also in main function class write @EnableTransactionManagement
            /// this brings atomicity i.e. one fail so all, fail all done so done work
            /// also achieve isolation, if 2 person use api at same time spring boot
            /// will create 2 transactional context for each user hence process will
            /// be isolated and independent
            /// if function fail at any point all process will be roll back
            userService.saveUser(user);
            return true;
        }catch(Exception e){
            return false;
        }

    }
    public void saveEntry(JournalEntryV2 journalEntryV2){
        journalEntryRepository.save(journalEntryV2);
    }
    public List<JournalEntryV2> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntryV2> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    @Transactional
    public void deleteById(ObjectId id, String userName){
//        journalEntryRepository.deleteById(id);
        User user = userService.findByUserName(userName);
        boolean b = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        if (b){
            journalEntryRepository.deleteById(id);
            userService.saveUser(user);
        }
    }

//    public List<JournalEntryV2> findByUserName(String userName){
//
//    }

}
