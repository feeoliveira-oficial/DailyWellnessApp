package com.example.dailywellnesstracker.View;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dailywellnesstracker.Model.WellnessEntry;
import com.example.dailywellnesstracker.R;
import com.example.dailywellnesstracker.ViewModel.MainActivityViewModel;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextWaterIntake, editTextSleepHours;
    private Button buttonAddEntry, buttonSetDate;
    private Spinner spinnerExercise;
    private RecyclerView recyclerViewEntries;
    private WellnessAdapter wellnessAdapter;
    private ProgressBar progressBar;
    private Calendar date;
    private MainActivityViewModel viewModel;
    private List<WellnessEntry> wellnessList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = Calendar.getInstance();
        editTextWaterIntake = findViewById(R.id.editTextWaterIntake);
        editTextSleepHours = findViewById(R.id.editTextSleepHours);
        buttonAddEntry = findViewById(R.id.buttonAddEntry);
        spinnerExercise = findViewById(R.id.spinnerExercise);
        buttonSetDate = findViewById(R.id.buttonSetDate);
        progressBar = findViewById(R.id.progressBar);
        recyclerViewEntries = findViewById(R.id.recyclerViewEntries);

        wellnessList = new ArrayList<>();
        wellnessAdapter = new WellnessAdapter(this, wellnessList);
        recyclerViewEntries.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEntries.setAdapter(wellnessAdapter);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.getAllEntries().observe(this, entries -> {
            wellnessList.clear();
            wellnessList.addAll(entries);
            wellnessAdapter.notifyDataSetChanged();
            updateProgressBar();
        });

        progressBar.setMax(100);

        buttonAddEntry.setOnClickListener(view -> {
            String waterIntake = editTextWaterIntake.getText().toString().trim();
            String sleepHours = editTextSleepHours.getText().toString().trim();
            String exercise = spinnerExercise.getSelectedItem().toString();

            if (!waterIntake.isEmpty() && !sleepHours.isEmpty() && date != null) {
                WellnessEntry newEntry = new WellnessEntry(waterIntake, sleepHours, exercise, date);
                viewModel.insert(newEntry);
                clearFields();
                Snackbar.make(view, "Entry added", Snackbar.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            }
        });

        buttonSetDate.setOnClickListener(v -> showDatePickerDialog());
    }

    public void updateProgressBar() {
        int completedEntries = 0;
        for (WellnessEntry entry : wellnessList) {
            if (entry.isCompleted()) {
                completedEntries++;
            }
        }
        if (wellnessList.size() > 0) {
            int progressPercentage = (int) ((completedEntries / (float) wellnessList.size()) * 100);
            progressBar.setProgress(progressPercentage);
        } else {
            progressBar.setProgress(0);
        }
    }

    private void clearFields() {
        editTextWaterIntake.setText("");
        editTextSleepHours.setText("");
        date = null;
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            date = Calendar.getInstance();
            date.set(year1, month1, dayOfMonth);
        }, year, month, day);
        datePickerDialog.show();
    }

    public void refreshList() {
        viewModel.getAllEntries().observe(this, entries -> {
            wellnessList.clear();
            wellnessList.addAll(entries);
            wellnessAdapter.notifyDataSetChanged();
            updateProgressBar();
        });
    }

}
