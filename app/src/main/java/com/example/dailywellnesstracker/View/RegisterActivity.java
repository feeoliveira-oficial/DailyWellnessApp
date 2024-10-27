package com.example.dailywellnesstracker.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.dailywellnesstracker.Model.User;
import com.example.dailywellnesstracker.Model.WellnessEntry;
import com.example.dailywellnesstracker.R;
import com.example.dailywellnesstracker.ViewModel.MainActivityViewModel;
import com.example.dailywellnesstracker.ViewModel.UserViewModel;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextAge, editTextHeight, editTextWeight;
    private Spinner spinnerGender;
    private Button buttonRegister;
    private UserViewModel userViewModel;
    private ArrayAdapter<CharSequence> adapter;

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
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        spinnerGender = findViewById(R.id.spinnerGender);
        buttonRegister = findViewById(R.id.buttonRegister);

        RecyclerView recyclerViewEntries = findViewById(R.id.recyclerViewEntries);
        recyclerViewEntries.setLayoutManager(new LinearLayoutManager(this));

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        int userId = getIntent().getIntExtra("userId", -1);

        adapter = ArrayAdapter.createFromResource(
                this, R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        if (userId != -1) {
            userViewModel.getUserById(userId).observe(this, user -> {
                if (user != null) {
                    editTextName.setText(user.getUsername());
                    editTextAge.setText(user.getAge());
                    editTextHeight.setText(user.getHeight());
                    editTextWeight.setText(user.getWeight());
                    spinnerGender.setSelection(adapter.getPosition(user.getGender()));
                }
            });

        }

        buttonRegister.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String age = editTextAge.getText().toString();
            String height = editTextHeight.getText().toString();
            String weight = editTextWeight.getText().toString();
            String gender = spinnerGender.getSelectedItem().toString();

            if (name.isEmpty() || age.isEmpty() || height.isEmpty() || weight.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            } else {
                User newUser = new User(name, age, height, weight, gender);
                userViewModel.insertUser(newUser);

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button buttonUsers = findViewById(R.id.buttonUsers);
        buttonUsers.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, UserListActivity.class);
            startActivity(intent);
        });


    }

}
