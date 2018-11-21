package app.stape.songs.behavior;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Application extends android.app.Application {

    private static Application instance;

    public Application() {
        instance = this;
    }

    public static Context self() {
        return instance;
    }

    public static ProgressDialog getProgress(Context context, int message){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(self().getString(message));
        progressDialog.setCancelable(false);

        return progressDialog;
    }

    public static boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) self().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI")) {
                if (ni.isConnected()) {
                    haveConnectedWifi = true;
                }
            }
            if (ni.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (ni.isConnected()) {
                    haveConnectedMobile = true;
                }
            }
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
