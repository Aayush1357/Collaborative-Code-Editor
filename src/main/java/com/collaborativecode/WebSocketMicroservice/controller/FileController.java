package com.collaborativecode.WebSocketMicroservice.controller;


import com.collaborativecode.WebSocketMicroservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.*;
@RestController
@RequestMapping("api/files")
@CrossOrigin(origins = "http://localhost:3000")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping
    public List<String> getFileStructure(@RequestParam(defaultValue = ".") String path){

        return fileService.listFilesNFolders(path);

    }


}
