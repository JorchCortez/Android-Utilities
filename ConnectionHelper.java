package com.example.jorch.svmovil.ModulosYHelpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionHelper {

    public static long lastNoConnectionTs = -1;
    public static boolean isOnline = true;

    public static boolean isConnected(Context context) {
        ConnectivityManager cm =(ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();


        return isNetworkAvailable(activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnected());
    }

    public static boolean isConnectedOrConnecting(Context context) {
        ConnectivityManager cm =(ConnectivityManager)         context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public static boolean isNetworkAvailable (Boolean connected)
    {
        if (connected)
        {
            try {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8)
                {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    //your codes here

                    HttpURLConnection urlc = (HttpURLConnection)
                            (new URL("http://clients3.google.com/generate_204")
                                    .openConnection());
                    urlc.setRequestProperty("User-Agent", "Android");
                    urlc.setRequestProperty("Connection", "close");
                    urlc.setConnectTimeout(1500);
                    urlc.connect();
                    return (urlc.getResponseCode() == 204 &&
                            urlc.getContentLength() == 0);
                }
            }
            catch (Exception e)
            {
                Log.e("Connect", "Error checking internet connection", e);
            }
        }
        else
        {
            Log.d("Connect", "No network available!");
        }
        return false;
    }


}
