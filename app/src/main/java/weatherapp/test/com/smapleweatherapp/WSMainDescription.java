package weatherapp.test.com.smapleweatherapp;

/**
 * Created by raman on 7/23/2015.
 * storing the values related to main weatherDescription
 */
public class WSMainDescription {

    private Double weatherTemp;
    private int weatherPressure;
    private int weatherHumidity;
    private Double weatherMinTemp;
    private Double weatherMaxTemp;

    public Double getWeatherTemp() {
        return weatherTemp;
    }

    public void setWeatherTemp(Double weatherTemp) {
        this.weatherTemp = weatherTemp;
    }

    public int getWeatherPressure() {
        return weatherPressure;
    }

    public void setWeatherPressure(int weatherPressure) {
        this.weatherPressure = weatherPressure;
    }

    public int getWeatherHumidity() {
        return weatherHumidity;
    }

    public void setWeatherHumidity(int weatherHumidity) {
        this.weatherHumidity = weatherHumidity;
    }

    public Double getWeatherMinTemp() {
        return weatherMinTemp;
    }

    public void setWeatherMinTemp(Double weatherMinTemp) {
        this.weatherMinTemp = weatherMinTemp;
    }

    public Double getWeatherMaxTemp() {
        return weatherMaxTemp;
    }

    public void setWeatherMaxTemp(Double weatherMaxTemp) {
        this.weatherMaxTemp = weatherMaxTemp;
    }
}
