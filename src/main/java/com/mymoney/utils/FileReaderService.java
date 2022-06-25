package com.mymoney.utils;

import com.mymoney.exception.MyMoneyException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReaderService {

    public static List<String> readFileIntoString(String filePath) {
        Path path = Path.of(filePath);
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new MyMoneyException("Error occurred while processing input file path : " + filePath);
        }
        return lines;
    }

}
