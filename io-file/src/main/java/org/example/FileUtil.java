package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;

public class FileUtil {
    public static void main(String[] args) throws IOException {
//        tryFileStream();
        tryZeroCopy();
    }

    // file stream
    public static void tryFileStream() throws IOException {
        Path tmpFile = Files.createTempFile("try-java-file", "txt");
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(tmpFile, StandardOpenOption.WRITE)) {
            for (int i = 0; i < 1000; i++) {
                bufferedWriter.write("file content: line #" + i);
                bufferedWriter.newLine();
            }
        }
        try (BufferedReader bufferedReader = Files.newBufferedReader(tmpFile)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        }
        Files.delete(tmpFile);
    }

    // try zero copy
    public static void tryZeroCopy() throws IOException {
        Path srcPath = Files.createTempFile("try-java-file", ".txt");
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(srcPath, StandardOpenOption.WRITE)) {
            for (int i = 0; i < 1000; i++) {
                bufferedWriter.write("file content: line #" + i);
                bufferedWriter.newLine();
            }
        }
        Path targetPath = Files.createTempFile("try-java-file-target", ".txt");

        try (FileChannel src = FileChannel.open(srcPath, StandardOpenOption.READ)) {
            FileChannel target = FileChannel.open(targetPath, StandardOpenOption.WRITE);
            long pos = 0;
            while (true) {
                long bytes = src.transferTo(pos, 100, target);
                pos+=bytes;
                if (bytes < 100) {
                    break;
                }
            }
        }
        for (String l : Files.readAllLines(targetPath)) {
            System.out.println(l);
        }
        Files.delete(targetPath);
        Files.delete(srcPath);

    }
}
