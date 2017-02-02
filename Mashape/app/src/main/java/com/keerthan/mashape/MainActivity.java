package com.keerthan.mashape;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    ProgressDialog progress;
    EditText txtCityInput;
    Button btnCheckResults;
    ArrayList<Country> countryList;
    RecyclerView recyclerView;
    CountryListAdapter countryListAdapter;
    private static String url = "https://restcountries-v1.p.mashape.com/name/";
    String cityname;
    SQLiteDatabase db;
    DBHandler dbHandler = DBHandler.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCityInput = (EditText) findViewById(R.id.editText);
        btnCheckResults = (Button) findViewById(R.id.button);
        List<Country> countriesFromDB = dbHandler.getAllCountries();
        for (Country countries :countriesFromDB)
        {
            Log.d("MainActivity",countries.getCountryName() + " "+ countries.getCountryPopulation());
        }
        btnCheckResults.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        cityname= txtCityInput.getText().toString();
        countryList = new ArrayList<>();
        recyclerView  = (RecyclerView) findViewById(R.id.rView);
        countryListAdapter = new CountryListAdapter(this, countryList, new CountryListAdapter.onClickListener() {
            @Override
            public void onclick(int position) {
                dbHandler.deleteCountries(position);
            }
        });
        new FetchData().execute();
    }

    private class FetchData extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress= new ProgressDialog(MainActivity.this);
            progress.setMessage("Loading.. Please wait !!");
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HTTPHandler httpHandler = HTTPHandler.getInstance();
                String jsonString = httpHandler.makeServiceCall(url+cityname);
                JSONArray countryArray = new JSONArray(jsonString);
                for (int i = 0;i<countryArray.length();i++) {
                    JSONObject c = countryArray.getJSONObject(i);
                    Country country = new Country();
                    if (c.has("name")){
                        String name = c.getString("name");
                        country.setCountryName(name);
                    }
                    if (c.has("capital"))
                    {
                        String capital = c.getString("capital");
                        country.setCountryCapital(capital);
                    }
                    if (c.has("region"))
                    {
                        String region = c.getString("region");
                        country.setCountryRegion(region);
                    }
                    if (c.has("population"))
                    {
                        String population = c.getString("population");
                        country.setCountryPopulation(population);
                    }
                    if (c.has("subregion"))
                    {
                        String subregion = c.getString("subregion");
                        country.setCountrySubRegion(subregion);
                    }
                    if (c.has("latlng"))
                    {
                        String latLang = c.getString("latlng");
                        country.setCountryLatLang(latLang);
                    }
                    if (c.has("topLevelDomain"))
                    {
                        String domain = c.getString("topLevelDomain");
                        country.setCountryDomain(domain);
                    }
                    if (c.has("timezones"))
                    {
                        String timeZone = c.getString("timezones");
                        country.setCountryTimeZone(timeZone);
                    }
                    dbHandler.addCountry(country);
                    countryList.add(country);
                }

            }catch (Exception e)
            {
                e.printStackTrace();
                return  null;
            }finally {
                if (urlConnection !=null)
                {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("MainActivity", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            if (progress.isShowing())
            {
                progress.dismiss();
            }
            if (countryList.size() == 0)
            {
                Toast.makeText(getApplicationContext(),"No data found !!!",Toast.LENGTH_SHORT).show();
            }
            recyclerView.setAdapter(countryListAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        }
    }
}
