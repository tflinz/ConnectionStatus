package ca.casualt.connectionstatus.connutil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import ca.casualt.connectionstatus.R;

/**
 * Utilities for fetching the information about connections.
 * 
 * @author Tony
 */
public final class ConnInfo {

    /**
     * Utility Class.
     */
    private ConnInfo() {
    }

    /**
     * Using the provided not-null context, this will return the current network
     * info.
     * 
     * @param context
     *            used to access system service.
     * @return the active network info.
     */
    public static NetworkInfo getActiveNetworkInfo(final Context context) {
        ConnectivityManager conService = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = conService.getActiveNetworkInfo();
        return activeNetInfo;
    }

    /**
     * Using the provided not-null context, this will return the current network
     * state, if available.
     * 
     * @param context
     *            used to access system service and string constants.
     * @return the active network state (or "unknown" if unavailable).
     */
    public static String getActiveNetworkState(final Context context) {
        String toReturn = context.getString(R.string.unknown);
        NetworkInfo activeNetInfo = getActiveNetworkInfo(context);
        if (activeNetInfo != null) {
            State activeState = activeNetInfo.getState();
            toReturn = activeState.name();
        }
        return toReturn;
    }

    /**
     * Using the provided not-null context, this will return the current network
     * type, if available.
     * 
     * @param context
     *            used to access system service and string constants.
     * @return the active network type (or "unknown" if unavailable).
     */
    public static String getActiveNetworkType(final Context context) {
        String toReturn = context.getString(R.string.unknown);
        NetworkInfo activeNetInfo = getActiveNetworkInfo(context);
        if (activeNetInfo != null) {
            int netType = activeNetInfo.getType();
            switch (netType) {
                case ConnectivityManager.TYPE_WIFI:
                    toReturn = context.getString(R.string.NetworkType_Value_WiFi);
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    toReturn = context.getString(R.string.NetworkType_Value_Mobile);
                    break;
                default:
                    // Unknown/unhandled case
                    break;
            }
        }
        return toReturn;
    }

    /**
     * Using the provided not-null context, this will return the current network
     * name, if available.
     * 
     * @param context
     *            used to access system service and string constants.
     * @return the active network name (or "unknown" if unavailable).
     */
    public static String getActiveNetworkName(final Context context) {
        String toReturn = context.getString(R.string.unknown);
        NetworkInfo activeNetInfo = getActiveNetworkInfo(context);
        if (activeNetInfo != null) {
            int netType = activeNetInfo.getType();
            switch (netType) {
                case ConnectivityManager.TYPE_WIFI:
                    final WifiManager wifiManager = (WifiManager) context
                            .getSystemService(Context.WIFI_SERVICE);
                    final WifiInfo wConnInfo = wifiManager.getConnectionInfo();
                    final String ssid = wConnInfo.getSSID();
                    if (ssid != null && !ssid.isEmpty()) {
                        toReturn = ssid;
                    }
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                default:
                    // Unknown/unhandled case
                    break;
            }
        }
        return toReturn;
    }

}
