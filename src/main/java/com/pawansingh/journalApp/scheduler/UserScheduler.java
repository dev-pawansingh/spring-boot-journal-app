package com.pawansingh.journalApp.scheduler;

import com.pawansingh.journalApp.entity.JournalEntryV2;
import com.pawansingh.journalApp.entity.User;
import com.pawansingh.journalApp.repository.UserRepositoryImpl;
import com.pawansingh.journalApp.service.EmailService;
import com.pawansingh.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Scheduled(cron = "0 0 9 * * SUN")  // cron maker
//    @Scheduled(cron = "0 * * ? * *")  // every second
    public void fetchUserAndSendSaMail(){

        List<User> users = userRepository.getUserForSA();
        for(User user : users){
            List<JournalEntryV2> journalEntries = user.getJournalEntries();
            List<String> filteredEntries = journalEntries.stream().filter(x -> x.getDate()
                            .isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getContent()).collect(Collectors.toList());
            String entry = String.join(" ", filteredEntries);
            String sentiment = sentimentAnalysisService.getSentiment(entry);
            emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days",sentiment);
        }

    }

}
