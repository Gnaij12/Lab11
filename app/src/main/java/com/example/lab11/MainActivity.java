package com.example.lab11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    private TextView date,time,day,numDaysWeeks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        day = findViewById(R.id.day);
        numDaysWeeks = findViewById(R.id.numDaysWeeks);
        getData();
    }

    private void getData() {
        String myUrl = "https://corona.lmao.ninja/v2/all";
        StringRequest myRequest = new StringRequest(Request.Method.GET, myUrl,
                response -> {
                    try{
                        //Create a JSON object containing information from the API.
                        JSONObject myJsonObject = new JSONObject(response);
                        String datetime = myJsonObject.getString("datetime");
                        date.setText(datetime.substring(0,datetime.indexOf("T")));
                        time.setText(datetime.substring(datetime.indexOf("T"),datetime.indexOf(".")));
                        day.setText(myJsonObject.getString("day_of_week"));
                        numDaysWeeks.setText(myJsonObject.getString("deaths"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                volleyError -> Toast.makeText(MainActivity.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show()
        );

    }
}