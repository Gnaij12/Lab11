package com.example.lab11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    private TextView date,time,day,numDaysWeeks,percentDone;
    private String[] dayToString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        day = findViewById(R.id.day);
        numDaysWeeks = findViewById(R.id.numDaysWeeks);
        percentDone = findViewById(R.id.percentDone);
        dayToString = new String[]{"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        getData();
    }

    private void getData() {
        String myUrl = "https://worldtimeapi.org/api/ip";
        StringRequest myRequest = new StringRequest(Request.Method.GET, myUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            //Create a JSON object containing information from the API.
                            JSONObject myJsonObject = new JSONObject(response.toString());
                            String datetime = myJsonObject.getString("datetime");
                            date.setText(datetime.substring(0,datetime.indexOf("T")));
                            String abbreviation = myJsonObject.getString("abbreviation");
                            time.setText(datetime.substring(datetime.indexOf("T")+1,datetime.indexOf(".")) + " " + abbreviation);
                            day.setText(dayToString[myJsonObject.getInt("day_of_week")]);
                            String weeks = myJsonObject.getString("week_number");
                            String days = myJsonObject.getString("day_of_year");
                            numDaysWeeks.setText(weeks + " Weeks,\n" + days + " Days into Year");
                            percentDone.setText(Math.round(Integer.valueOf(days)/365.0*100) + "% Done with Year");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println("yo");
                        Toast.makeText(
                                MainActivity.this,
                                volleyError.getMessage(),
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(myRequest);

    }

}