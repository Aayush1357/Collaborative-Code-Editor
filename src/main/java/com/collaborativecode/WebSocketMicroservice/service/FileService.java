package com.collaborativecode.WebSocketMicroservice.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileService {

    public static List<String> listFilesNFolders(String dir){
        List<String> files = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(dir))){
            return paths.map(path -> {
                if (Files.isDirectory(path)){;
                    return "DIR" + path.toString();
                }else {
                    return "FILE" + path.toString();
                }
            }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return files;
    }
}
