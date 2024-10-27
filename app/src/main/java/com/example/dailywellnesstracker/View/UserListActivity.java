package com.example.dailywellnesstracker.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dailywellnesstracker.Model.User;
import com.example.dailywellnesstracker.R;
import com.example.dailywellnesstracker.ViewModel.UserViewModel;
import java.util.List;


public class UserListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;
    private UserViewModel userViewModel;
    private Button buttonDeleteUsers;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_register) {
            startActivity(new Intent(this, RegisterActivity.class));
            return true;
        } else if (id == R.id.nav_weather) {
            startActivity(new Intent(this, WeatherActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(this);
        recyclerViewUsers.setAdapter(userAdapter);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, users -> {
            if (users != null) {
                userAdapter.setUsers(users);
            }
        });
        // Button to delete users and delete the tasks associated with them
        buttonDeleteUsers = findViewById(R.id.buttonDeleteUsers);
        buttonDeleteUsers.setOnClickListener(v -> {
            boolean selectionMode = !userAdapter.selectionMode;
            userAdapter.setSelectionMode(selectionMode);

            if (!selectionMode) {
                confirmAndDeleteSelectedUsers();
            }
        });
    }

    private void confirmAndDeleteSelectedUsers() {
        List<User> selectedUsers = userAdapter.getSelectedUsers();

        if (selectedUsers.isEmpty()) {
            Toast.makeText(this, "No users selected", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Delete Users")
                .setMessage("Are you sure you want to delete the selected users?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    for (User user : selectedUsers) {
                        userViewModel.deleteUserAndTasks(user);
                    }
                    Toast.makeText(this, "Users deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
