package app.android.tanzi.com.privacypannel3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

public class ApplicationDetailsActivity extends AppCompatActivity {

    ApplicationViewController data_source = new ApplicationViewController(this);

    String[] dummy= {"this", "is", "dummy"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_details);


        Intent intent = getIntent();
        final String appName = intent.getExtras().getString("ApplicationName");

        String[] permissionsArray = data_source.loadApplicationBasedPermissionNames(appName);

        final ApplicationDetailsAdapter applicationDetailsAdapter = new ApplicationDetailsAdapter(this, permissionsArray);
        ListView applicationDetailsView = (ListView) findViewById(R.id.application_details_view);
        applicationDetailsView.setAdapter(applicationDetailsAdapter);

//        applicationDetailsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Object item = applicationDetailsAdapter.getItem(position);
//
//                String str = (String) item;
//
//                Toast.makeText(getApplicationContext(), appName, Toast.LENGTH_SHORT).show();
//            }
//        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(appName); // to change the tool bar title
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
}
