package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GetInputFileContents {

    public static List<String> getFileLines(String inputFilePath) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));

        return reader.lines().toList();
    }

    public static String getFileAsString(String inputFilePath) throws IOException {
        return Files.readString(Path.of(inputFilePath));
    }
}
