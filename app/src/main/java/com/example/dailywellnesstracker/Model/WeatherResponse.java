package com.example.dailywellnesstracker.Model;

import java.util.List;

public class WeatherResponse {

    private Main main;
    private List<Weather> weather;

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public class Main {
        private float temp;
        private float humidity;

        public float getTemp() {
            return temp;
        }

        public float getHumidity() {
            return humidity;
        }
    }

    public class Weather {
        private String description;

        public String getDescription() {
            return description;
        }
    }
}
