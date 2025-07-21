package HttpProject.handlers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpResponse;

public class JsonBodyHandler implements HttpResponse.BodyHandler<JsonNode>{

    private  final ObjectMapper objectMapper;

    public JsonBodyHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public  static  JsonBodyHandler create(ObjectMapper objectMapper){
        return  new JsonBodyHandler(objectMapper);
    }

    @Override
    public HttpResponse.BodySubscriber<JsonNode> apply(HttpResponse.ResponseInfo responseInfo) {
        return HttpResponse.BodySubscribers.mapping(HttpResponse.BodySubscribers.ofByteArray(),byteArray->{
            try{
                return  objectMapper.readTree(byteArray);
            } catch (IOException e) {
                throw new RuntimeException("Failed to parse JSON",e);
            }
        });
    }
}
