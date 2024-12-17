package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoginDataLoader {
    public static List<String[]> loadLoginData(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath))
                .skip(1) // 첫 줄은 헤더이므로 생략
                .map(line -> line.split(","))
                .collect(Collectors.toList());
    }
}

