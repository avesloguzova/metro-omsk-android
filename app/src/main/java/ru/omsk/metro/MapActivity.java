package ru.omsk.metro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import ru.omsk.metro.gui.SubwayView;
import ru.omsk.metro.model.SubwayMap;
import ru.omsk.metro.net.LoadResult;
import ru.omsk.metro.net.LoadService;
import ru.omsk.metro.net.LoadServiceException;


public class MapActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataProcess();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, RESULT_OK);
        }

        return super.onOptionsItemSelected(item);
    }

    private void processLoadResult(@NotNull LoadResult result) {
        if (result.isSucceeded()) {
            SubwayView view = (SubwayView) findViewById(R.id.subwayView);
            view.setMap(SubwayMap.fromJSON(result.getJSON()));
        }
    }

    public void loadDataProcess() {
        //TODO progress bar
        SharedPreferences preferences = getSharedPreferences(getString(R.string.PREF_NAME), MODE_PRIVATE);
        new LoadServiceGateway(this, preferences.getInt(getString(R.string.PREF_KEY_CITY), 2)).execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        loadDataProcess();
    }

    private class LoadServiceGateway extends AsyncTask<Void, Void, LoadResult> {

        @NotNull
        private final Context context;
        private final int id;

        private LoadServiceGateway(@NotNull Context context, int id) {
            this.context = context;
            this.id = id;
        }

        @Override
        protected LoadResult doInBackground(Void... voids) {
            try {
                return new LoadService(context).load(id);
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
