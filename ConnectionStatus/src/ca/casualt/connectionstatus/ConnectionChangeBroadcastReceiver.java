package ca.casualt.connectionstatus;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import ca.casualt.connectionstatus.connutil.ConnInfo;

/**
 * For handling connection change events.
 * 
 * @author Tony
 */
public final class ConnectionChangeBroadcastReceiver extends BroadcastReceiver {

    /**
     * For Logging.
     */
    private static final String TAG = "ConnChangeReceiver";

    /**
     * The id to use for connection change broadcasts.
     */
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        {
            Log.v(TAG, "In onReceive: " + intent);
            Log.v(TAG, "Extras: " + bundle);
            for (String key : bundle.keySet()) {
                Log.v(TAG, "[" + key + "]: " + bundle.get(key));
            }
        }
        // Pull out network information to check if connected.
        if (bundle != null && bundle.containsKey("networkInfo")
                && (bundle.get("networkInfo") instanceof NetworkInfo)) {
            NetworkInfo netInfo = (NetworkInfo) bundle.get("networkInfo");
            if (!netInfo.isConnected()) {
                return;
            }
        }
        // TODO: allow enable/disable via shared preferences.
        // doToast(context);
        doNotification(context);
    }

    public static void doNotification(Context context) {
        Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(smartSelectIcon(context)).setContentTitle("Current Connection")
                .setContentText(getCleanNetName(context)).setOngoing(true);
        Notification notification = builder.build();

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

    private static int smartSelectIcon(Context context) {
        NetworkInfo netInfo = ConnInfo.getActiveNetworkInfo(context);
        if (netInfo == null || netInfo.getType() != ConnectivityManager.TYPE_WIFI) {
            return R.drawable.ic_launcher_grey;
        } else {
            return R.drawable.ic_launcher;
        }
    }

    private void doToast(Context context) {
        Toast toast = new Toast(context);
        toast.setView(new LinearLayout(context));
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            View layout = inflater.inflate(R.layout.toast_connection, (ViewGroup) toast.getView());

            TextView netState = (TextView) layout.findViewById(R.id.textViewToastNetState);
            netState.setText(ConnInfo.getActiveNetworkState(context));

            TextView netName = (TextView) layout.findViewById(R.id.textViewToastNetName);
            netName.setText(getCleanNetName(context));
        }
        toast.show();
    }

    private static String getCleanNetName(final Context context) {
        return cleanNetName(ConnInfo.getActiveNetworkName(context));
    }

    private static String cleanNetName(final String toClean) {
        return toClean.replaceAll("\"", "");
    }
}
