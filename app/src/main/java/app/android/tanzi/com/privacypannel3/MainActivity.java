package app.android.tanzi.com.privacypannel3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        /************************For loading the required data start*********************/

        ApplicationViewController appData = new ApplicationViewController(this);
        if (getPreferences(MODE_PRIVATE).getBoolean("is_first_run", true)) {
//            Log.d("InsideMain: ", "nothing");
            //for executing only once
            getPreferences(MODE_PRIVATE).edit().putBoolean("is_first_run", false).commit();

            //load package data and save in database. only once in whole  lifetime
            appData.LoadAndSaveAllApplicationInfo();
        }
        /************************For loading the required data end*********************/
        SharedPreferences get_user_profile = getSharedPreferences("USER_PROFILE_PREFS_NAME", MODE_PRIVATE); // get
        int id = get_user_profile.getInt("Radio Button Id:", 0); //0 is the default value.

        ProfileSettings profileSettings = new ProfileSettings();
        profileSettings.setProfile(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
