import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class weatherApiData {

    public static void main(String[] args) {

        try {
            //Retrieve user input
            Scanner input = new Scanner(System.in);
            String city;
            do {
                System.out.print("Enter the city name(Say No to Quit): ");
                city = input.nextLine();

                if (city.equalsIgnoreCase("No")) break;

                //Get weather data
                HttpRequest postRequest = HttpRequest.newBuilder()
                        .uri(URI.create("https://api.tomorrow.io/v4/weather/forecast?location=42.3478,-71.0466&apikey=kFpVVl9Px24iQget7cCxEA9cUODLZv84"))
                        .build();

                HttpResponse<String> response = HttpClient.newHttpClient().send(postRequest, HttpResponse.BodyHandlers.ofString());

                System.out.println(response.statusCode());
                System.out.println(response.body());

            } while (!city.equalsIgnoreCase("No"));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
