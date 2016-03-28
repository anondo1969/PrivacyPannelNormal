package app.android.tanzi.com.privacypannel3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Entu on 3/24/2016.
 */
public class ApplicationRestartSignalReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(AppContext.getContext(), "App will be started now", Toast.LENGTH_LONG).show();

        Intent service = new Intent(AppContext.getContext(), ApplicationRestartService.class);
        AppContext.getContext().startService(service);

    }
}
