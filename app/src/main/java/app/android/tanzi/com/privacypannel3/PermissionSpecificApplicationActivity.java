package app.android.tanzi.com.privacypannel3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class PermissionSpecificApplicationActivity extends AppCompatActivity {

    ApplicationViewController data_source = new ApplicationViewController(this);

    String[] dummy= {"this", "is", "dummy"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_specific_application);

        Intent intent = getIntent();
        final String permissionName = intent.getExtras().getString("PermissionName");

        String[] appNames = data_source.loadPermissionBasedAppNames(permissionName);

        final PermissionSpecificApplicationAdapter permissionSpecificApplicationAdapter = new PermissionSpecificApplicationAdapter(this, appNames);
        ListView permissionSpecificApplicationView = (ListView) findViewById(R.id.permission_specific_application_list_view);
        permissionSpecificApplicationView.setAdapter(permissionSpecificApplicationAdapter);

        permissionSpecificApplicationView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = permissionSpecificApplicationAdapter.getItem(position);

                String str = (String) item;

//                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(permissionName); // to change the tool bar title
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
