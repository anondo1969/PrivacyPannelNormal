package app.android.tanzi.com.privacypannel3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TransparencyControlActivity extends AppCompatActivity {

    String[] transparency_control = {
            "Applications",
            "Permissions"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparency_control);

        final TransparencyControlAdapter transparencyControlAdapter = new TransparencyControlAdapter(this, transparency_control);

        ListView transparencyListView = (ListView) findViewById(R.id.transparency_control_list_view);

        transparencyListView.setAdapter(transparencyControlAdapter);

        transparencyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = transparencyControlAdapter.getItem(position);

                String str = (String) item;

                if (str == "Applications") {
                    Intent intent = new Intent(getApplicationContext(), ApplicationListActivity.class);
                    //based on item add info to intent
                    startActivity(intent);
                }else if(str == "Permissions"){
                    Intent intent = new Intent(getApplicationContext(), ApplicationPermissionActivity.class);
                    //based on item add info to intent
                    startActivity(intent);
                }
                else{
//                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                }
            }
        });
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
}