package com.example.dailywellnesstracker.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.dailywellnesstracker.Model.AppDatabase;
import com.example.dailywellnesstracker.Model.User;
import com.example.dailywellnesstracker.Model.UserDao;
import com.example.dailywellnesstracker.Model.WellnessEntryDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private final UserDao userDao;
    private final WellnessEntryDao wellnessEntryDao;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        wellnessEntryDao = db.wellnessEntryDao();
        allUsers = userDao.getAllUsers();

    }

    public void insertUser(User user) {
        executorService.execute(() -> userDao.insert(user));
    }

    public LiveData<List<User>> getAllUsers() {
        return userDao.getAllUsers();
    }

    public LiveData<User> getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    public void deleteUser(User user) {
        executorService.execute(() -> userDao.delete(user));
    }

    public void deleteUserAndTasks(User user) {
        executorService.execute(() -> {
            wellnessEntryDao.deleteTasksByUserId(user.getId());
            userDao.delete(user);
        });
    }
}
