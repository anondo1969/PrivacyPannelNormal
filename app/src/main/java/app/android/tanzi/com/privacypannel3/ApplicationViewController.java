package app.android.tanzi.com.privacypannel3;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Entu on 3/20/2016.
 */
public class ApplicationViewController extends ContextWrapper{

   Context context; //1

    DatabaseHandler db ;//2

    //PackageManager pm;

    public ApplicationViewController(Context base) {
        super(base);

        db = new DatabaseHandler(this);

    }



    //search and load all the packages for only one time
    public void LoadAndSaveAllApplicationInfo() {
        final PackageManager pm = this.getPackageManager();
        int class_count = 1;
        int permission_count = 0;
        final List<ApplicationInfo> installedApps = pm.getInstalledApplications(PackageManager.GET_META_DATA);
//        Log.d("Inside pkview ", "before..");
        for (ApplicationInfo app : installedApps) {
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 1) { //to ensure only installed apps
//                Log.d("Inside pkview", "Start: ----------------------------");
//                Log.d("Inside pkview", "Serial: " + class_count++);
//                Log.d("Inside pkview", "Name: " + app.loadLabel(pm)); // App name
//                Log.d("Inside pkview", "Package: " + app.packageName); // package name
//                Log.d("Inside pkview", "UID: " + app.uid); //UID
//                Log.d("Inside pkview", "Directory: " + app.sourceDir);

                //converting char sequence to string for app name
                final StringBuilder sb = new StringBuilder(app.loadLabel(pm).length());
                sb.append(app.loadLabel(pm));


                //insert data in package table

//                Log.d("Insert: ", "Inserting ..");
                db.addApplicationInfo(new ApplicationInfoController(app.uid, sb.toString(), app.packageName, "0"));

                //Permissions:
                StringBuffer permissions = new StringBuffer();

                try {
                    PackageInfo packageInfo = pm.getPackageInfo(app.packageName, PackageManager.GET_PERMISSIONS);

                    String[] requestedPermissions = packageInfo.requestedPermissions;
                    if (requestedPermissions != null) {
                        for (int i = 0; i < requestedPermissions.length; i++) {
                            permissions.append(requestedPermissions[i] + "\n");
                            permission_count++;

                            //insert data in permission table
                            //  if(saveData) { //only save data once if true
//                            Log.d("Insert: ", "Inserting ..");
                            db.addPermissionInfo(new PermissionInfoController(app.uid, requestedPermissions[i]));
                            //  }

                        }
//                        Log.d("Inside pkview", "Total Permissions: " + permission_count);
//                        Log.d("Inside pkview", "Permissions: \n" + permissions);
//                        Log.d("Inside pkview", "End: ----------------------------");
                        permission_count = 0;
                    } else {
                        //no permission
//                        Log.d("Inside pkview", "Total Permissions: " + permission_count);

                    }


                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        db.close();

    }

    // populate the list of apps those are in runtime (current apps)
    public void listActiveApplications(){

        //detecting forground apps
        new AsyncTask<Void, Void, List<ProcessManager.Process>>() {

            long startTime;

            @Override
            protected List<ProcessManager.Process> doInBackground(Void... params) {
                startTime = System.currentTimeMillis();
                return ProcessManager.getRunningApps();
            }

            @Override
            protected void onPostExecute(List<ProcessManager.Process> processes) {
                StringBuilder sb = new StringBuilder();
                sb.append("Execution time: ").append(System.currentTimeMillis() - startTime).append("ms\n");
                sb.append("Running apps:\n");

                for (ProcessManager.Process process : processes) {

                    //check whether it is a system app or not
                    //-------------------------------START---------------------------------------------------------------------

//                    Log.d("Foreground Process: ", "starts");

                    ApplicationInfoController applicationInfo = db.getApplicationInfo(process.uid);


                    if(applicationInfo.getUID()!=0) {
                        sb.append('\n').append(process.name);
                        sb.append('\n').append(process.uid);

//                        String log = "Id: "+applicationInfo.getId()+" ,UID: " + applicationInfo.getUID()
//                                +" ,Name: " + applicationInfo.getAppName() + " ,Package Name: " + applicationInfo.getPackageName()
//                                + " ,Permission Checked: " + applicationInfo.getPermissionChecked();
//                        // Writing Contacts to log
//                        Log.d("Foreground Process: ", log);

                        //---------------------------END------------------------------------------------------------------
                    }
                }
//                Toast.makeText(getApplicationContext(), "Active Apps: " + sb.toString(), Toast.LENGTH_LONG).show();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        db.close();
    }

    //these apps are for testing purposes............

    public void testRetrievingSingleApplication(int UID) {

        //fetching a row
//        Log.d("Inside view ", "before..");
        ApplicationInfoController a = db.getApplicationInfo(UID); //10257

//        String log = "Id: " + a.getId() + " ,UID: " + a.getUID()
//                + " ,Name: " + a.getAppName() + " ,Package Name: " + a.getPackageName()
//                + " ,Permission Checked: " + a.getPermissionChecked();
//        // Writing Contacts to log
//        Log.d("Inside view", log);
        //-----------------------------------------------------------

        db.close();
    }

    public void testDeletingingSingleApplication(int UID) {

        //deleting a row
        db.deleteApplicationInfo(new ApplicationInfoController(10146));

        db.close();
    }

    //1. call to load values in list
    public void testRetrievingAllPackageTable() {

        // Reading all row from package table
//        Log.d("Reading: ", "Reading all contacts..");
        List<ApplicationInfoController> applicationInfo = db.getAllApplicationInfo();

//        for (ApplicationInfoController app : applicationInfo) {
//            String logg = "Id: "+app.getId()+" ,UID: " + app.getUID()
//                    +" ,Name: " + app.getAppName() + " ,Package Name: " + app.getPackageName()
//                    + " ,Permission Checked: " + app.getPermissionChecked();
//            // Writing Contacts to log
//            Log.d("AllData: ", logg);
//        }
        db.close();
    }

    //---------------------------------------------------------------------------------------------------------------
    //load all the applications name which uses location permission
    public String[] retrieveLocationPermissionUsedAppsInformation(){

        List<String> appNames = new ArrayList<String>();

        // Reading all row from package table
//        Log.d("Reading: ", "Reading all contacts in location method..");
        List<ApplicationInfoController> applicationInfo = db.getAllApplicationInfo();

        for (ApplicationInfoController app : applicationInfo) {

            // Reading package specific permissions from permission table
//            Log.d("Reading: ", "Reading all contacts..");
            List<PermissionInfoController> packageSpecificPermissionInfo = db.getPackageSpecificPermissionInfo(app.getUID());

            for (PermissionInfoController apps : packageSpecificPermissionInfo) {

                if(apps.getPermissions().equals("android.permission.ACCESS_COARSE_LOCATION")
                        ||apps.getPermissions().equals("android.permission.ACCESS_FINE_LOCATION") ) {

                    appNames.add(app.getAppName());
                    break;
                }

            }

        }

        String[] appNamesArray = new String[appNames.size()];
        appNames.toArray(appNamesArray);
        db.close();
//        for (String app : appNamesArray) {
//
//
//            Log.d("hmm: ", app.toString());
//        }
        return appNamesArray;

    }
    //-------------------------------------------------------------------------------------------------------------------

    /**********************call to load application name in list*********************/

    public String[] loadApplicationName() {

        List<String> a = new ArrayList<String>();

        // Reading all row from package table
        List<ApplicationInfoController> applicationInfo = db.getAllApplicationInfo();

        for (ApplicationInfoController app : applicationInfo) {
            a.add(app.getAppName());
        }
        db.close();
        String[] myArray = new String[a.size()];
        a.toArray(myArray);
        return myArray;
    }

    //loads all the permissions based on a application name
    //---------------------------------------------------------------------------------------------
    public String[] loadApplicationBasedPermissionNames(String appName){

        ApplicationInfoController applicationInfo = db.getAppNameApplicationInfo(appName);


        List<PermissionInfoController> permissionInfo = db.getAllPermissionInfo();

        List<String> permissionNames = new ArrayList<String>();


        for (PermissionInfoController perInfo : permissionInfo) {
            if(perInfo.getUID()==(applicationInfo.getUID())){

                permissionNames.add(perInfo.getPermissions());
            }

        }
        String[] permissionNamesArray = new String[permissionNames.size()];
        permissionNames.toArray(permissionNamesArray);
        db.close();
//        for (String app : permissionNamesArray) {
//            Log.d("testcase: ", app.toString());
//        }
        return permissionNamesArray;
    }
    //---------------------------------------------------------------------------------------------------

    //load all unique permission names
    //-------------------------------------------------------------------------------------------------------------
    public String[] loadPermissionName(){

        // Reading all row from permission table
//        Log.d("Permissions: ", "Reading all contacts..");
        List<PermissionInfoController> permissionInfo = db.getAllPermissionInfo();
        List<String> permissions = new ArrayList<String>();
        for (PermissionInfoController app : permissionInfo) {

            permissions.add(app.getPermissions());
        }
        Set<String> uniquePermissions = new HashSet<String>(permissions);

        String[] permissionsArray = new String[uniquePermissions.size()];
        uniquePermissions.toArray(permissionsArray);

//        for (String app : permissionsArray) {
//            Log.d("xxx: ", app.toString());
//        }
        db.close();
        return permissionsArray;

    }
    //-----------------------------------------------------------------------------------------------------------------

    //load all the applications which use a particular permission
    //---------------------------------------------------------------------------------------------------------
    public String[] loadPermissionBasedAppNames(String permissionName){

        //we need all the data from permission table
        List<PermissionInfoController> permissionInfo = db.getAllPermissionInfo();

        List<String> appNames = new ArrayList<String>();

        //now we will create the list based on the permission given
        for (PermissionInfoController app : permissionInfo) {
            if(app.getPermissions().equals(permissionName)){

                ApplicationInfoController a = db.getApplicationInfo(app.getUID());

                appNames.add(a.getAppName());
            }

        }
        String[] appNamesArray = new String[appNames.size()];
        appNames.toArray(appNamesArray);
        db.close();
//        for (String app : appNamesArray) {
//            Log.d("boomboomboom: ", app.toString());
//        }
        return appNamesArray;
    }
    //----------------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------------------------
    //change the permissions check values in database
    public void changeLocationCheckValues(String [] appNames, Boolean [] check){
        int success=0;
        int position=-1;
        //send every app names and change the permissions check values in database
        for (String a : appNames) {
            position++;
            ApplicationInfoController appInfo = db.getAppNameApplicationInfo(a.toString());

            ApplicationInfoController applications = new ApplicationInfoController();
            applications.setId(appInfo.getId());
            applications.setUID(appInfo.getUID());
            applications.setAppName(appInfo.getAppName());
            applications.setPackageName(appInfo.getPackageName());
            if(check[position]) {
                applications.setPermission_checked("1");
            }
            else{
                applications.setPermission_checked("0");
            }

            success = db.updateApplicationInfo(applications);

//            Log.d("PermissionCheckChange", Integer.toString(success));
            //just to check
            testRetrievingSingleApplication(a.toString());
        }

        db.close();
    }
    //----------------------------------------------------------------------------------------------------

    //based on Appname
    public void testRetrievingSingleApplication(String Appname) {

        //fetching a row
//        Log.d("Inside view ", "before..");
        ApplicationInfoController a = db.getAppNameApplicationInfo(Appname); //10257

//        String log = "Id: " + a.getId() + " ,UID: " + a.getUID()
//                + " ,Name: " + a.getAppName() + " ,Package Name: " + a.getPackageName()
//                + " ,Permission Checked: " + a.getPermissionChecked();
//        // Writing Contacts to log
//        Log.d("PermissionCheckChange", log);
        //-----------------------------------------------------------

        db.close();
    }

    public void testRetrievingAllPermissionTable() {

        // Reading all row from permission table
//        Log.d("Reading: ", "Reading all contacts..");
        List<PermissionInfoController> permissionInfo = db.getAllPermissionInfo();

//        for (PermissionInfoController app : permissionInfo) {
//            String log = "Id: "+app.getId()+" ,UID: " + app.getUID()
//                    +" ,Permissions: " + app.getPermissions();
//            // Writing Contacts to log
//            Log.d("Name: ", log);
//        }
        db.close();

    }

    // 2. call this for a single package cell
    public List testRetrievingPackageSpecificPermissions(int UID) {

        // Reading package specific permissions from permission table
//        Log.d("Reading: ", "Reading all contacts..");
        List<PermissionInfoController> packageSpecificPermissionInfo = db.getPackageSpecificPermissionInfo(UID);

//        for (PermissionInfoController apps : packageSpecificPermissionInfo) {
//            String lo = " ,Permissions: " + apps.getPermissions();
//            // Writing Contacts to log
//            Log.d("Name: ", lo);
//        }
        db.close();

        return packageSpecificPermissionInfo;
    }
}
