package AutoSign.functions;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

public class SendUrl {
    
    // THIS METHOD IS RUN WITHIN THE SUBMITURL METHOD, AND IT ALLOWS TO SEND THE SUBMITTED URL TO THE PYTHON SERVER FOR VIDEO TRANSLATION
    public static void sendurl(String url) throws JSONException {

        // Creates new json object
        JSONObject json = new JSONObject();
        // Pass the url to the json object
        json.put("url", url);

        // create HttpClient to submit the request
        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            // Create post request to python server
            HttpPost request = new HttpPost("https://python-microservice.herokuapp.com/download");
            // Assign json obect as parameter of the request
            StringEntity params = new StringEntity(json.toString()); 
            // Add header to the request
            request.addHeader("content-type", "application/json");
            // Set parameter(json object) as entity of the request
            request.setEntity(params);
            // Execute post request and get the response
            HttpResponse response = httpClient.execute(request);
        } catch (Exception ex) {
        }
    }
}
