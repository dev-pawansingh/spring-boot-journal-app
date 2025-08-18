package com.pawansingh.journalApp.service;

import com.pawansingh.journalApp.entity.User;
import com.pawansingh.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;
    private static final PasswordEncoder passEncoder = new BCryptPasswordEncoder();

//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void saveUser(User user){
        userRepository.save(user);
    }
    public boolean saveNewUser(User user){
        try{
            user.setPassword(passEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
//            logger.warn("hahaha 2 same useer bana liye");
            log.warn("haha i am logging using Slfj4");
            log.debug("checking if enable or not using application.yml file");
            return false;
        }

    }
    public void saveAdmin(User user){
        user.setPassword(passEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("ADMIN","USER"));
        userRepository.save(user);
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }
    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }
    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }
    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
    public void deleteByUserName(String userName){
        userRepository.deleteByUserName(userName);
    }
}
