package org.http;

import org.http.handler.HttpDemo;

import java.io.IOException;

public class Runner {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpDemo demo = new HttpDemo();
        demo.demo();

//        Thread.sleep(100000L);
    }
}
