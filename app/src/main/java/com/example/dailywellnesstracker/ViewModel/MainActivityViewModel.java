package com.example.dailywellnesstracker.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.dailywellnesstracker.Model.WellnessEntry;
import com.example.dailywellnesstracker.Repository.WellnessRepository;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private WellnessRepository repository;

    public MainActivityViewModel(Application application) {
        super(application);
        repository = new WellnessRepository(application);
    }

    public void insert(WellnessEntry entry) {
        repository.insert(entry);
    }

    public LiveData<List<WellnessEntry>> getAllEntries() {
        return repository.getAllEntries();
    }

    public void update(WellnessEntry entry) {
        repository.update(entry);
    }

    public void delete(WellnessEntry entry) {
        repository.delete(entry);
    }
}
