package HttpProject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpExamplePost {

    public static void main(String[] args) {
        try{
           //URL url = new URL("http://example.com");
            URL url = new URL("http://localhost:8080");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent","Chrome");
            connection.setRequestProperty("Accept","application/json,text/html");
            connection.setReadTimeout(30000);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            String parameters="first=Joe&last=Smith";
            int length=parameters.getBytes().length;
            connection.setRequestProperty("Content-Lenght",String.valueOf(length ));
            DataOutputStream output=new DataOutputStream(connection.getOutputStream());
            output.writeBytes(parameters);
            output.flush();
            output.close();
            int responseCode=connection.getResponseCode();
            System.out.println("Response code: "+responseCode);
        }catch (IOException e){
            throw  new RuntimeException(e);
        }
    }
}
