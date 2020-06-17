package com.example.demo.consumer;

import com.example.demo.gray.rule.GrayMetadataRule;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Arrays.asList(new ArrayList<Integer>());

        System.out.println("name :" + GrayMetadataRule.class.getName());
        System.out.println("canonicalName :" + GrayMetadataRule.class.getCanonicalName());
    }
}
