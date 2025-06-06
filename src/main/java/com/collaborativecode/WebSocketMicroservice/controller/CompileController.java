package com.collaborativecode.WebSocketMicroservice.controller;


import com.collaborativecode.WebSocketMicroservice.service.JdoodleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://lcoalhost/3000")
@RequestMapping("/api/compile")
public class CompileController {

    @Autowired
    private JdoodleService jdoodleService;

    @PostMapping
    public Map<String , Object> compileCode(@RequestBody Map<String, Object> request){
        String language = (String) request.get("language");
        String version = (String) request.get("version");
        String code = (String) request.get("code");


        return jdoodleService.executeCode(language , version , code);
    }
}
