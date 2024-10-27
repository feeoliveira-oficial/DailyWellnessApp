package com.example.dailywellnesstracker.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Calendar;

@Entity(tableName = "wellness_entries")
public class WellnessEntry {
    @PrimaryKey(autoGenerate = true)
    private int userId;
    private String waterIntake;
    private String sleepHours;
    private String exercise;
    private Calendar date;
    private boolean isCompleted;


    public WellnessEntry(String waterIntake, String sleepHours, String exercise, Calendar date, int userId) {
        this.waterIntake = waterIntake;
        this.sleepHours = sleepHours;
        this.exercise = exercise;
        this.date = date;
        this.isCompleted = false;
        this.userId = userId;
    }

    // Getters e Setters
    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public String getWaterIntake() {return waterIntake;}

    public String getSleepHours() {return sleepHours;}

    public String getExercise() {return exercise;}

    public Calendar getDate() {return date;}

    public boolean isCompleted() {return isCompleted;}

    public void setWaterIntake(String waterIntake) {this.waterIntake = waterIntake;}

    public void setSleepHours(String sleepHours) {this.sleepHours = sleepHours;}

    public void setExercise(String exercise) {this.exercise = exercise;}

    public void setCompleted(boolean isCompleted) {this.isCompleted = isCompleted;}



}
