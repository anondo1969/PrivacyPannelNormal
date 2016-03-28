package app.android.tanzi.com.privacypannel3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class AppDeleteReceiver extends BroadcastReceiver {
    Context context = AppContext.getContext();
    final PackageManager pm = context.getPackageManager();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getData() == null) {
            return;
        }

        final String packageName = intent.getData().getSchemeSpecificPart();

        Toast.makeText(context, "An App deleted: " + packageName, Toast.LENGTH_LONG).show();

        //fetch the UID of the packagename
        final DatabaseHandler db = new DatabaseHandler(context);
        ApplicationInfoController applicationInfo = db.getApplicationInfo(packageName);

        //delete row from package table
        db.deleteApplicationInfo(new ApplicationInfoController(packageName));

        //delete row from permission table
        db.deletePermissionInfo(new PermissionInfoController(applicationInfo.getUID()));

        db.close();
    }
}
