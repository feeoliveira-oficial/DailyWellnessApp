package com.example.dailywellnesstracker.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dailywellnesstracker.R;
import com.example.dailywellnesstracker.ViewModel.TaskListViewModel;

public class TaskListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTasks;
    private TaskAdapter taskAdapter;
    private TaskListViewModel taskListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter();
        recyclerViewTasks.setAdapter(taskAdapter);

        taskListViewModel = new ViewModelProvider(this).get(TaskListViewModel.class);

        int userId = getIntent().getIntExtra("userId", -1);

        if (userId != -1)
        {
            taskListViewModel.getTasksForUser(userId).observe(this, tasks -> {
                if (tasks != null && !tasks.isEmpty())
                {
                    taskAdapter.setTasks(tasks);
                }
                else
                {
                    Toast.makeText(this, "No tasks found for this user", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(this, "Invalid user ID", Toast.LENGTH_SHORT).show();
        }
    }


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
}
