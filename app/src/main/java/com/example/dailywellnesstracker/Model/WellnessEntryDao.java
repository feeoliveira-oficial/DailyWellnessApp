package com.example.dailywellnesstracker.Model;

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

    @Query("SELECT * FROM wellness_entries WHERE date BETWEEN :startDate AND :endDate")
    LiveData<List<WellnessEntry>> getEntriesByDateRange(long startDate, long endDate);

    @Query("SELECT * FROM wellness_entries")
    LiveData<List<WellnessEntry>> getAllEntries();


}
