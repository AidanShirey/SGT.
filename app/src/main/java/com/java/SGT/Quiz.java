package com.java.SGT;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class Quiz {
    public ArrayList<String> questions;
    public ArrayList<String> types;
    public ArrayList<String> choices;
    public ArrayList<String> answers;
    public String topic;
    public Quiz(){
        // Default constructor
    }
    public Quiz(ArrayList<String> questions, ArrayList<String> types, ArrayList<String> choices, ArrayList<String> answers, String topic){
        this.questions = questions;
        this.types = types;
        this.choices = choices;
        this.answers = answers;
        this.topic = topic;
    }

}
