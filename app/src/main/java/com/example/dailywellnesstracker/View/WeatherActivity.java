package com.example.dailywellnesstracker.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dailywellnesstracker.Model.WeatherApiService;
import com.example.dailywellnesstracker.Model.WeatherResponse;
import com.example.dailywellnesstracker.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {

    private EditText editTextCity;
    private TextView textViewWeather;
    private Button buttonFetchWeather;

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
        setContentView(R.layout.activity_weather);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        editTextCity = findViewById(R.id.editTextCity);
        textViewWeather = findViewById(R.id.textViewWeather);
        buttonFetchWeather = findViewById(R.id.buttonFetchWeather);
        buttonFetchWeather.setOnClickListener(v -> fetchWeather());
    }

    private void fetchWeather() {
        String city = editTextCity.getText().toString().trim();
        if (city.isEmpty()) {
            Toast.makeText(this, "Please enter a city", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApiService weatherApiService = retrofit.create(WeatherApiService.class);
        Call<WeatherResponse> call = weatherApiService.getWeather(city, "6dec843a19ad0ca2058e96a1d3c5454c", "metric");
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(WeatherActivity.this, "Error fetching weather!", Toast.LENGTH_SHORT).show();
                    return;
                }

                WeatherResponse weatherResponse = response.body();
                String weatherInfo = "City: " + city + "\n" +
                        "Temp: " + weatherResponse.getMain().getTemp() + "°C\n" +
                        "Humidity: " + weatherResponse.getMain().getHumidity() + "%\n" +
                        "Condition: " + weatherResponse.getWeather().get(0).getDescription();

                textViewWeather.setText(weatherInfo);
            }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t) {
                    Toast.makeText(WeatherActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
