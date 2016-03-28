package app.android.tanzi.com.privacypannel3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ApplicationPermissionActivity extends AppCompatActivity {

    ApplicationViewController data_source = new ApplicationViewController(this);

    String[] dummy= {"this", "is", "dummy"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_permission);

        String[] permissionNames = data_source.loadPermissionName();

        final ApplicationPermissionAdapter applicationPermissionAdapter = new ApplicationPermissionAdapter(this, permissionNames);
        ListView applicationPermissionView = (ListView) findViewById(R.id.application_permission_list_view);
        applicationPermissionView.setAdapter(applicationPermissionAdapter);

        applicationPermissionView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = applicationPermissionAdapter.getItem(position);

                String str = (String) item;

                Intent intent = new Intent(getApplicationContext(), PermissionSpecificApplicationActivity.class);
                intent.putExtra("PermissionName", str);
                //based on item add info to intent
                startActivity(intent);
//
//                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Application Permissions"); // to change the tool bar title
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
    /*************enabling Back Navigation *******************/
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
