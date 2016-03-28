package app.android.tanzi.com.privacypannel3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Set;

public class LocationAccuracyViewActivity extends AppCompatActivity {

    String[] dummy = {"the","la", "Blau","khau"};
    int[] dummy2 = {0,2,1,3};
    boolean[] dummy3={true,false,false,true};
    SharedPreferences.Editor location_accuracy_editor;
    SharedPreferences prefs;
    ArrayList<Boolean> locationList = new ArrayList <Boolean>();;
    Set<String> appNamesSet;
    Set<String> appNamesGet;
    ArrayList<String> appNamesList;
    int count =0;
    int pos=-1;

    ApplicationViewController data_source = new ApplicationViewController(this);
    LocationAccuracyViewAdapter locationAccuracyViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_accuracy_view);

        String[] application_list = data_source.retrieveLocationPermissionUsedAppsInformation();

        count = application_list.length;

        /***********************to save set check**********************/
        if (getPreferences(MODE_PRIVATE).getBoolean("is_first_runn", true)) {
//            Log.d("OneTime: ", "enter");
            //for executing only once
            getPreferences(MODE_PRIVATE).edit().putBoolean("is_first_runn", false).commit();

            for (int i = 0; i < count; i++) {
                //locationList.set(i, "false");
                locationList.add(false);
            }
//            Log.d("TanziLogg", Integer.toString(locationList.size()));
        }
        else{
//            Log.d("OneTime: ", "not");




            //retrieve data from shared preference and assigned it to location arraylist

            appNamesList = getStringArrayPref(this, "LocationBasedApps");

            for (pos = 0; pos < appNamesList.size(); pos++) {
                //locationList.set(i, "false");
                locationList.add(false);
            }
            pos=-1;

            for(String i : appNamesList){
                pos++;

                if(i.equals("true")) {
                    locationList.set(pos, true);
                }
                else{
                    locationList.set(pos, false);
                }
            }
        }



        /******new CustomAdapter with passing Shared Preference Editor*********/

        locationAccuracyViewAdapter = new LocationAccuracyViewAdapter(this, application_list,  locationList);

        ListView locationListView = (ListView) findViewById(R.id.location_accuracy_list_view);

        View header = (View) getLayoutInflater().inflate(R.layout.location_accuracy_view_header,null);

        locationListView.addHeaderView(header);
        locationListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        locationListView.setAdapter(locationAccuracyViewAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    /*************enabling Back Navication *******************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
//        Log.d("Anondo", "Inside onPause");
        int pos=-1;
        Boolean[] values = locationAccuracyViewAdapter.saveValues();

        /********Sending user fake gps input to database********/

        ApplicationViewController data_source = new ApplicationViewController(this);
        String[] application_list = data_source.retrieveLocationPermissionUsedAppsInformation();

        data_source.changeLocationCheckValues(application_list, values);


//        Toast.makeText(this, "value saved", Toast.LENGTH_SHORT).show();
//
//        for(Boolean i : values){
//
//            Log.d("Anondo", Boolean.toString(i));
//        }

//the list containing all checkbox information


        appNamesList = new ArrayList<String>();

        for(Boolean i : values){
            pos++;
            appNamesList.add(Boolean.toString(i));

        }


//        for(String j : appNamesSet){
//
//            Log.d("xxx", j);
//
//        }

        setStringArrayPref(this, "LocationBasedApps", appNamesList);
    }

    public static void setStringArrayPref(Context context, String key, ArrayList<String> values) {
        SharedPreferences prefs = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();

        //
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.commit();
    }

    public static ArrayList<String> getStringArrayPref(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("LocationBasedApps", Context.MODE_PRIVATE);
        String json = prefs.getString(key, null);
        ArrayList<String> urls = new ArrayList();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }
}
