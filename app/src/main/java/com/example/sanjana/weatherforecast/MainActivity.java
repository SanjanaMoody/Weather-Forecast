package com.example.sanjana.weatherforecast;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {

    public EditText street;
    public EditText city;
    public RadioButton celsius;
    public RadioButton fahrenheit;
    public String streetInfo;
    public String cityInfo;
    public String stateInfo;
    public String degree;
    public String url;
    private Spinner state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner staticSpinner = (Spinner) findViewById(R.id.StateVal);


        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.country_arrays,
                        android.R.layout.simple_spinner_item);


        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        staticSpinner.setAdapter(staticAdapter);

        street = (EditText)findViewById(R.id.StreetVal);
        city = (EditText)findViewById(R.id.CityVal);
        state = (Spinner)findViewById(R.id.StateVal);
        celsius = (RadioButton) findViewById(R.id.radioCelsius);
        fahrenheit = (RadioButton) findViewById(R.id.radioFahrenheit);
        findViewById(R.id.clearBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                street.setText("");
                city.setText("");
                state.setSelection(0);
                celsius.setChecked(false);
                fahrenheit.setChecked(true);
            }

        });
        findViewById(R.id.AboutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
            }

        });

        findViewById(R.id.searchBtn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                streetInfo = street.getText().toString();
                cityInfo = city.getText().toString();
                stateInfo = "";
                degree = "";
                url = "";

                if(streetInfo.equals("")) {
                    Toast.makeText(MainActivity.this,
                            "Please enter street address", Toast.LENGTH_LONG).show();
                    return;
                }
                if(cityInfo.equals("")) {
                    Toast.makeText(MainActivity.this,
                            "Please enter city name", Toast.LENGTH_LONG).show();
                    return;
                }
                int pos =state.getSelectedItemPosition();
                if(pos!=0)
                {
                    stateInfo = state.getSelectedItem().toString();
                }
                else{
                    Toast.makeText(MainActivity.this,
                            "Please select a state", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!stateInfo.equals("Select State"))
                {
                    stateInfo = state.getSelectedItem().toString();
                    Map<String,String> statesValues = new HashMap<String, String>();
                    statesValues.put("Alabama","AL");
                    statesValues.put("Alaska","AK");
                    statesValues.put("Arizona","AZ");
                    statesValues.put("Arkansas","AR");
                    statesValues.put("California","CA");
                    statesValues.put("Colorado","CO");
                    statesValues.put("Connecticut","CI");
                    statesValues.put("Delaware","DE");
                    statesValues.put("District of Columbia","DC");
                    statesValues.put("Florida","FL");
                    statesValues.put("Georgia","GA");
                    statesValues.put("Hawaii","HI");
                    statesValues.put("Idaho","ID");
                    statesValues.put("Illinois","IL");
                    statesValues.put("Indiana","IN");
                    statesValues.put("Iowa","IA");
                    statesValues.put("Kansas","KS");
                    statesValues.put("Kentucky","KY");
                    statesValues.put("Loiusiana","LA");
                    statesValues.put("Maine","ME");
                    statesValues.put("Maryland","MD");
                    statesValues.put("Massachusetts","MA");
                    statesValues.put("Michigan","MI");
                    statesValues.put("Minnesota","MN");
                    statesValues.put("Mississippi","MS");
                    statesValues.put("Missouri","MO");
                    statesValues.put("Montana","MT");
                    statesValues.put("Nebraska","NE");
                    statesValues.put("Nevada","NV");
                    statesValues.put("New Hampshire","NH");
                    statesValues.put("New Jersey","NJ");
                    statesValues.put("New Mexico","NM");
                    statesValues.put("New York","NY");
                    statesValues.put("North Carolina","NC");
                    statesValues.put("North Dakota","ND");
                    statesValues.put("Ohio","OH");
                    statesValues.put("Oklahoma","OK");
                    statesValues.put("Oregon","OR");
                    statesValues.put("Pennsylvania","PA");
                    statesValues.put("Rhode Island","RI");
                    statesValues.put("South Carolina","SC");
                    statesValues.put("South Dakota","SD");
                    statesValues.put("Tennessee","TN");
                    statesValues.put("Texas","TX");
                    statesValues.put("Utah","UT");
                    statesValues.put("Vermont","VT");
                    statesValues.put("Virginia","VA");
                    statesValues.put("Washington","WA");
                    statesValues.put("West Virginia","WV");
                    statesValues.put("Wisconsin","WI");
                    statesValues.put("Wyoming","WY");

                    stateInfo = statesValues.get(stateInfo);
                }
                else{
                    Toast.makeText(MainActivity.this, "Please select a state", Toast.LENGTH_LONG).show();
                    return;
                }
                if(celsius.isChecked()){
                    degree = "si";
                }
                else if(fahrenheit.isChecked()){
                    degree = "us";
                }
                try{
                    String param="streetValue="+URLEncoder.encode(streetInfo,"UTF-8")+
                            "&cityValue="+URLEncoder.encode(cityInfo,"UTF-8")+
                            "&stateValue="+URLEncoder.encode(stateInfo,"UTF-8")+
                            "&degree="+URLEncoder.encode(degree,"UTF-8");

                    url="http://webtechnology-env.elasticbeanstalk.com/index.php?"+param;
                    new GetData().execute(url);
                }catch (UnsupportedEncodingException e){
                    Toast.makeText(MainActivity.this, "Unable to contact server. Please try again.", Toast.LENGTH_LONG).show();
                    return;
                }



            }
        });
    }

    private class GetData extends AsyncTask<String,Void, JSONObject>{

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            String response;
            try {

                HttpClient httpClient = new DefaultHttpClient();
                Log.d("AWS Info", params[0]);
                HttpGet httpget = new HttpGet(params[0]);
                HttpResponse res = httpClient.execute(httpget);
                HttpEntity httpEntity = res.getEntity();
                response = EntityUtils.toString(httpEntity);
                Log.d("Response from AWS", response);
                return new JSONObject(response);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(JSONObject result) {
            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            Bundle b = new Bundle(); b.putString("Data",result.toString());
            intent.putExtras(b); intent.putExtra("city", cityInfo);
            intent.putExtra("degree", degree);
            intent.putExtra("state", stateInfo);
            startActivity(intent);
        }

    }
}

