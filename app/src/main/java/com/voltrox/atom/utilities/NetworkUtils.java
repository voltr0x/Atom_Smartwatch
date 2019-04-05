package com.voltrox.atom.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String FORECAST_BASE_URL =
            "https://api.openweathermap.org/data/2.5/weather?";

    //Units we want our API to return - can change in settings if want
    private static final String units = "metric";
    //Type of data format
    private static final String format = "json";
    //Number of days we want our API to return
    private static final int numDays = 7;
    //API key
    private static final String apiKey = "46f9ba47a09eba74fda4147aa356bc59";

    final static String QUERY_PARAM = "id";
    final static String UNITS_PARAM = "units";
    final static String FORMAT_PARAM = "mode";
    final static String DAYS_PARAM = "cnt";
    final static String KEY_PARAM = "appid";

    public static URL buildUrl (String locationQuery){
        Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, locationQuery)
                .appendQueryParameter(UNITS_PARAM, units)
                .appendQueryParameter(FORMAT_PARAM, format)
                .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                .appendQueryParameter(KEY_PARAM, apiKey)
                .build();

        URL url = null;
        try{
            url = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }

    public static String getResponseFromHttpUrl (URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput){
                return scanner.next();
            }else{
                return null;
            }
        }finally {
            urlConnection.disconnect();
        }
    }
}
