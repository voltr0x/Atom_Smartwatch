package com.voltrox.atom.utilities;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class OpenWeatherJsonUtils {

    public static String[] sendWeatherData;
    /*Method to parse JSON data obtained from URL*/
    public static String[] getSimpleWeatherStringsFromJson(Context context, String forecastJsonStr)
        throws JSONException {

        /* All temperatures are children of the "temp" object */
        final String OWM_TEMPERATURE = "temp";

        /* Max temperature for the day */
        final String OWM_MAX = "temp_max";
        final String OWM_MIN = "temp_min";

        final String OWM_HUMIDITY = "humidity";
        final String OWM_PRESSURE = "pressure";
        final String OWM_DESCRIPTION = "main";

        final String OWM_WIND = "wind";
        final String OWM_WSPEED = "speed";

        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONObject weatherDescription = forecastJson.getJSONObject(OWM_DESCRIPTION);
        String temperature = weatherDescription.getString(OWM_TEMPERATURE);
        String humidity = weatherDescription.getString(OWM_HUMIDITY);
        String pressure = weatherDescription.getString(OWM_PRESSURE);
        String low = weatherDescription.getString(OWM_MIN);

        JSONObject windDescription = forecastJson.getJSONObject(OWM_WIND);
        String windspeed = windDescription.getString(OWM_WSPEED);

        return sendWeatherData = new String[]{temperature, humidity, pressure, windspeed, low};
    }
}
