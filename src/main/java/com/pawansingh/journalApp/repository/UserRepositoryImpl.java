package com.pawansingh.journalApp.repository;

import com.pawansingh.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    MongoTemplate mongoTemplate;

     public List<User> getUserForSA(){
         Query query = new Query();
//         query.addCriteria(Criteria.where("userName").is("Ram"));
         // ne notEqual
         // lt lessThan
         // lte lessThanOrEqual
         // nin not in(list) eg nin("Rajat","Shanu")

//         query.addCriteria(Criteria.where("email").exists(true));
//         query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

         /// to match the regular expression of email
         /// get string inside regix from internet, this one is fake
//         query.addCriteria(Criteria.where("email").regex("kdjfsakdj"));

         /// in above case and operator is automatically applicable, we can do it in below
         /// method if we want or/and Operator between 2 queries

         Criteria criteria = new Criteria();
         query.addCriteria(criteria.andOperator(Criteria.where("email").exists(true),Criteria.where("sentimentAnalysis").is(true)));

         List<User> users = mongoTemplate.find(query, User.class);
         return users;
     }

}
