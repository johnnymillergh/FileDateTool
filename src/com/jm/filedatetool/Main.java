package com.jm.filedatetool;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        FileDateTools.makeCreatedTimeEqualModifiedTime("C:\\Users\\Johnny\\Music\\钟俊 - 童心是小鸟.wav");
        long endTime = System.currentTimeMillis();
        System.err.println("Runtime: " + ((endTime - startTime) / 1000d) + " s (=" + (endTime - startTime) + " ms)");
    }
}
