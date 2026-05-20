import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Scanner;

public class weatherApiData {
    public static void main(String[] args) {

        //Move API key to a variable for easier management
        String apiKey = "4e57404bb7adb9075184e20f3d60c5f7";
        Scanner input = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();
        String city;

        try {
            do {
                System.out.print("City name(Say No to quit): ");
                String cityName = input.nextLine();

                if (cityName.equalsIgnoreCase("No")) {
                    System.out.println("Thank You!");
                    input.close();
                    return;
                }

                //Dynamic URL Construction
                //We encode the string to handle special characters and spaces safely
                String encodedCityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);
                String urlString = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", encodedCityName, apiKey);


                //Dynamic Request
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(urlString))
                        .GET()
                        .build();

                //Send And Consume
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                try {
                    String responseBody = response.body();

                    // Initialize the Jackson Mapper
                    ObjectMapper mapper = new ObjectMapper();

                    // Perform the Deserialization
                    WeatherData data = mapper.readValue(responseBody, WeatherData.class);

                    // Now you can access your data via the getters you wrote!
                    System.out.println("--- Weather Report ---");
                    System.out.println("City: " + data.getCityName());
                    System.out.println("Current Temp: " + data.getMain().getTemp() + "°");
                    System.out.println("Humidity: " + data.getMain().getHumidity() + "%");

                    if (data.getWeather().length > 0){
                        System.out.println("Description: " + data.getWeather()[0].getDescription());
                    }

                } catch (Exception e) {
                    System.out.println("Error parsing JSON: " + e.getMessage());
                    e.printStackTrace();
                }

                System.out.println("Status code: " + response.statusCode());

                if (response.statusCode() == 200) {
                    System.out.println("Weather Data for " + cityName + ":");
                    System.out.println(response.body());
                }else{
                    ObjectMapper mapper = new ObjectMapper();
                    WeatherData weather = mapper.readValue(response.body(), WeatherData.class);

                    System.out.println("The weather in " + weather.getCityName() + " is " + weather.getMain().getTemp() + " degrees.");
                }

                if (response.statusCode() == 404) {
                    System.out.println("Weather Data Not Found. Enter a valid city name.");
                }
            } while (true);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            input.close();
        }
    }
}