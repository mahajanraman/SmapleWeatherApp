package weatherapp.test.com.smapleweatherapp;

/**
 * Created by raman on 7/23/2015.
 * storing the values related to weatherDescription
 */
public class WSDescription {

    private String mainDescription;
    private String fullDescription;
    private String iconId;

    public void setMainDescription(String mainDescription) {
        this.mainDescription = mainDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getMainDescription() {
        return mainDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public String getIconId() {
        return iconId;
    }
}
