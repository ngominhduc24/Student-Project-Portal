package swp.studentprojectportal.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class InstanceSingleton {
    private static InstanceSingleton instance;

    // Private constructor to avoid client applications to use constructor
    private InstanceSingleton() {
    }

    public static InstanceSingleton getInstance() {
        if (instance == null) {
            instance = new InstanceSingleton();
        }
        return instance;
    }

    public String callApiFap(String hrefApi) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create an HTTP GET request
            HttpGet httpGet = new HttpGet(hrefApi);

            // Execute the request and get the response
            CloseableHttpResponse response = httpClient.execute(httpGet);

            // Get the response entity as a string
            String responseBody = EntityUtils.toString(response.getEntity());

            responseBody = responseBody.substring(1, responseBody.length() - 1).replace("\"", "");

            response.close();

            return responseBody;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
