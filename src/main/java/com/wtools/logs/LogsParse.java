package com.wtools.logs;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author Jerry
 * @commpany Sunray
 * @since 2019/6/19
 */
public class LogsParse {

    public static void main(String[] args) throws Exception {
        parseMessageDate();
    }

    public static void parseMessageDate() throws IOException {
        Path sourceFile = Paths.get("D:\\doc\\dizhi\\shpharm.log.2019-06-17.7");
        Path targetFile = Paths.get("D:\\doc\\dizhi\\shpharm.log.2019-06-17-test.7");
        Files.lines(sourceFile, Charset.forName("gb2312"))
                .filter(s -> s.startsWith("2019-06-17 10:41:33"))
                .forEach(s -> {
                    try {
                        Files.write(targetFile, s.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                        Files.write(targetFile, "\n".getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
