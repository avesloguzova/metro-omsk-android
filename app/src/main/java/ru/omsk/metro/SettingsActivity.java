package ru.omsk.metro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.omsk.metro.model.City;
import ru.omsk.metro.net.LoadResult;
import ru.omsk.metro.net.LoadServiceException;
import ru.omsk.metro.net.MockLoadService;

public class SettingsActivity extends Activity {

    @NotNull
    public static List<City> fromJSON(@NotNull JSONObject lineObject) {
        JSONArray stationsArray = (JSONArray) lineObject.get("cities");

        List<City> cities = new ArrayList<City>();
        for (Object stationObject : stationsArray) {
            cities.add(City.fromJSON((JSONObject) stationObject));
        }

        return cities;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        loadDataProcess();
    }

    private void loadDataProcess() {


        new LoadServiceGateway(this).execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    private void processLoadResult(@NotNull LoadResult result) {

        if (result.getJSON() != null) {
            final ArrayList<String> listItems = new ArrayList<String>();
            final List<City> cities = fromJSON(result.getJSON());
            for (City city : cities) {
                listItems.add(city.getName());
            }
            ArrayAdapter<String> adapter;
            ListView view = (ListView) findViewById(R.id.listView);
            view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    SharedPreferences preferences = getSharedPreferences(getString(R.string.PREF_NAME), MODE_PRIVATE);
                    preferences.edit().putInt(getString(R.string.PREF_KEY_CITY), cities.get(i).getId()).apply();
                    preferences.edit().commit();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
            view.setAdapter(adapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    private class LoadServiceGateway extends AsyncTask<Void, Void, LoadResult> {

        @NotNull
        private final Context context;

        private LoadServiceGateway(@NotNull Context context) {
            this.context = context;
        }

        @Override
        protected LoadResult doInBackground(Void... voids) {
            try {
                return new MockLoadService(context).loadCities();
            } catch (LoadServiceException e) {
                return new LoadResult(e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(@NotNull LoadResult result) {
            if (!result.isSucceeded()) {
                Toast.makeText(context, result.getExceptMessage(), Toast.LENGTH_LONG).show();
            } else {
                processLoadResult(result);
            }
        }
    }


}
