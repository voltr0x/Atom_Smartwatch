package com.voltrox.atom;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.voltrox.atom.data.AtomPreferences;
import com.voltrox.atom.utilities.NetworkUtils;
import com.voltrox.atom.utilities.OpenWeatherJsonUtils;

import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView mWeatherTextView;
    TextView mLowTempTextView;
    TextView mHumidTextView;
    TextView mPressureTextView;
    TextView mSpeedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Display date*/
        Calendar mCalendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        TextView displayDate = findViewById(R.id.showDate);
        displayDate.setText(currentDate);

        /*Open time and alarm settings*/
        TextView displayTime = findViewById(R.id.showTime);
        displayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                //Change the settings activity page
            }
        });

        /*Display weather on textview*/
        mWeatherTextView = findViewById(R.id.high_temperature);
        mLowTempTextView = findViewById(R.id.low_temperature);
        mHumidTextView = findViewById(R.id.showHumid);
        mPressureTextView = findViewById(R.id.showPressure);
        mSpeedTextView = findViewById(R.id.showSpeed);
        loadWeatherData();
    }

    /*Function to load weather data*/
    private void loadWeatherData(){
        //Location ID - may be added in settings to change

        String location = AtomPreferences.getPreferredWeatherLocation(this);
        new FetchWeatherTask().execute(location);
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {
            //if no zip code, theres nothing to look up
            if (params.length == 0){
                return null;
            }

            String location = params[0];
            URL weatherRequestUrl = NetworkUtils.buildUrl(location);

            try{
                String jsonWeatherResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);

                String[] simpleJsonWeatherData = OpenWeatherJsonUtils
                        .getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);

                return simpleJsonWeatherData;

            }catch (Exception e){
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String[] weatherData) {
            if (weatherData != null) {
                    String tv_temp = weatherData[0];
                    String tv_humid = weatherData[1];
                    String tv_pressure = weatherData[2];
                    String tv_wind_speed = weatherData[3];
                    String tv_low = weatherData[4];

                    mWeatherTextView.setText(tv_temp);
                    mLowTempTextView.setText(tv_low);
                    mHumidTextView.setText(tv_humid);
                    mPressureTextView.setText(tv_pressure + " mbar");
                    mSpeedTextView.setText(tv_wind_speed + " kmph");

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_settings:
                Intent startSettingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(startSettingsActivity);
                return true;
            case R.id.menu_about:
                Intent startMenuActivity = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(startMenuActivity);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}