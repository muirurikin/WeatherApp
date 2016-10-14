package com.alexona.weatherapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.DateFormat;
import android.icu.text.DecimalFormat;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import Util.Utils;
import data.JSONWeatherParser;
import data.WeatherHttpClient;
import model.Weather;

public class MainActivity extends AppCompatActivity {

    private TextView cityName;
    private TextView temp;
    private ImageView iconView;
    private TextView description;
    private TextView humididty;
    private TextView pressure;
    private TextView wind;
    private TextView sunrise;
    private TextView sunset;
    private TextView updated;

    Weather weather = new Weather();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = (TextView) findViewById(R.id.cityText);
        temp = (TextView) findViewById(R.id.tempText);
        iconView = (ImageView) findViewById(R.id.thumbnailIcon);
        description = (TextView) findViewById(R.id.cloudText);
        humididty = (TextView) findViewById(R.id.humidText);
        pressure = (TextView) findViewById(R.id.pressureText);
        wind = (TextView) findViewById(R.id.windText);
        sunrise = (TextView) findViewById(R.id.riseText);
        sunset = (TextView) findViewById(R.id.setText);
        updated = (TextView) findViewById(R.id.updateText);

        renderWeatherData("Nairobi,KE");

    }


    public void renderWeatherData(String city) {

        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(new String[]{city + "&units=metric"});

    }

    private class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImage(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            iconView.setImageBitmap(bitmap);
//            super.onPostExecute(bitmap);
        }

        private Bitmap downloadImage(String code) {
            final DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

//            final HttpGet getRequest = new HttpGet(Utils.ICON_URL + code + ".png");
            final HttpGet getRequest = new HttpGet("http://www.9ori.com/store/media/images/8ab579a656.jpg");

            try {
            HttpResponse response = client.execute(getRequest);

                final int statusCode = response.getStatusLine().getStatusCode();

                if ( statusCode != HttpStatus.SC_OK) {
                    Log.e("DownloadImage", "Error:" + statusCode);
                }
                final HttpEntity entity = response.getEntity();

                if (entity != null) {
                    InputStream inputStream = null;

                    inputStream = entity.getContent();

                    //Decode contents from the stream
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
        } catch (IOException) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class WeatherTask extends AsyncTask<String, Void, Weather> {
        @Override
        protected Weather doInBackground(String... params) {

            String data = ( (new WeatherHttpClient()).getWeatherData(params[0]));

            weather.iconData = weather.currentCondition.getIcon();

            weather = JSONWeatherParser.getWeather(data);

            Log.v("Data: ", weather.currentCondition.getDescription());

            new DownloadImageAsyncTask().execute(weather.iconData);


            return weather;

        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            DateFormat df = DateFormat.getTimeInstance();

            String sunriseDate = df.format(new Date(weather.place.getSunrise()));
            String sunsetDate = df.format(new Date(weather.place.getSunset()));
            String updateDate = df.format(new Date(weather.place.getLastupdate()));


            DecimalFormat decimalFormat = new DecimalFormat("#.#");

            String tempFormat = decimalFormat.format(weather.currentCondition.getTemperature());

            cityName.setText(weather.place.getCity() + "," + weather.place.getCountry());
            temp.setText("" + tempFormat + "°C");
            humididty.setText("Humidity: " + weather.currentCondition.getHumidity() + "%");
            pressure.setText("Pressure: " + weather.currentCondition.getPressure() + "hPa");
            wind.setText("Wind: " + weather.wind.getSpeed() + "mps");
            sunrise.setText("Sunrise: " + sunriseDate);
            sunset.setText("Sunset: " + sunsetDate);
            updated.setText("Last Updated: " + updateDate);
            description.setText("Condition: " + weather.currentCondition.getCondition() + "(" +
                    weather.currentCondition.getDescription() + ")");
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the Menu -> Adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle action bar item clicks here.


        int id = item.getItemId();

        // No  inspection simplifiableIf Statement
        if (id == R.id.change_cityId) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
