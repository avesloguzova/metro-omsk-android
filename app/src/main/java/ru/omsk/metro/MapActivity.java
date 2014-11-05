package ru.omsk.metro;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.simple.JSONObject;


public class MapActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void processLoadResult(LoadResult result){


    }
    public void loadDataProcess() {
        //TODO progress bar

        new LoadServiceGateway(this).execute();
    }
    private class LoadServiceGateway extends AsyncTask<Void, Void,LoadResult> {
        private Context context;

        private LoadServiceGateway(Context context) {
            this.context = context;
        }

        @Override
        protected LoadResult doInBackground(Void... voids) {
            try {
                return new LoadService(context).load();
            } catch (LoadServiceException e) {
                return new LoadResult(false,e.getMessage());
            }
        }
        @Override
        protected void onPostExecute(LoadResult result) {
            if (!result.isSuccess()) {
                Toast.makeText(context, result.getExceptMessage(), Toast.LENGTH_LONG).show();
            } else {
                processLoadResult(result);
            }
        }
    }
}