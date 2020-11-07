package com.example.yu_map.TimeTable.List.ShowLecture;

public class ShowLectureViewItem {
    private String LectureName;
    private String LectureProfessorName;
    private String LectureGrade;
    private String LectureRequire;
    private String FirstDays;
    private String FirstDaysStartTime;
    private String TotalSchedule;

    public String getTotalSchedule() {
        return TotalSchedule;
    }

    public void setTotalSchedule(String totalSchedule) {
        TotalSchedule = totalSchedule;
    }

    public String getFirstDays() {
        return FirstDays;
    }

    public void setFirstDays(String firstDays) {
        FirstDays = firstDays;
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

    public String getSecondDays() {
        return SecondDays;
    }

    public void setSecondDays(String secondDays) {
        SecondDays = secondDays;
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

    private String FirstDaysFinishTime;
    private String SecondDays;
    private String SecondDaysStartTime;
    private String SecondDaysFinishTime;

    public String getLectureName() {
        return LectureName;
    }

    public void setLectureName(String lectureName) {
        LectureName = lectureName;
    }

    public String getLectureProfessorName() {
        return LectureProfessorName;
    }

    public void setLectureProfessorName(String lectureProfessorName) {
        LectureProfessorName = lectureProfessorName;
    }

    public String getLectureGrade() {
        return LectureGrade;
    }

    public void setLectureGrade(String lectureGrade) {
        LectureGrade = lectureGrade;
    }

    public String getLectureRequire() {
        return LectureRequire;
    }

    public void setLectureRequire(String lectureRequire) {
        LectureRequire = lectureRequire;
    }

}
