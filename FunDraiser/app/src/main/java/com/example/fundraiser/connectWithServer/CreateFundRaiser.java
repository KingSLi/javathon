package com.example.fundraiser.connectWithServer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateFundRaiser {
    private String fundraiserName;
    private ArrayList<String> participants;

    public CreateFundRaiser(String fundraiserName, String participants) {
        this.fundraiserName = fundraiserName;
        this.participants = new ArrayList<>(Arrays.asList(participants.split(", ")));

        for (int i = 0; i < this.participants.size(); ++i) {
            System.out.println(this.participants.get(i));
            System.out.println("\n");
        }
    }
}
