package com.example.quartz.utils;

import org.quartz.JobDetail;
import org.quartz.Trigger;

import java.util.Map;

public class Utils {
    public static void printAllJobDetails(Map<String, JobDetail> map){
       map.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " " + entry.getValue());
        });

    }

    public static void printAllTriggers(Map<String, Trigger> map){
        map.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " " + entry.getValue());
        });

    }
}