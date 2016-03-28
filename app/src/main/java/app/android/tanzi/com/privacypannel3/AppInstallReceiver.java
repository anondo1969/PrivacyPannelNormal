package app.android.tanzi.com.privacypannel3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class AppInstallReceiver extends BroadcastReceiver {
    Context context = AppContext.getContext();
    final PackageManager pm = context.getPackageManager();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getData() == null) {
            return;
        }

        //fetch the package name from intent
        final String packageName = intent.getData().getSchemeSpecificPart();

        try {
            final ApplicationInfo appInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);

            //saving data in package table
            //---------------------------START---------------------------------------
            final DatabaseHandler db = new DatabaseHandler(context);

            //converting char sequence to string for app name
            final StringBuilder sb = new StringBuilder(appInfo.loadLabel(pm).length());
            sb.append(appInfo.loadLabel(pm));

            //inserting data in package table
            db.addApplicationInfo(new ApplicationInfoController(appInfo.uid, sb.toString(), appInfo.packageName, "0"));
            //-------------------------------END--------------------------------------

            //saving data in permission Table
            //-----------------------------START-------------------------------------------------------------------------
            StringBuffer permissions = new StringBuffer();

            try {
                PackageInfo packageInfo = pm.getPackageInfo(appInfo.packageName, PackageManager.GET_PERMISSIONS);

                String[] requestedPermissions = packageInfo.requestedPermissions;
                if (requestedPermissions != null) {
                    for (int i = 0; i < requestedPermissions.length; i++) {
                        permissions.append(requestedPermissions[i] + "\n");


                        //insert data in permission table
//                        Log.d("Insert: ", "Inserting ..");
                        db.addPermissionInfo(new PermissionInfoController(appInfo.uid, requestedPermissions[i]));


                    }
//                    Log.d("Inside pkview", "Permissions: \n" + permissions);
//                    Log.d("Inside pkview", "End: ----------------------------");

                } else {
                    //no permission
//                    Log.d("Inside pkview", "Total Permissions: 0 " );

                }


            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            db.close();
            //------------------------------END--------------------------------------------------------------------------

            Toast.makeText(context, "New App installed: "+appInfo.loadLabel(pm) +" "+ packageName +" "+ appInfo.uid
                    + appInfo.sourceDir, Toast.LENGTH_LONG).show();

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "Sorry, no info: "+ packageName, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
