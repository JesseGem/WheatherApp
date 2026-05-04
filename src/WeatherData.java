import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// This prevents the program from crashing if the API sends data we don't need
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {

    // Maps the JSON field "name" to our variable cityName
    @JsonProperty("name")
    private String cityName;

    // Maps the JSON object "main" to our internal MainDetails class
    @JsonProperty("main")
    private MainDetails main;

    // Getters and Setters (Jackson needs these to work)
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public MainDetails getMain() {
        return main;
    }

    public void setMain(MainDetails main) {
        this.main = main;
    }

    // This nested class handles the data inside the "main": { ... } part of the JSON
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MainDetails {
        private double temp;
        private int humidity;

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }
    }
}
