package com.example.currentweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    EditText city;
    TextView temperaturetv;
    TextView pressuretv;
    TextView humiditytv;
    private final String url = "https:/api.openweathermap.org/data/2.5/weather";
    private final String appid = "32e7a01615aea3aa9ca33b7bd1433c44";
    DecimalFormat df = new DecimalFormat(("#.##"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city = findViewById(R.id.cityEditText);
        temperaturetv = findViewById(R.id.temperatureTextView);
        pressuretv = findViewById(R.id.pressureTextView);
        humiditytv = findViewById(R.id.humidityTextView);
    }

    public void getWeather(View view) {
        String urlResult = "";
        String cityString = city.getText().toString().trim();
        if (cityString.equals("")) {
            temperaturetv.setText("Nem írt be semmilyen város nevet");
            humiditytv.setText("");
            pressuretv.setText("");
        } else {
            urlResult = url + "?q=" + cityString + "&appid=" + appid;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlResult, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    double temp = jsonObjectMain.getDouble("temp") - 273.15;
                    float pressure = jsonObjectMain.getInt("pressure");
                    int humidity = jsonObjectMain.getInt("humidity");
                    temperaturetv.setText("Hőmérséklet = " + df.format(temp)+ "°C");
                    pressuretv.setText("Nyomás = " + pressure + "hPa");
                    humiditytv.setText("Páratartalom = " + humidity + "%");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}