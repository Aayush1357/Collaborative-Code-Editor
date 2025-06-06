package com.collaborativecode.WebSocketMicroservice.service;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Service
public class JdoodleService {

    private static final String JDoodle_API_URL = "https://api.jdoodle.com/v1/execute";

    private static final String CLIENT_ID = "1536a25e2e79635edf5edf8756cfd135";
    private static final String CLIENT_SECRET = "68c973a3857c6bd23bfd769cc6358c4211bf069dbdecc1a929e3459853b3cb59";



    public Map executeCode(String language , String version , String code){

        RestTemplate restTemplate = new RestTemplate();

        Map<String , Object>  requestBody = new HashMap<>();
        requestBody.put("clientId" , CLIENT_ID);
        requestBody.put("clientSecret" , CLIENT_SECRET);
        requestBody.put("script" , code);
        requestBody.put("language" , language);
        requestBody.put("versionIndex" , version);

        return restTemplate.postForObject(JDoodle_API_URL , requestBody , Map.class);
    }
}
