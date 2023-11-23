package m13.api;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

//https://reflectoring.io/comparison-of-java-http-clients/
public class ClientHttpClientJava implements ClientAPI{

    private final Map<String,String> props;

    public ClientHttpClientJava(Map<String, String> props) {
        this.props = props;
    }

    private HttpRequest.Builder connect(String reqUrl, Map<String, String> params) throws URISyntaxException {
        if (params != null && !params.isEmpty()) {
            reqUrl = reqUrl.contains("?") ? reqUrl + "&" : reqUrl + "?";
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();
                reqUrl = reqUrl + (key + "=" + val);
            }
        }

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(new URI(reqUrl))
                .timeout(Duration.ofMillis(Integer.parseInt(props.get("Timeout"))))
                .header("Content-Type", props.get("Content-Type"))
                .header("Accept", props.get("Accept"))
                .header("User-Agent", props.get("User-Agent"));

        return builder;
    }
    public HttpRequest getConnection(String reqUrl) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(new URI(reqUrl))
                .build();
    }
    @Override
    public Map<String, String> create(String reqUrl, String reqBody, Map<String, String> params) {

        Map<String, String> retResp = getRetRespMap();
        HttpClient client = getClient();
        HttpRequest.Builder builder;
        int respCode = 0;

        try {
            builder = connect(reqUrl, params);
            HttpRequest request = builder
                    .POST(HttpRequest.BodyPublishers.ofString(reqBody))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            respCode = response.statusCode();
            retResp.put("respCode", String.valueOf(respCode));
            retResp.put("respBody", response.body().replace("\n", ""));

        } catch (URISyntaxException | IOException | InterruptedException e) {
            retResp.put("respErr", e.getMessage());
            System.out.println("Error during GET request: " + e.getMessage());
        }

        if (respCode < 200 || respCode > 299){
            retResp.put("respBody", "{}");
        }
        return retResp;
    }

    @Override
    public Map<String, String> get(String reqUrl, String reqBody, Map<String, String> params) {

        Map<String, String> retResp = getRetRespMap();
        HttpClient client = getClient();
        HttpRequest.Builder builder;
        int respCode = 0;

        try {
            builder = connect(reqUrl, params);
            HttpRequest request = builder.GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            respCode = response.statusCode();
            retResp.put("respCode", String.valueOf(respCode));
            retResp.put("respBody", response.body().replace("\n", ""));

        } catch (URISyntaxException | IOException | InterruptedException e) {
            retResp.put("respErr", e.getMessage());
            System.out.println("Error during GET request: " + e.getMessage());
        }

            if (respCode < 200 || respCode > 299){
                retResp.put("respBody", "{}");
            }

        return retResp;
    }

    @Override
    public Map<String, String> update(String reqUrl, String reqBody, Map<String, String> params) {

        Map<String, String> retResp = getRetRespMap();
        HttpClient client = getClient();
        HttpRequest.Builder builder;
        int respCode = 0;

        try {
            builder = connect(reqUrl, params);
            HttpRequest request = builder
                    .PUT(HttpRequest.BodyPublishers.ofString(reqBody))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            respCode = response.statusCode();
            retResp.put("respCode", String.valueOf(respCode));
            retResp.put("respBody", response.body().replace("\n", ""));

        } catch (URISyntaxException | IOException | InterruptedException e) {
            retResp.put("respErr", e.getMessage());
            System.out.println("Error during GET request: " + e.getMessage());
        }

        if (respCode < 200 || respCode > 299){
            retResp.put("respBody", "{}");
        }

        return retResp;
    }

    @Override
    public Map<String, String> delete(String reqUrl, Map<String, String> params) {

        Map<String, String> retResp = getRetRespMap();
        HttpClient client = getClient();
        HttpRequest.Builder builder;
        int respCode = 0;

        try {
            builder = connect(reqUrl, params);
            HttpRequest request = builder.DELETE().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            respCode = response.statusCode();
            retResp.put("respCode", String.valueOf(respCode));
            retResp.put("respBody", response.body().replace("\n", ""));

        } catch (URISyntaxException | IOException | InterruptedException e) {
            retResp.put("respErr", e.getMessage());
            System.out.println("Error during GET request: " + e.getMessage());
        }

        if (respCode < 200 || respCode > 299){
            retResp.put("respBody", "{}");
        }

        return retResp;
    }

    private HttpClient getClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofMillis(Integer.parseInt(props.get("Timeout"))))
                .build();
    }

    private Map<String, String> getRetRespMap() {
        Map<String, String> retResp = new HashMap<>();
        retResp.put("respErr", null);
        retResp.put("respCode", null);
        retResp.put("respBody", null);
        return retResp;
    }

}
