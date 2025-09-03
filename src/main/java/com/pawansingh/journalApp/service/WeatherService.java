package com.pawansingh.journalApp.service;

import com.pawansingh.journalApp.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;
    private static final String api = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city){

        WeatherResponse weatherResponse = redisService.get(city, WeatherResponse.class);

        if(weatherResponse != null){
            return weatherResponse;
        }else{
            String finalApi = api.replace("API_KEY",apiKey).replace("CITY",city);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);

            ///  POST Request format
//        String requestBody = "{\n" +
//                "    \"userName\":\"Pawan\",\n" +
//                "    \"password\":\"Pawan\"\n" +
//                "}   ";
//        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody);
//        restTemplate.exchange(finalApi,HttpMethod.POST,httpEntity,WeatherResponse.class);

            WeatherResponse body = response.getBody();
            if(body != null){
                redisService.set(city+"",body,300l);
            }
            return body;
        }
    }
}
