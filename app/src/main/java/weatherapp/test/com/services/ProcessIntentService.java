package weatherapp.test.com.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import weatherapp.test.com.utils.WeatherConstants;

/**
 * Created by raman on 7/23/2015.
 * Created to process the heavy operation and then pass the result using receiver
 */
public class ProcessIntentService extends IntentService {

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;
    private static final String TAG = ProcessIntentService.class.getSimpleName();

    public ProcessIntentService() {
        super(ProcessIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent arg0) {
        Log.d(ProcessIntentService.class.getName(), "service started...");
        final ResultReceiver receiver = arg0.getParcelableExtra(WeatherConstants.RECIEVER_TAG);
        String command = arg0.getStringExtra(WeatherConstants.COMMAND_TAG);
        String cityName = null;
        Bundle b = new Bundle();
        try {
            cityName = URLEncoder.encode(arg0.getStringExtra(WeatherConstants.CITY_NAME), "UTF-8");
            if (command.equals(WeatherConstants.REQUEST_TAG)) {
//                receiver.send(STATUS_RUNNING, Bundle.EMPTY);
                try {
                    b.putString(WeatherConstants.RESPONSE_TAG, GET(WeatherConstants.WEATHER_API + cityName));
                    receiver.send(STATUS_FINISHED, b);
                } catch (Exception e) {
                    b.putString(Intent.EXTRA_TEXT, e.toString());
                    receiver.send(STATUS_ERROR, b);
                }
            }
        } catch (UnsupportedEncodingException e) {
             receiver.send(STATUS_ERROR, b);
            e.printStackTrace();
        }finally {
            Log.d(ProcessIntentService.class.getName(), "service stopping...");
            this.stopSelf();
        }
    }

    /**
     * Response from the server for a given url
     *
     * @param url
     * @return
     */
    public static String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpParams httpParameters = new BasicHttpParams();
            // Set the timeout in milliseconds until a connection is established.
            // The default value is zero, that means the timeout is not used.
            int timeoutConnection = 60000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            // Set the default socket timeout (SO_TIMEOUT)
            // in milliseconds which is the timeout for waiting for data.
            int timeoutSocket = 30000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
            } else
                result = "";

        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
        return result;
    }

    /**
     * convert the input stream to result
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
