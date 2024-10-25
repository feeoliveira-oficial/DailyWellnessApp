package com.example.dailywellnesstracker.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.dailywellnesstracker.Model.User;
import com.example.dailywellnesstracker.Repository.UserRepository;


public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;

    public UserViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public void insertUser(User user) {
        repository.insertUser(user);
    }

    public User loginUser(String username, String password) {
        return repository.getUser(username, password);
    }

    public LiveData<Integer> isUserExists(String username) {
        return repository.isUserExists(username);
    }
}
