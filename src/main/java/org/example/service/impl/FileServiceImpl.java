package org.example.service.impl;

import lombok.SneakyThrows;
import org.example.service.FileService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileServiceImpl implements FileService {
    @Override
    @SneakyThrows
    public List<String> readFromFile(String filePath){
        File file = new File(filePath);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        List<String> lines = new ArrayList<>();
        String line;

        while((line = br.readLine()) != null)
            lines.add(line);

        br.close();
        fr.close();

        return lines;
    }

    @Override
    @SneakyThrows
    public void addData(String filePath, String data) {
        Files.write(Paths.get(filePath), (data+"\n").getBytes(), StandardOpenOption.APPEND);
    }
}

