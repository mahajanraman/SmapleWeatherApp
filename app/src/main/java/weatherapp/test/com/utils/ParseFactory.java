package weatherapp.test.com.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import weatherapp.test.com.smapleweatherapp.WSDescription;
import weatherapp.test.com.smapleweatherapp.WSMainDescription;
import weatherapp.test.com.smapleweatherapp.Weather;
import weatherapp.test.com.smapleweatherapp.WeatherSystemData;

/**
 * Created by Mahajan on 23-Jul-15.
 * A factory class for parsing the json content
 */
public class ParseFactory {

    private static final String LATITUDE  = "lat";
    private static final String LONGITUDE  = "lon";
    private static final String COORDINATES  = "coord";
    private static final String WEATHER = "weather";
    private static final String CURRENT_TEMP = "temp";
    private static final String PRESSURE = "pressure";
    private static final String HUMIDITY = "humidity";
    private static final String MIN_TEMP = "temp_min";
    private static final String MAX_TEMP = "temp_max";
    private static final String MAIN = "main";
    private static final String CITY_NAME = "name";
    private static final String SYS = "sys";
    private static final String COUNTRY = "country";
    private static final String MESSAGE = "message";
    private static final String SUNRISE = "sunrise";
    private static final String SUNSET = "sunset";
    private static final String WIND = "wind";
    private static final String CLOUDS = "clouds";
    private static final String ALL = "all";
    private static final String SPEED = "speed";

    /**
     * parse weather data
     * @param data
     * @return
     */
    public static Weather parseWeatherData(String data) throws ParseException{
        Weather mWeather = new Weather();
        try{
            JSONObject jObj = new JSONObject(data);
            mWeather.setCityName(jObj.getString(CITY_NAME));
            JSONObject mObj = new JSONObject(jObj.getString(COORDINATES));
            mWeather.setLatitude(mObj.getDouble(LATITUDE));
            mWeather.setLongitude(mObj.getDouble(LONGITUDE));
            mWeather.setWeatherDescription(addWeatherDescription(jObj.getJSONArray(WEATHER)));
            mWeather.setWindSpeed(windInfo(jObj.getString(WIND)));
            mWeather.setCloudsRange(cloudInfo(jObj.getString(CLOUDS)));
            mWeather.setMainWeatherDescription(addWeatherMainDescription(jObj.getString(MAIN)));
            mWeather.setWeatherSystemDescription(addWeatherSystemDescription(jObj.getString(SYS)));
            return mWeather;
        }catch (Exception e){
            throw ParseException.makeException(e);
        }
    }

    /**
     * parse the whole description for the weather
     * @param mJsonArray
     * @return
     */
    public static WSDescription addWeatherDescription (JSONArray mJsonArray) throws ParseException{
        WSDescription mWholeDescription  = new WSDescription();;
        try {
            for (int i = 0; i < mJsonArray.length(); i++) {

                JSONObject eachVal = mJsonArray.getJSONObject(i);
                mWholeDescription.setMainDescription(eachVal.getString("main"));
                mWholeDescription.setFullDescription(eachVal.getString("description"));
                mWholeDescription.setIconId(eachVal.getString("icon"));
            }
            return mWholeDescription;
        }catch (Exception e){
            throw ParseException.makeException(e);
        }
    }

    /**
     * parse the wind description for the weather
     * @param mJsonArray
     * @return
     */
    public static double windInfo (String mJsonArray) throws ParseException{
        try {
            JSONObject mainVal = new JSONObject(mJsonArray);
          return  mainVal.getDouble(SPEED);
        }catch (Exception e){
            throw ParseException.makeException(e);
        }
    }

    /**
     * parse the clouds description for the weather
     * @param mJsonArray
     * @return
     */
    public static int cloudInfo (String mJsonArray) throws ParseException{
        try {
            JSONObject mainVal = new JSONObject(mJsonArray);
            return  mainVal.getInt(ALL);
        }catch (Exception e){
            throw ParseException.makeException(e);
        }
    }

    /**
     * parse the main description for the weather
     * @param mJsonArray
     * @return
     */
    public static WSMainDescription addWeatherMainDescription (String mJsonArray) throws ParseException{
        WSMainDescription mWSMainDescription  = new WSMainDescription();;
        try {
                JSONObject mainVal = new JSONObject(mJsonArray);
                mWSMainDescription.setWeatherTemp(mainVal.getDouble(CURRENT_TEMP));
                mWSMainDescription.setWeatherPressure(mainVal.getInt(PRESSURE));
                mWSMainDescription.setWeatherHumidity(mainVal.getInt(HUMIDITY));
                mWSMainDescription.setWeatherMinTemp(mainVal.getDouble(MIN_TEMP));
                mWSMainDescription.setWeatherMaxTemp(mainVal.getDouble(MAX_TEMP));

            return mWSMainDescription;
        }catch (Exception e){
            throw ParseException.makeException(e);
        }
    }

    /**
     * parse the system main data
     * @param mJsonArray
     * @return
     */
    public static WeatherSystemData  addWeatherSystemDescription (String mJsonArray) throws  ParseException{
        WeatherSystemData mWeatherSystemData  = new WeatherSystemData();;
        try {
            JSONObject mainSysdata = new JSONObject(mJsonArray);
            mWeatherSystemData.setWeatherCountry(mainSysdata.getString(COUNTRY));
            mWeatherSystemData.setWeatherMessage(mainSysdata.getString(MESSAGE));
            mWeatherSystemData.setWeatherSunrise(mainSysdata.getString(SUNRISE));
            mWeatherSystemData.setWeatherSunSet(mainSysdata.getString(SUNSET));

            return mWeatherSystemData;
        }catch (Exception e){
            throw ParseException.makeException(e);
        }
    }
}
