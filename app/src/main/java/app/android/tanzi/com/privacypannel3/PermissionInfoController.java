package app.android.tanzi.com.privacypannel3;

/**
 * Created by Entu on 3/20/2016.
 */
public class PermissionInfoController {
    //column headers
    int _id;
    int _UID;
    String _permissions;

    // Empty constructor
    public PermissionInfoController(){

    }

    //constructor
    public PermissionInfoController(int UID){
        this._UID = UID;
    }

    //constructor
    public PermissionInfoController(int UID, String permissions){

        this._UID = UID;
        this. _permissions = permissions;
    }

    //constructor
    public PermissionInfoController(int id, int UID, String permissions){

        this._id = id;
        this._UID = UID;
        this. _permissions = permissions;

    }

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

    //getting permissions
    public String getPermissions(){
        return this._permissions;
    }

    //setting permissions
    public void setPermissions(String permissions) {
        this._permissions = permissions;
    }
}
