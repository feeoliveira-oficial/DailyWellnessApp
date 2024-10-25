package com.example.dailywellnesstracker.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.dailywellnesstracker.Model.AppDatabase;
import com.example.dailywellnesstracker.Model.User;
import com.example.dailywellnesstracker.Model.UserDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private final UserDao userDao;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
    }

    public void insertUser(User user) {
        executorService.execute(() -> userDao.insert(user));
    }

    public User getUser(String username, String password) {
        return userDao.getUser(username, password);
    }

    public LiveData<Integer> isUserExists(String username) {
        return userDao.isUserExists(username);
    }
}
