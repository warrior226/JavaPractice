package HttpProject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpExample {

    public static void main(String[] args) {
        try{
           //URL url = new URL("http://example.com");
            URL url = new URL("http://localhost:8080");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent","Chrome");
            connection.setRequestProperty("Accept","application/json,text/html");
            connection.setReadTimeout(30000);

            int responseCode=connection.getResponseCode();
            System.out.println("Response code: "+responseCode);
        }catch (IOException e){
            throw  new RuntimeException(e);
        }
    }
}
