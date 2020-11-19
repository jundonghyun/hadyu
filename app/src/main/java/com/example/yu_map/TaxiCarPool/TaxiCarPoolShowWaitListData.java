package com.example.yu_map.TaxiCarPool;

public class TaxiCarPoolShowWaitListData {
    double FinishLatitude;
    double StartLatitude;
    double FinsiahLongitude;
    String Start;
    String End;
    String RemainTime;
    double StartLongitude;
    String MakerID;
    String FirstUser;
    String SecondUser;
    String ThirdUser;


    public TaxiCarPoolShowWaitListData(){

    }

    public String getFirstUser() {
        return FirstUser;
    }

    public void setFirstUser(String firstUser) {
        FirstUser = firstUser;
    }

    public String getSecondUser() {
        return SecondUser;
    }

    public void setSecondUser(String secondUser) {
        SecondUser = secondUser;
    }

    public String getThirdUser() {
        return ThirdUser;
    }

    public void setThirdUser(String thirdUser) {
        ThirdUser = thirdUser;
    }

    public TaxiCarPoolShowWaitListData(double finishLatitude, double startLatitude, double finsiahLongitude, String start, String end, String remainTime, double startLongitude, String makerID, String firstUser, String secondUser, String thirdUser) {
        FinishLatitude = finishLatitude;
        StartLatitude = startLatitude;
        FinsiahLongitude = finsiahLongitude;
        Start = start;
        End = end;
        RemainTime = remainTime;
        StartLongitude = startLongitude;
        MakerID = makerID;
        FirstUser = firstUser;
        SecondUser = secondUser;
        ThirdUser = thirdUser;
    }

    public double getFinishLatitude() {
        return FinishLatitude;
    }

    public void setFinishLatitude(double finishLatitude) {
        FinishLatitude = finishLatitude;
    }

    public double getStartLatitude() {
        return StartLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        StartLatitude = startLatitude;
    }

    public double getFinsiahLongitude() {
        return FinsiahLongitude;
    }

    public void setFinsiahLongitude(double finsiahLongitude) {
        FinsiahLongitude = finsiahLongitude;
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String end) {
        End = end;
    }

    public String getRemainTime() {
        return RemainTime;
    }

    public void setRemainTime(String remainTime) {
        RemainTime = remainTime;
    }

    public double getStartLongitude() {
        return StartLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        StartLongitude = startLongitude;
    }

    public String getMakerID() {
        return MakerID;
    }

    public void setMakerID(String makerID) {
        MakerID = makerID;
    }
}
