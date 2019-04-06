package com.voltrox.atom.utilities;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ContentHandler;
import java.net.HttpURLConnection;

public class OpenWeatherJsonUtils {

    /*Method to parse JSON data obtained from URL*/
    public static String getSimpleWeatherStringsFromJson(Context context, String forecastJsonStr)
        throws JSONException {

        /* Weather information. Each day's forecast info is an element of the "list" array */
        final String OWM_LIST = "list";

        /* All temperatures are children of the "temp" object */
        final String OWM_TEMPERATURE = "temp";

        /* Max temperature for the day */
        final String OWM_MAX = "max";
        final String OWM_MIN = "min";

        final String OWM_HUMIDITY = "humidity";
        final String OWM_PRESSURE = "pressure";

        final String OWM_WEATHER = "weather";
        final String OWM_DESCRIPTION = "main";

        final String OWM_MESSAGE_CODE = "cod";

        /* String array to hold each day's weather String */
        String parsedWeatherData = null;

        JSONObject forecastJson = new JSONObject(forecastJsonStr);



        JSONObject weatherDescription = forecastJson.getJSONObject(OWM_DESCRIPTION);
        String temperature = weatherDescription.getString(OWM_TEMPERATURE);


        String humidity = weatherDescription.getString(OWM_HUMIDITY);
        String pressure = weatherDescription.getString(OWM_PRESSURE);
        parsedWeatherData = "Temperature " + temperature + "\n" +
                            "Humidity " + humidity + "\n" +
                            "Pressure" + pressure;

        return parsedWeatherData;
    }
}
