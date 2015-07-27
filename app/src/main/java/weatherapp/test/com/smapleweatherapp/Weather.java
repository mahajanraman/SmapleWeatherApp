package weatherapp.test.com.smapleweatherapp;

/**
 * Created by Mahajan on 23-Jul-15.
 * Pojo class for storing weather related whole information.
 */
public class Weather {
    private double latitude;
    private double longitude;
    private String cityName;
    private double windSpeed;
    private int cloudsRange;
    private WSDescription weatherDescription;
    private WSMainDescription mWSMainDescription;
    private WeatherSystemData mWeatherSystemData;

    public int getCloudsRange() {
        return cloudsRange;
    }

    public void setCloudsRange(int cloudsRange) {
        this.cloudsRange = cloudsRange;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setWeatherDescription(WSDescription weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public void setMainWeatherDescription(WSMainDescription weathermMainDescription) {
        this.mWSMainDescription = weathermMainDescription;
    }

    public void setWeatherSystemDescription(WeatherSystemData weatherSystemData) {
        this.mWeatherSystemData = weatherSystemData;
    }

    public WSDescription getWeatherDescription() {
        return weatherDescription;
    }

    public WSMainDescription getmWSMainDescription() {
        return mWSMainDescription;
    }

    public WeatherSystemData getmWeatherSystemData() {
        return mWeatherSystemData;
    }
}
