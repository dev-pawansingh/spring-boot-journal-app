package com.pawansingh.journalApp.scheduler;

import com.pawansingh.journalApp.entity.JournalEntryV2;
import com.pawansingh.journalApp.entity.User;
import com.pawansingh.journalApp.enums.Sentiment;
import com.pawansingh.journalApp.repository.UserRepositoryImpl;
import com.pawansingh.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

//    @Autowired
//    private SentimentAnalysisService sentimentAnalysisService;

//    @Scheduled(cron = "0 0 9 * * SUN")  // cron maker
//    @Scheduled(cron = "0 * * ? * *")  // every second
//    public void fetchUserAndSendSaMail(){
//
//        List<User> users = userRepository.getUserForSA();
//        for(User user : users){
//            List<JournalEntryV2> journalEntries = user.getJournalEntries();
//            List<String> filteredEntries = journalEntries.stream().filter(x -> x.getDate()
//                            .isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
//                    .map(x -> x.getContent()).collect(Collectors.toList());
//            String entry = String.join(" ", filteredEntries);
//            String sentiment = sentimentAnalysisService.getSentiment(entry);
//            emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days",sentiment);
//        }
//
//    }


    public void fetchUserAndSendSaMail(){

        List<User> users = userRepository.getUserForSA();
        for(User user : users){
            List<JournalEntryV2> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate()
                            .isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getSentiment()).collect(Collectors.toList());

            Map<Sentiment,Integer> sentimentCount = new HashMap<>();
            for(Sentiment sentiment : sentiments){
                if (sentiment != null){
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment,0)+1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for(Map.Entry<Sentiment,Integer> entry : sentimentCount.entrySet()){
                if(entry.getValue() > maxCount){
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if(mostFrequentSentiment != null){
                emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days",mostFrequentSentiment.toString());
            }
        }

    }


}
