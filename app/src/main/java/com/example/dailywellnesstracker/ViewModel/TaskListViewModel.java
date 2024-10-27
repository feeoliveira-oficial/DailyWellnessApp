package com.example.dailywellnesstracker.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.dailywellnesstracker.Model.WellnessEntry;
import com.example.dailywellnesstracker.Repository.WellnessRepository;
import java.util.List;

public class TaskListViewModel extends AndroidViewModel {

    private WellnessRepository repository;

    public TaskListViewModel(Application application) {
        super(application);
        repository = new WellnessRepository(application);
    }

    public LiveData<List<WellnessEntry>> getTasksForUser(int userId) {
        return repository.getEntriesForUser(userId);
    }
}
