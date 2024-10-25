package com.example.dailywellnesstracker.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Calendar;

@Entity(tableName = "wellness_entries")
public class WellnessEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String waterIntake;
    private String sleepHours;
    private String exercise;
    private Calendar date;
    private boolean isCompleted;

    public WellnessEntry(String waterIntake, String sleepHours, String exercise, Calendar date) {
        this.waterIntake = waterIntake;
        this.sleepHours = sleepHours;
        this.exercise = exercise;
        this.date = date;
        this.isCompleted = false;
    }

    // Getters e Setters
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getWaterIntake() {return waterIntake;}

    public String getSleepHours() {return sleepHours;}

    public String getExercise() {return exercise;}

    public Calendar getDate() {return date;}

    public boolean isCompleted() {return isCompleted;}

    public void setWaterIntake(String waterIntake) {this.waterIntake = waterIntake;}

    public void setSleepHours(String sleepHours) {this.sleepHours = sleepHours;}

    public void setExercise(String exercise) {this.exercise = exercise;}

    public void setDate(Calendar date) {this.date = date;}

    public void setCompleted(boolean isCompleted) {this.isCompleted = isCompleted;}
}
