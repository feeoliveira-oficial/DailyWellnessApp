package com.example.dailywellnesstracker.Model;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface WellnessEntryDao {

    @Insert
    void insert(WellnessEntry entry);

    @Update
    void update(WellnessEntry entry);

    @Delete
    void delete(WellnessEntry entry);

    @Query("SELECT * FROM wellness_entries ORDER BY date DESC")
    LiveData<List<WellnessEntry>> getAllEntries();

    @Query("SELECT * FROM wellness_entries WHERE userId = :userId ORDER BY date DESC")
    LiveData<List<WellnessEntry>> getEntriesForUser(int userId);

    @Query("DELETE FROM wellness_entries WHERE userId = :userId")
    void deleteTasksByUserId(int userId);

    @Query("SELECT * FROM wellness_entries WHERE entryId = :entryId LIMIT 1")
    LiveData<WellnessEntry> getEntryById(int entryId);

}
