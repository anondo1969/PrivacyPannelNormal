package app.android.tanzi.com.privacypannel3;

import android.app.Application;
import android.content.Context;

/**
 * Created by Entu on 3/20/2016.
 */
public class AppContext extends Application {

    private static Application sApplication;

    public static Application getApplication() {
        return sApplication;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}
