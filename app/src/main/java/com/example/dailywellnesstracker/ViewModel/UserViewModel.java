package com.example.dailywellnesstracker.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.dailywellnesstracker.Model.User;
import com.example.dailywellnesstracker.Repository.UserRepository;
import java.util.List;


public class UserViewModel extends AndroidViewModel {
    private final LiveData<List<User>> allUsers;
    private UserRepository repository;

    public UserViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);
        allUsers = repository.getAllUsers();
    }

    public void insertUser(User user) {
        repository.insertUser(user);
    }

    public LiveData<List<User>> getAllUsers() {
        return repository.getAllUsers();
    }

    public LiveData<User> getUserById(int userId) {
        return repository.getUserById(userId);
    }


    public void deleteUserAndTasks(User user) {
        repository.deleteUserAndTasks(user);
    }
}
