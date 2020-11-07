package com.example.yu_map.Lecture;

import java.util.HashMap;

public class Lecture {

    private String Grade;
    private String LectureName;
    private String FirstDays;
    private String SecondDays;
    private String Professor;
    private String FirstDaysStartTime;
    private String FirstDaysFinishTime;
    private String SecondDaysStartTime;
    private String SecondDaysFinishTime;
    private String Required;

    public Lecture(){
    }

    public Lecture(String grade1,String Required1 ,String LectureName1, String Professor1, String FirstDays1
                    ,String FirstDaysStartTime1, String FirstDaysFinishTime1, String SecondDays1, String SecondDaysStartTime1, String SecondDaysFinishTime1){
        this.Grade = grade1;
        this.Required = Required1;
        this.LectureName = LectureName1;
        this.Professor = Professor1;
        this.FirstDays = FirstDays1;
        this.FirstDaysStartTime = FirstDaysStartTime1;
        this.FirstDaysFinishTime = FirstDaysFinishTime1;
        this.SecondDays = SecondDays1;
        this.SecondDaysStartTime = SecondDaysStartTime1;
        this.SecondDaysFinishTime = SecondDaysFinishTime1;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getLectureName() {
        return LectureName;
    }

    public void setLectureName(String lectureName) {
        LectureName = lectureName;
    }

    public String getFirstDays() {
        return FirstDays;
    }

    public void setFirstDays(String firstDays) {
        FirstDays = firstDays;
    }

    public String getSecondDays() {
        return SecondDays;
    }

    public void setSecondDays(String secondDays) {
        SecondDays = secondDays;
    }

    public String getProfessor() {
        return Professor;
    }

    public void setProfessor(String professor) {
        Professor = professor;
    }

    public String getFirstDaysStartTime() {
        return FirstDaysStartTime;
    }

    public void setFirstDaysStartTime(String firstDaysStartTime) {
        FirstDaysStartTime = firstDaysStartTime;
    }

    public String getFirstDaysFinishTime() {
        return FirstDaysFinishTime;
    }

    public void setFirstDaysFinishTime(String firstDaysFinishTime) {
        FirstDaysFinishTime = firstDaysFinishTime;
    }

    public String getSecondDaysStartTime() {
        return SecondDaysStartTime;
    }

    public void setSecondDaysStartTime(String secondDaysStartTime) {
        SecondDaysStartTime = secondDaysStartTime;
    }

    public String getSecondDaysFinishTime() {
        return SecondDaysFinishTime;
    }

    public void setSecondDaysFinishTime(String secondDaysFinishTime) {
        SecondDaysFinishTime = secondDaysFinishTime;
    }

    public String getRequired() {
        return Required;
    }

    public void setRequired(String required) {
        Required = required;
    }
}
