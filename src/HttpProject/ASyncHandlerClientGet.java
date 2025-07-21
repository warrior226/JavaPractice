package HttpProject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import static java.net.HttpURLConnection.HTTP_OK;

public class ASyncHandlerClientGet {

    public static void main(String[] args) {
        try{
           //URL url = new URL("http://example.com");
            URL url = new URL("http://localhost:8080");
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request =HttpRequest.newBuilder()
                    .GET()
                    .uri(url.toURI())
                    .header("User-Agent","Chrome")
                    .headers("Accept","application/json","Accept","text/html")
                    .timeout(Duration.ofSeconds(30)) //If the response is not received within the specified timeout then an HttpTimeoutException is thrown from HttpClient::send or HttpClient::sendAsync completes exceptionally with an HttpTimeoutExceptio
                    .build();
            HttpResponse<Stream<String>> response;
            CompletableFuture<HttpResponse<Stream<String>>> responseFuture = httpClient.sendAsync(request,HttpResponse.BodyHandlers.ofLines()); //Block until it receives a response
            responseFuture.thenApply(ASyncHandlerClientGet::filterResponse)
                    .thenApply(ASyncHandlerClientGet::transformResponse)
                    .thenAccept(ASyncHandlerClientGet::printResponse);
            System.out.println(
                    "Ten jobs to do besides handling the response"
            );
            int jobs=0;
            while(jobs++<10){
                TimeUnit.SECONDS.sleep(1);
                System.out.printf("Job %d",jobs);
            }

        }catch (IOException | URISyntaxException | InterruptedException e){
            throw  new RuntimeException(e);
        }
    }

    private static  void handleResponse(HttpResponse<Stream<String>> response){

        if(response.statusCode()==HTTP_OK){
            response.body().filter(s->s.contains("<h1>"))
                    .map(s->s.replaceAll("<[^>]*>","").strip())
                    .forEach(System.out::println);
        }else{
            System.out.println("Error reading response "+response.uri());
        }
    }
    private static  Stream<String> filterResponse(HttpResponse<Stream<String>> response){
        System.out.println("Filtering Response...");
        if(response.statusCode()==HTTP_OK){
          return   response.body().filter(s->s.contains("<h1>"));
        }else{
            return  Stream.empty();
        }
    }

    private static  Stream<String> transformResponse(Stream<String> response){
        System.out.println("transforming Response");
        return response.map(s->s.replaceAll("<[^>]*>","").strip());
    }

    private static void printResponse(Stream<String>response){
        System.out.println("printing Response");
        response.forEach(System.out::println);
    }
}
