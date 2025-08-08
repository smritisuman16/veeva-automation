package com.veeva.automation.utils;
import java.nio.file.*;
import java.io.IOException;
public class FileUtils {
    public static void writeStringToFile(String path, String content) throws IOException {
        Files.createDirectories(Paths.get(path).getParent());
        Files.writeString(Paths.get(path), content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
