package com.jm.filedatetool;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <b>Description: FileDateTools, to change description here.</b>
 * <p>Author: Johnny Miller</p>
 * <p>Email: johnnysviva@outlook.com</p>
 * <p>Date: 6/27/2018</p>
 * <p>Time: 4:23 PM</p>
 **/
public class FileDateTools {
    public static void makeCreatedTimeEqualModifiedTime(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (file.isFile()) {
                // TODO: make created time equal modified time
                Date modifiedDate = getModifiedTime(file.getAbsolutePath());
                System.err.println("File: " + file.getAbsolutePath() + " --- " + setCreatedTime(modifiedDate, file
                        .getAbsolutePath()));
            } else if (files.length == 0) {
                System.out.println("Empty directory: " + file.getAbsolutePath());
                return;
            } else {
                for (File file1 : files) {
                    if (file1.isDirectory()) {
                        System.out.println("Directory: " + file1.getAbsolutePath());
                        makeCreatedTimeEqualModifiedTime(file1.getAbsolutePath());
                    } else {
                        // TODO: make created time equal modified time
                        Date modifiedDate = getModifiedTime(file1.getAbsolutePath());
                        System.err.println("File: " + file1.getAbsolutePath() + " --- " + setCreatedTime
                                (modifiedDate, file1.getAbsolutePath()));
                    }
                }
            }
        } else {
            System.err.println("Invalid path!");
        }
    }

    public static void getFileList(String strPath) {
        File file = new File(strPath);
        try {
            if (file.isDirectory()) {
                File[] fs = file.listFiles();
                for (int i = 0; i < fs.length; i++) {
                    String absolutePath = fs[i].getAbsolutePath();
                    System.out.println("Directory: " + absolutePath);
                    getFileList(absolutePath);
                }
            } else if (file.isFile()) {
                String fileName = file.getAbsolutePath();
                System.out.println("File: " + fileName);
            } else {
                System.err.println("Incorrect path!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get file's created time.
     *
     * @param fullFileName Absolute path of file
     * @return Date class instance
     */
    public static Date getCreatedTime(String fullFileName) {
        Path path = Paths.get(fullFileName);
        BasicFileAttributeView basicFileAttributeView = Files.getFileAttributeView(path, BasicFileAttributeView
                .class, LinkOption.NOFOLLOW_LINKS);
        BasicFileAttributes basicFileAttributes;
        try {
            basicFileAttributes = basicFileAttributeView.readAttributes();
            Date createdDate = new Date(basicFileAttributes.creationTime().toMillis());
            return createdDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 0, 1, 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * Get file's modified time.
     *
     * @param fullFileName Absolute path of file
     * @return Date class instance
     */
    public static Date getModifiedTime(String fullFileName) {
        File file = new File(fullFileName);
        long modifiedTime = file.lastModified();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(modifiedTime);

        return calendar.getTime();
    }

    public static boolean setCreatedTime(Date date, String fullFileName) {
        Path path = Paths.get(fullFileName);
        FileTime fileTime = FileTime.fromMillis(date.getTime());
        try {
            Files.setAttribute(path, "basic:creationTime", fileTime, LinkOption.NOFOLLOW_LINKS);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
