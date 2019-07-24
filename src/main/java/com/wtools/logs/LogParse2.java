package com.wtools.logs;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.SocketHandler;
import java.util.stream.Collectors;

/**
 * @author Jerry
 * @commpany Sunray
 * @since 2019/6/21
 */
public class LogParse2 {

    public static void main(String[] args) throws Exception {
//        parseSygcLog();
//        parseShpharm();

//        parseWithKeywords("D:\\doc\\errorlog\\20190621\\shpharm\\shpharm.log.2019-06-21.5\\shpharm.log.2019-06-21.5"
//                ,"updateAutoRfidCabinetInv"
//                ,"gb2312"
//                ,"call updateAutoRfidCabinetInv");

        parseMerge("D:\\doc\\errorlog\\20190621\\shpharm\\shpharm.log.2019-06-21.5\\shpharm.log.2019-06-21.5-updateAutoRfidCabinetInv","utf-8","call updateAutoRfidCabinetInv end");


//            parseExAlarm();


    }


    public static void parseWithKeywords(String sourceLog,String funcName,String charset,String kewwords) throws IOException {
        Path sourceFile = Paths.get(sourceLog);
        Path targetFile = Paths.get(sourceLog+"-"+funcName);
        Files.lines(sourceFile, Charset.forName(charset))
                .filter(s -> s.contains(kewwords))
                .forEach(s -> {
                    try {
                        Files.write(targetFile, s.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                        Files.write(targetFile, "\n".getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    public static void parseMerge(String sourceLog,String charset,String endKeywords) throws IOException {
        Path sourceFile = Paths.get(sourceLog);
        Path targetFile = Paths.get(sourceLog+"-merge");
        List<String> startList = new ArrayList<>();
        List<String> endList = new ArrayList<>();

        Files.lines(sourceFile, Charset.forName(charset))
                .forEach(s -> {
                    if(s.contains(endKeywords)){
                        endList.add(s);
                    }else {
                        startList.add(s);
                    }
                });

        AtomicInteger count = new AtomicInteger(0);
//        for(String v:startList) {
//            String threadName = v.substring(24, 49);
//            Iterator<String> it = endList.iterator();
//            System.out.println(endList.size());
//            while (it.hasNext()) {
//                String next = it.next();
//                if (next.substring(24, 49).equals(threadName)) {
//                    System.out.println("count " + count.getAndIncrement());
//                    try {
//                        Files.write(targetFile, v.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
//                        Files.write(targetFile, "\n".getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
//                        Files.write(targetFile, next.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
//                        Files.write(targetFile, "\n".getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    it.remove();
//                    break;
//                }
//            }
//        }
        startList.forEach(v -> {
            String threadName = v.substring(24, 49);
            Iterator<String> it = endList.iterator();
            System.out.println(endList.size());
            while (it.hasNext()){
                String next = it.next();
                if(next.substring(24, 49) .equals(threadName)){
                    System.out.println("count "+ count.getAndIncrement());
                    try{
                        Files.write(targetFile, v.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                        Files.write(targetFile, "\n".getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                        Files.write(targetFile, next.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                        Files.write(targetFile, "\n".getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
//                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");

                        LocalDateTime startTIme = LocalDateTime.parse(v.substring(0,19), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//                        Date startDate = format.parse(v.substring(0,19));
//                        System.out.println(startDate.toString());

//                        Date endDate = format.parse(next.substring(0,19));
//                        System.out.println(endDate.toString());
//
//                        System.out.println(endDate.getTime() - startDate.getTime());

//                        startTIme.
//                        LocalDateTime startTIme = LocalDateTime.parse(v.substring(0,19), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

//                        System.out.println(startTIme.toString());
                        LocalDateTime endTime = LocalDateTime.parse(next.substring(0,19), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//                        System.out.println(endTime.toString());

//                        Duration duration = Duration.between(startTIme,endTime);
                        long seconds = ChronoUnit.SECONDS.between(startTIme,endTime);
//                        System.out.println(seconds);
                        Files.write(targetFile, new String(seconds+"").getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                        Files.write(targetFile, "\n".getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
//                        System.out.println(duration.getSeconds());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    it.remove();
                    break;
                }

            }
        });

    }


    public static void parseSygcLog() throws IOException {
        Path sourceFile = Paths.get("D:\\doc\\errorlog\\1009\\20190712\\10.1.7.115\\sygc.log.2019-07-09.1\\sygc.log.2019-07-09.1");
        Path targetFile = Paths.get("D:\\doc\\errorlog\\1009\\20190712\\10.1.7.115\\sygc.log.2019-07-09.1\\sygc.log.2019-07-09.1-DayReportJob");
        Files.lines(sourceFile, Charset.forName("gb2312"))
                .filter(s -> s.contains("org.springframework.scheduling.quartz.SchedulerFactoryBean#0_Worker-4"))
                .forEach(s -> {
                    try {
                        Files.write(targetFile, s.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                        Files.write(targetFile, "\n".getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }



    public static void parseShpharm() throws IOException {
        Path sourceFile = Paths.get("D:\\doc\\errorlog\\20190621\\syby\\shpharm\\shpharm.log.2019-06-21.9-");
//        Path targetFile = Paths.get("D:\\doc\\errorlog\\20190621\\syby\\shpharm\\shpharm.log.2019-06-21.9-data");
        String targetPath = "D:\\doc\\errorlog\\20190621\\syby\\shpharm\\shpharm.log.2019-06-21.9-data";
//        Set<String> logSize = Files.lines(sourceFile, Charset.forName("utf-8"))
//                .filter(s -> s.length() > 100)
//                .map(s -> s.substring(24,49))
//                .filter(s -> s.startsWith("[http"))
//                .collect(Collectors.toSet());
////                .forEach(System.out::println);
//        System.out.println(logSize.size());

        Files.lines(sourceFile, Charset.forName("utf-8"))
                .forEach(v -> {
                    if (v.length() > 100) {
                        String threadName = v.substring(24, 49);
                        if (threadName.startsWith("[http")) {
                            Path targetFile = Paths.get(targetPath + "-" + threadName);
                            try {
                                Files.write(targetFile, v.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                                Files.write(targetFile, "\n".getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    public static void parseExAlarm() throws IOException {
        Path sourceFile = Paths.get("D:\\doc\\errorlog\\20190621\\sygc\\sygc-09-10.log");
//        Path targetFile = Paths.get("D:\\doc\\errorlog\\20190621\\syby\\shpharm\\shpharm.log.2019-06-21.9-data");
//        String targetPath = "D:\\doc\\errorlog\\20190621\\shpharm\\shpharm.log.2019-06-21.9-ex";
        Path targetPath = Paths.get("D:\\doc\\errorlog\\20190621\\sygc\\sygc-09-10-actionMessage");

//        Set<String> logSize = Files.lines(sourceFile, Charset.forName("utf-8"))
//                .filter(s -> s.length() > 100)
//                .map(s -> s.substring(24,49))
//                .filter(s -> s.startsWith("[http"))
//                .collect(Collectors.toSet());
////                .forEach(System.out::println);
//        System.out.println(logSize.size());

        Files.lines(sourceFile, Charset.forName("utf-8"))
                .forEach(v -> {
                    if (v.length() > 100) {
                        if (v.contains("actionMessage st") || v.contains("actionMessage ed")) {
                            try {
                                Files.write(targetPath, v.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                                Files.write(targetPath, "\n".getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }
}
