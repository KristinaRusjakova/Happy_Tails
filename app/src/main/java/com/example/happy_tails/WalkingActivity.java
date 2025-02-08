package com.example.happy_tails;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;

public class WalkingActivity {
    private int walkingID;
    private int userID;
    private String dog;
    private LocalDate walkingDate;
    private LocalTime walkingStartTime;
    private LocalTime walkingEndTime;
    private long totalWalkingTime;
    private double totalWalkingDistance;
    private String walkingDescription;

    public WalkingActivity(){
    }
    public WalkingActivity(int walkingID, int userID, String dog, LocalDate walkingDate, LocalTime walkingStartTime, LocalTime walkingEndTime, double totalWalkingDistance, String walkingDescription) {
        this.walkingID = walkingID;
        this.userID = userID;
        this.dog = dog;
        this.walkingDate = walkingDate;
        this.walkingStartTime = walkingStartTime;
        this.walkingEndTime = walkingEndTime;
        this.totalWalkingTime = calculateWalkingTime();
        this.totalWalkingDistance = totalWalkingDistance;
        this.walkingDescription = walkingDescription;
    }

    private long calculateWalkingTime() {
        if (walkingStartTime != null && walkingEndTime != null) {
            return Duration.between(walkingStartTime, walkingEndTime).toMinutes();
        }
        return 0;
    }
    public void setWalkingID(int walkingID) {
        this.walkingID = walkingID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setDog(String dog) {
        this.dog = dog;
    }

    public void setWalkingDate(LocalDate walkingDate) {
        this.walkingDate = walkingDate;
    }

    public void setWalkingStartTime(LocalTime walkingStartTime) {
        this.walkingStartTime = walkingStartTime;
    }

    public void setWalkingEndTime(LocalTime walkingEndTime) {
        this.walkingEndTime = walkingEndTime;
    }

    public void setTotalWalkingTime(long totalWalkingTime) {
        this.totalWalkingTime = totalWalkingTime;
    }

    public void setTotalWalkingDistance(double totalWalkingDistance) {
        this.totalWalkingDistance = totalWalkingDistance;
    }

    public void setWalkingDescription(String walkingDescription){
        this.walkingDescription = walkingDescription;
    }
    public int getWalkingID() {
        return walkingID;
    }

    public int getUserID() {
        return userID;
    }

    public String getDog() {
        return dog;
    }

    public LocalDate getWalkingDate() {
        return walkingDate;
    }

    public LocalTime getWalkingStartTime() {
        return walkingStartTime;
    }

    public LocalTime getWalkingEndTime() {
        return walkingEndTime;
    }

    public long getTotalWalkingTime() {
        return totalWalkingTime;
    }

    public double getTotalWalkingDistance() {
        return totalWalkingDistance;
    }

    public String getWalkingDescription(){
        return walkingDescription;
    }
}
