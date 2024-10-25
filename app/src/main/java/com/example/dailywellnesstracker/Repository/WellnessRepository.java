package com.example.dailywellnesstracker.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.dailywellnesstracker.Model.AppDatabase;
import com.example.dailywellnesstracker.Model.WellnessEntry;
import com.example.dailywellnesstracker.Model.WellnessEntryDao;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WellnessRepository {
    private final WellnessEntryDao wellnessEntryDao;
    private final ExecutorService executorService;

    public WellnessRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        wellnessEntryDao = db.wellnessEntryDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(WellnessEntry entry) {
        executorService.execute(() -> wellnessEntryDao.insert(entry));
    }

    public LiveData<List<WellnessEntry>> getEntriesByDateRange(long startDate, long endDate) {
        return wellnessEntryDao.getEntriesByDateRange(startDate, endDate);
    }

    public LiveData<List<WellnessEntry>> getAllEntries() {
        return wellnessEntryDao.getAllEntries();
    }

    public void update(WellnessEntry entry) {
        executorService.execute(() -> wellnessEntryDao.update(entry));
    }

    public void delete(WellnessEntry entry) {
        executorService.execute(() -> wellnessEntryDao.delete(entry));
    }
}
