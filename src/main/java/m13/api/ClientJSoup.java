package m13.api;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClientJSoup implements ClientAPI {

    private Connection connect(String reqUrl, Map<String, String> params) {
        Connection conn = Jsoup.connect(reqUrl)
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .followRedirects(false)
                .ignoreHttpErrors(true)
                .ignoreContentType(true)
                .userAgent("Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.4 Safari/537.36");
        if (params != null) {
            conn.data(params);
        }
        return conn;
    }
    public Connection getConnect(String reqUrl) {
        return Jsoup.connect(reqUrl);
    }

    @Override
    public Map<String, String> create(String reqUrl, String reqBody, Map<String, String> params) {
        Map<String, String> retResp = new HashMap<>();
        retResp.put("respCode", null);
        retResp.put("respBody", null);

        try {
            Connection conn = connect(reqUrl, params)
                    .method(Connection.Method.POST)
                    .requestBody(reqBody);

            Connection.Response response = conn.execute();

            retResp.put("respCode", String.valueOf(response.statusCode()));
            retResp.put("respBody", response.parse().text());
        } catch (IOException e) {
            System.out.println("Error during POST request: " + e.getMessage());
        }

        return retResp;
    }

    @Override
    public Map<String, String> get(String reqUrl, String reqBody, Map<String, String> params) {
        Map<String, String> retResp = new HashMap<>();
        retResp.put("respCode", null);
        retResp.put("respBody", null);

        try {
            Connection conn = connect(reqUrl, params)
                    .method(Connection.Method.GET);

            Connection.Response response = conn.execute();

            retResp.put("respCode", String.valueOf(response.statusCode()));
            retResp.put("respBody", response.parse().text());
        } catch (IOException e) {
            retResp.put("respCode", e.getMessage());
            System.out.println("Error during GET request: " + e.getMessage());
        }

        return retResp;
    }

    @Override
    public Map<String, String> update(String reqUrl, String reqBody, Map<String, String> params) {
        Map<String, String> retResp = new HashMap<>();
        retResp.put("respCode", null);
        retResp.put("respBody", null);

        try {
            Connection conn = connect(reqUrl, params)
                    .method(Connection.Method.PUT)
                    .requestBody(reqBody);

            Connection.Response response = conn.execute();

            retResp.put("respCode", String.valueOf(response.statusCode()));
            retResp.put("respBody", response.parse().text());
        } catch (IOException e) {
            System.out.println("Error during POST request: " + e.getMessage());
        }

        return retResp;
    }

    @Override
    public Map<String, String> delete(String reqUrl, Map<String, String> params) {
        Map<String, String> retResp = new HashMap<>();
        retResp.put("respCode", null);
        retResp.put("respBody", null);
        try {
            Connection conn = connect(reqUrl, params)
                    .method(Connection.Method.DELETE);

            Connection.Response response = conn.execute();

            retResp.put("respCode", String.valueOf(response.statusCode()));
            retResp.put("respBody", response.parse().text());
        } catch (IOException e) {
            System.out.println("Error during POST request: " + e.getMessage());
        }
        return retResp;
    }
}
