package com.pawansingh.journalApp.config;

import com.pawansingh.journalApp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests() // tells spring security to start authorizing the requests.
//                .antMatchers("/hello").permitAll() // specify that HTTP request matching the path("/hello") should be permitted for all users even if they are not authenticated
//                .anyRequest().authenticated()//more general matcher specifies any req should be authenticated, meaning users have to provide valid credentials to access these endpoints
//                .and() // method to join several configurations. It helps to continue the configuration from the root (HttpSecurity)
//                .formLogin(); // enables form based auth. By default, it will provide a form for the user to enter their username and pass.
//    }

    /// This is for using authentication setup at end points i.e. where we want to setup auth
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/journal/**", "/user/**").authenticated() // journal k baad kutch bhi
                .antMatchers("/admin/**").hasRole("ADMIN")
                
                .anyRequest().permitAll()
                .and()
                .httpBasic();
        //http.csrf().disable();
        /// cross-site request forgery, by default enabled, when spring security is enabled
        /// when csrf protection is enabled, spring security expect a csrf token in request,
        /// which we are not sending currently so we disabled it

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable();
        /// as spring security manage sessions so v r making it disable by making stateless

    }

    /// This is for authentication here we will use the userDetails we have created in UserDetailsService
    /// implementation class to include authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
