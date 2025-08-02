package com.pawansingh.journalApp.controller;

import com.pawansingh.journalApp.entity.User;
import com.pawansingh.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    ///  Now will be done by ADMIN only
//    @GetMapping()
//    public List<User> getAllUsers(){
//        return userService.getAll();
//    }

    /// new user can be created by any one so sending it to public controller
//    @PostMapping()
//    public boolean createNewUser(@RequestBody User user){
//        userService.saveNewEntry(user);
//        return true;
//    }

    /// update will be done by authenticated user only and including user end point in spring security
//    @PutMapping("/{userName}")
//    public boolean updateUser(@RequestBody User user, @PathVariable String userName){
//        User userInDb = userService.findByUserName(userName);
//        if (userInDb!=null){
//            userInDb.setUserName(user.getUserName());
//            userInDb.setPassword(user.getPassword());
//            userService.saveEntry(userInDb);
//            return true;
//        }
//        return false;
//    }

    /// Same as above one but we will not take userName as path param
    /// then how we will get userName?
    /// userName and pass will come through security context holder as this page now need auth
    @PutMapping()
    public boolean updateUser(@RequestBody User user) {

        ///  if user is reaching here it means he have pass and username because user end-pt is secure
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);
        return true;
    }

    @DeleteMapping()
    public boolean deleteUserByUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteByUserName(authentication.getName());
        return true;
    }

}
