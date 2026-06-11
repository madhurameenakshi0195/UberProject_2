package com.uberclone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {

        System.out.println("Application Started");
        System.out.println("Database Connected");
    }
}