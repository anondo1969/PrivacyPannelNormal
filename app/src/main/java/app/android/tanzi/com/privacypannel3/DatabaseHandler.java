package app.android.tanzi.com.privacypannel3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Entu on 3/20/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {


    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "appsInfo";

    // table name
    private static final String TABLE_PACKAGES = "packageData";
    private static final String TABLE_PERMISSIONS = "permissionData";

    // PackageData Table Columns names

    private static final String KEY_ID = "id";
    private static final String KEY_UID = "UID";
    private static final String KEY_APP_NAME = "appName";
    private static final String KEY_PACKAGE_NAME = "packageName";
    private static final String KEY_PERMISSION_CHECKED = "permissionChecked";

    // permissionData Table Columns names

    private static final String KEY_PERMISSION_ID = "id";
    private static final String KEY_PERMISSION_UID = "UID";
    private static final String KEY_PERMISSIONS = "permissions";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {



        String CREATE_PACKAGES_TABLE = "CREATE TABLE " + TABLE_PACKAGES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_UID + " INTEGER," + KEY_APP_NAME + " TEXT," + KEY_PACKAGE_NAME + " TEXT,"
                + KEY_PERMISSION_CHECKED + " TEXT" + ")";


        db.execSQL(CREATE_PACKAGES_TABLE);

        String CREATE_PERMISSIONS_TABLE = "CREATE TABLE " + TABLE_PERMISSIONS + "("
                + KEY_PERMISSION_ID + " INTEGER PRIMARY KEY," + KEY_PERMISSION_UID + " INTEGER,"
                + KEY_PERMISSIONS + " TEXT" + ")";


        db.execSQL(CREATE_PERMISSIONS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PACKAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERMISSIONS);
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new rows in package table
    void addApplicationInfo(ApplicationInfoController applicationInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_UID, applicationInfo.getUID()); // Application UID
        values.put(KEY_APP_NAME, applicationInfo.getAppName()); // Application name
        values.put(KEY_PACKAGE_NAME, applicationInfo.getPackageName()); // Package Name
        values.put(KEY_PERMISSION_CHECKED, applicationInfo.getPermissionChecked()); // permission checking

        // Inserting Row
        db.insert(TABLE_PACKAGES, null, values);
        db.close(); // Closing database connection
    }

    // Adding new rows in permissions table
    void addPermissionInfo(PermissionInfoController permissionInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_PERMISSION_UID, permissionInfo.getUID());
        values.put(KEY_PERMISSIONS, permissionInfo.getPermissions()); // Get permissions

        // Inserting Row
        db.insert(TABLE_PERMISSIONS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single row in package table
    public ApplicationInfoController getApplicationInfo(int UID) {

//        Log.d("UID Value: ", "ashche");
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PACKAGES, new String[]{KEY_ID, KEY_UID,
                        KEY_APP_NAME, KEY_PACKAGE_NAME, KEY_PERMISSION_CHECKED}, KEY_UID + "=?",
                new String[]{String.valueOf(UID)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        //checking the null values
        if (cursor.getCount()==0){

            ApplicationInfoController applicationInfo = new ApplicationInfoController();
            return applicationInfo;
        }

//        String log = "Id: "+Integer.parseInt(cursor.getString(0))+" ,UID: " + Integer.parseInt(cursor.getString(1))
//                +" ,Name: " + cursor.getString(2) + " ,Package Name: " + cursor.getString(3)
//                + " ,Permission Checked: " + cursor.getString(4);
//        // Writing Contacts to log
//        Log.d("Cursor Values ", log);


        ApplicationInfoController applicationInfo = new ApplicationInfoController(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
                cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return contact
        return applicationInfo;

    }

    // Getting single row in package table based on App name
    //---------------------------------------------------------------------------------
    public ApplicationInfoController getAppNameApplicationInfo(String appName) {

//        Log.d("UID Value: ","ashche");
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PACKAGES, new String[]{KEY_ID, KEY_UID,
                        KEY_APP_NAME, KEY_PACKAGE_NAME, KEY_PERMISSION_CHECKED}, KEY_APP_NAME + "=?",
                new String[]{String.valueOf(appName)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        //checking the null values
        if (cursor.getCount()==0){

            ApplicationInfoController applicationInfo = new ApplicationInfoController();
            return applicationInfo;
        }

//        String log = "Id: "+Integer.parseInt(cursor.getString(0))+" ,UID: " + Integer.parseInt(cursor.getString(1))
//                +" ,Name: " + cursor.getString(2) + " ,Package Name: " + cursor.getString(3)
//                + " ,Permission Checked: " + cursor.getString(4);
//        // Writing Contacts to log
//        Log.d("Cursor Values ", log);


        ApplicationInfoController applicationInfo = new ApplicationInfoController(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
                cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return contact
        return applicationInfo;

    }
    //---------------------------------------------------------------------------------

    // Getting single row in package table based on package name
    public ApplicationInfoController getApplicationInfo(String packageName) {

//        Log.d("UID Value: ","ashche");
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PACKAGES, new String[]{KEY_ID, KEY_UID,
                        KEY_APP_NAME, KEY_PACKAGE_NAME, KEY_PERMISSION_CHECKED}, KEY_PACKAGE_NAME + "=?",
                new String[]{String.valueOf(packageName)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        //checking the null values
        if (cursor.getCount()==0){

            ApplicationInfoController applicationInfo = new ApplicationInfoController();
            return applicationInfo;
        }

//        String log = "Id: "+Integer.parseInt(cursor.getString(0))+" ,UID: " + Integer.parseInt(cursor.getString(1))
//                +" ,Name: " + cursor.getString(2) + " ,Package Name: " + cursor.getString(3)
//                + " ,Permission Checked: " + cursor.getString(4);
//        // Writing Contacts to log
//        Log.d("Cursor Values ", log);


        ApplicationInfoController applicationInfo = new ApplicationInfoController(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
                cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return contact
        return applicationInfo;

    }

    // Getting single row in permission table based on UID
    public PermissionInfoController getPermissionInfo(int UID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PERMISSIONS, new String[]{KEY_PERMISSION_ID, KEY_PERMISSION_UID,
                        KEY_PERMISSIONS}, KEY_PERMISSION_UID + "=?",
                new String[]{String.valueOf(UID)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        //checking the null values
        if (cursor.getCount()==0){

            PermissionInfoController permissionInfo = new PermissionInfoController();
            return permissionInfo;
        }

        PermissionInfoController permissionInfo = new PermissionInfoController(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
                cursor.getString(2));
        // return contact
        return permissionInfo;
    }

    // Getting All rows from package table
    public List<ApplicationInfoController> getAllApplicationInfo() {
        List<ApplicationInfoController> applicationInfoList = new ArrayList<ApplicationInfoController>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PACKAGES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ApplicationInfoController applicationInfo = new ApplicationInfoController();
                applicationInfo.setId(Integer.parseInt(cursor.getString(0)));
                applicationInfo.setUID(Integer.parseInt(cursor.getString(1)));
                applicationInfo.setAppName(cursor.getString(2));
                applicationInfo.setPackageName(cursor.getString(3));
                applicationInfo.setPermission_checked((cursor.getString(4)));
                // Adding contact to list
                applicationInfoList.add(applicationInfo);
            } while (cursor.moveToNext());
        }

        // return contact list
        return applicationInfoList;
    }

    // Getting All rows from permission table
    public List<PermissionInfoController> getAllPermissionInfo() {
        List<PermissionInfoController> permissionInfoList = new ArrayList<PermissionInfoController>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PERMISSIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PermissionInfoController permissionInfo = new PermissionInfoController();
                permissionInfo.setId(Integer.parseInt(cursor.getString(0)));
                permissionInfo.setUID(Integer.parseInt(cursor.getString(1)));
                permissionInfo.setPermissions(cursor.getString(2));

                // Adding contact to list
                permissionInfoList.add(permissionInfo);
            } while (cursor.moveToNext());
        }

        // return list
        return permissionInfoList;
    }

    // getting package specific permissions from permission table
    public List<PermissionInfoController> getPackageSpecificPermissionInfo(int UID) {

        List<PermissionInfoController> permissionInfoList = new ArrayList<PermissionInfoController>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_PERMISSIONS, new String[]{
                        KEY_PERMISSIONS}, KEY_PERMISSION_UID + "=?",
                new String[]{String.valueOf(UID)}, null, null, null, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PermissionInfoController permissionInfo = new PermissionInfoController();

                permissionInfo.setPermissions(cursor.getString(0));

                // Adding contact to list
                permissionInfoList.add(permissionInfo);
            } while (cursor.moveToNext());
        }

        // return list
        return permissionInfoList;

    }

    // Updating single contact from package table
    public int updateApplicationInfo(ApplicationInfoController applicationInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UID, applicationInfo.getUID());
        values.put(KEY_APP_NAME, applicationInfo.getAppName());
        values.put(KEY_PACKAGE_NAME, applicationInfo.getPackageName());
        values.put(KEY_PERMISSION_CHECKED, applicationInfo.getPermissionChecked());

        // updating row
        return db.update(TABLE_PACKAGES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(applicationInfo.getId()) });
    }

    // Updating single contact from permission table
    public int updatePermissionInfo(PermissionInfoController permissionInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PERMISSION_UID, permissionInfo.getUID());
        values.put(KEY_PERMISSIONS, permissionInfo.getPermissions());

        // updating row
        return db.update(TABLE_PERMISSIONS, values, KEY_PERMISSION_ID + " = ?",
                new String[] { String.valueOf(permissionInfo.getId()) });
    }

    // Deleting single contact from package table based on UID or Package Name
    public void deleteApplicationInfo(ApplicationInfoController applicationInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_PACKAGES, KEY_UID + " = ?", new String[]{String.valueOf(applicationInfo.getPackageName())});

        db.close();
    }

    // Deleting single contact from permission table based on UID or Package Name
    public void deletePermissionInfo(PermissionInfoController permissionInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_PERMISSIONS, KEY_PERMISSION_UID + " = ?", new String[]{String.valueOf(permissionInfo.getUID())});

        db.close();
    }


    // Getting contacts Count
    /*
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PACKAGES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    */
}
