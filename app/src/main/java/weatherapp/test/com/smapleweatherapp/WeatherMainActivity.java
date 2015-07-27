package weatherapp.test.com.smapleweatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import weatherapp.test.com.receivers.ProcessResultReceiver;
import weatherapp.test.com.services.ProcessIntentService;
import weatherapp.test.com.utils.ParseException;
import weatherapp.test.com.utils.ParseFactory;
import weatherapp.test.com.utils.WeatherConstants;
import weatherapp.test.com.utils.WeatherUtils;

public class WeatherMainActivity extends BaseActivity implements TextView.OnEditorActionListener, ProcessResultReceiver.Receiver, View.OnClickListener {

    private EditText cityName;
    private ProcessResultReceiver mProcessResultReceiver;
    private static final String TAG = WeatherMainActivity.class.getSimpleName();
    private static final int VAL1 = 0;
    private static final int VAL2 = 3;
    private static final double TEMP_DEDUCT = 273.0;
    private TextView showResponse;
    private ImageView weatherIcon;
    private ImageView flagIcon;
    private TextView currentTemp;
    private TextView conditionInfo;
    private Button searchButton;
    private TextView tempRange;
    private ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_main);
//        getActionBar().setTitle(getResources().getString(R.string.app_name));
        showResponse = (TextView) findViewById(R.id.response_data);
        weatherIcon = (ImageView) findViewById(R.id.weather_icon);
        flagIcon = (ImageView) findViewById(R.id.flag_icon);
        currentTemp = (TextView) findViewById(R.id.temp_data);
        conditionInfo = (TextView) findViewById(R.id.condition);
        searchButton = (Button) findViewById(R.id.search_weather_condition);
        searchButton.setOnClickListener(this);
        tempRange = (TextView) findViewById(R.id.temp_range);
        loadingBar = (ProgressBar)findViewById(R.id.laoding);
        cityName = (EditText) findViewById(R.id.city_compose);
        cityName.setOnEditorActionListener(WeatherMainActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mProcessResultReceiver == null) {
            mProcessResultReceiver = new ProcessResultReceiver(new Handler());
            mProcessResultReceiver.setReceiver(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProcessResultReceiver = null;
        showResponse = null;
        weatherIcon = null;
        flagIcon = null;
        cityName = null;
        currentTemp = null;
        conditionInfo = null;
        searchButton = null;
        tempRange = null;
        loadingBar = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mProcessResultReceiver != null) {
            // Clear the receiver to avoid leaks
            mProcessResultReceiver.setReceiver(null);
        }
        mProcessResultReceiver = null;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) throws ParseException {
        switch (resultCode) {
            case ProcessIntentService.STATUS_RUNNING:
                Log.d(TAG, "Running");
                break;
            case ProcessIntentService.STATUS_FINISHED:
                String data = resultData.getString(WeatherConstants.RESPONSE_TAG);
                Weather mWeatherInfo = ParseFactory.parseWeatherData(data);
                StringBuilder sBuilder = new StringBuilder();
                sBuilder.append(mWeatherInfo.getCityName())
                        .append(getResources().getString(R.string.comma))
                        .append(mWeatherInfo.getmWeatherSystemData().getWeatherCountry());

                Picasso.with(WeatherMainActivity.this)
                        .load(WeatherConstants.IMAGE_APPEND_URL + mWeatherInfo.getWeatherDescription().getIconId() + WeatherConstants.ICON_EXTENSION)
                        .into(weatherIcon);

                Picasso.with(WeatherMainActivity.this)
                        .load(WeatherConstants.FLAG_IMAGE_APPEND_URL + mWeatherInfo.getmWeatherSystemData().getWeatherCountry().toLowerCase() + WeatherConstants.ICON_EXTENSION)
                        .into(flagIcon);

                conditionInfo.setText(mWeatherInfo.getWeatherDescription().getFullDescription());

                currentTemp.setText((float) (mWeatherInfo.getmWSMainDescription().getWeatherTemp() - TEMP_DEDUCT) + this.getResources().getString(R.string.degree));
                currentTemp.setVisibility(View.VISIBLE);
                tempRange.setText(getResources().getString(R.string.min_temp) + (float) (mWeatherInfo.getmWSMainDescription().getWeatherMinTemp() - TEMP_DEDUCT) +
                        this.getResources().getString(R.string.degree) + getResources().getString(R.string.single_next_line) + getResources().getString(R.string.max_temp) +
                        (float) (mWeatherInfo.getmWSMainDescription().getWeatherMaxTemp() - TEMP_DEDUCT) +
                        this.getResources().getString(R.string.degree) + getResources().getString(R.string.double_next_line) + getResources().getString(R.string.wind_speed)
                        + mWeatherInfo.getWindSpeed() + getResources().getString(R.string.wind_unit) + getResources().getString(R.string.single_next_line) + getResources().getString(R.string.clouds) + mWeatherInfo.getCloudsRange() + "%\n" + getResources().getString(R.string.pressure)
                        + mWeatherInfo.getmWSMainDescription().getWeatherPressure() + getResources().getString(R.string.pressure_unit) + getResources().getString(R.string.double_next_line) + getResources().getString(R.string.geo_cords_start) + mWeatherInfo.getLatitude() + getResources().getString(R.string.comma) + mWeatherInfo.getLongitude() + getResources().getString(R.string.end_geo_cords));
                showResponse.setText(sBuilder.toString().trim());
                Log.d(TAG, "Response recieved :\n" + data);
                loadingBar.setVisibility(View.GONE);

                break;
            case ProcessIntentService.STATUS_ERROR:
                loadingBar.setVisibility(View.GONE);
                Log.d(TAG, "unexpcetd error occured");
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            validteAndSearch();
            return true;
        }
        return false;
    }

    /**
     * validate and do search
     */
    public void validteAndSearch(){
        if (cityName.getText().toString().trim().length() <= VAL1) {
            Toast.makeText(WeatherMainActivity.this, getResources().getString(R.string.city_name_error), Toast.LENGTH_SHORT).show();
        } else if (cityName.getText().toString().trim().length() < VAL2) {
            Toast.makeText(WeatherMainActivity.this, getResources().getString(R.string.city_name_length), Toast.LENGTH_SHORT).show();
        } else if (!WeatherUtils.isInternetConnect(WeatherMainActivity.this)) {
            Toast.makeText(WeatherMainActivity.this, getResources().getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
        } else {
            searchWeatherConditions();
        }
    }

    /**
     * search the weather conditions
     */
    private void searchWeatherConditions() {
        WeatherUtils.hideKeyboard(WeatherMainActivity.this);
        loadingBar.setVisibility(View.VISIBLE);
        try {
            final Intent intent = new Intent(Intent.ACTION_SYNC, null, this, ProcessIntentService.class);
            intent.putExtra(WeatherConstants.RECIEVER_TAG, mProcessResultReceiver);
            intent.putExtra(WeatherConstants.COMMAND_TAG, WeatherConstants.REQUEST_TAG);
            intent.putExtra(WeatherConstants.CITY_NAME, cityName.getText().toString().trim());
            startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_weather_condition:
                validteAndSearch();
                break;

            default:
                break;
        }
    }
}