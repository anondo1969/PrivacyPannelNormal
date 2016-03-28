package app.android.tanzi.com.privacypannel3;

/**
 * Created by Entu on 3/20/2016.
 */
public class ApplicationInfoController {
    //column headers
    int _id;
    int _UID;
    String _appName;
    String _packageName;
    String _permissionChecked;

    //  constructors
    //-----------------------------------------------------------------------------------------------------
    public ApplicationInfoController(){

    }

    public ApplicationInfoController(int UID){
        this._UID = UID;
    }

    public ApplicationInfoController(String packageName){
        this._packageName = packageName;
    }

    public ApplicationInfoController(String appName, String packageName){

        this. _appName = appName;
        this._packageName = packageName;
    }

    public ApplicationInfoController(int UID, String appName, String packageName, String permissionChecked){

        this._UID = UID;
        this. _appName = appName;
        this. _packageName = packageName;
        this. _permissionChecked = permissionChecked;
    }

    public ApplicationInfoController(int id, int UID, String appName, String packageName, String permissionChecked){

        this._id = id;
        this._UID = UID;
        this. _appName = appName;
        this. _packageName = packageName;
        this. _permissionChecked = permissionChecked;
    }

    //------------------------------------------------------------------------------------------------------------------

    //getting Id
    public int getId(){
        return this._id;
    }

    //setting ID
    public void setId(int id) {
        this._id = id;
    }

    //getting UID
    public int getUID(){
        return this._UID;
    }

    //setting UID
    public void setUID(int UID) {
        this._UID = UID;
    }

    //getting packageName
    public String getPackageName(){
        return this._packageName;
    }

    //setting packageName
    public void setPackageName(String packageName) {
        this._packageName = packageName;
    }

    //getting appName
    public String getAppName(){
        return this._appName;
    }

    //setting appName
    public void setAppName(String appName) {
        this._appName = appName;
    }

    //getting permission_checked
    public String getPermissionChecked(){
        return this._permissionChecked;
    }

    //setting permission_checked
    public void setPermission_checked(String permission_checked) {
        this._permissionChecked = permission_checked;
    }
}
