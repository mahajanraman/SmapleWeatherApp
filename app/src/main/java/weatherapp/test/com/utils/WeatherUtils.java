package weatherapp.test.com.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Mahajan on 22-Jul-15.
 * utility class for the utility functions.
 */
public class WeatherUtils {

    /**
     * hide the soft keyword
     * @param act
     */
    public static void hideKeyboard(Activity act) {
        // Check if no view has focus:
        View view = act.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * check the internet connection.
     * @param con
     * @return
     */
    public static boolean isInternetConnect(Context con) {
        ConnectivityManager conManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conManager != null) {
            NetworkInfo[] networkInfo = conManager.getAllNetworkInfo();
            if (networkInfo != null)
                for (int i = 0; i < networkInfo.length; i++)
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }
}
