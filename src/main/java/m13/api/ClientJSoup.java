package m13.api;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClientJSoup implements ClientAPI {
    private final Map<String,String> props;

    public ClientJSoup(Map<String,String> props) {
        this.props = props;
    }

    private Connection connect(String reqUrl, Map<String, String> params) {
        Connection conn = Jsoup.connect(reqUrl)
                .header("Content-Type", props.get("Content-Type"))
                .header("Accept", props.get("Accept"))
                .followRedirects(false)
                .ignoreHttpErrors(true)
                .ignoreContentType(true)
                .userAgent(props.get("User-Agent"))
                .timeout(Integer.parseInt(props.get("Timeout")));
                if (params != null) {
                    conn.data(params);
                }
        return conn;
    }
    public Connection getConnection(String reqUrl) {
        return Jsoup.connect(reqUrl);
    }

    @Override
    public Map<String, String> create(String reqUrl, String reqBody, Map<String, String> params) {
        Map<String, String> retResp = getRetRespMap();

        try {
            Connection.Response conn = connect(reqUrl, params)
                    .method(Connection.Method.POST)
                    .requestBody(reqBody)
                    .execute();

            int respCode = conn.statusCode();
            retResp.put("respCode", String.valueOf(respCode));

            if (respCode >= 200 && respCode < 300){
                retResp.put("respBody", conn.parse().text());
            }

        } catch (IOException e) {
            retResp.put("respErr", e.getMessage());
            System.out.println("Error during POST request: " + e.getMessage());
        }

        return retResp;
    }

    @Override
    public Map<String, String> get(String reqUrl, String reqBody, Map<String, String> params) {
        Map<String, String> retResp = getRetRespMap();

        try {
            Connection.Response conn = connect(reqUrl, params)
                    .method(Connection.Method.GET)
                    .execute();

            int respCode = conn.statusCode();
            retResp.put("respCode", String.valueOf(respCode));

            if (respCode >= 200 && respCode < 300){
                retResp.put("respBody", conn.parse().text());
            }

        } catch (IOException e) {
            retResp.put("respCode", e.getMessage());
            System.out.println("Error during GET request: " + e.getMessage());
        }

        return retResp;
    }

    @Override
    public Map<String, String> update(String reqUrl, String reqBody, Map<String, String> params) {
        Map<String, String> retResp = getRetRespMap();

        try {
            Connection.Response conn = connect(reqUrl, params)
                    .method(Connection.Method.PUT)
                    .requestBody(reqBody)
                    .execute();

            int respCode = conn.statusCode();
            retResp.put("respCode", String.valueOf(respCode));

            if (respCode >= 200 && respCode < 300){
                retResp.put("respBody", conn.parse().text());
            }

        } catch (IOException e) {
            retResp.put("respCode", e.getMessage());
            System.out.println("Error during POST request: " + e.getMessage());
        }

        return retResp;
    }

    @Override
    public Map<String, String> delete(String reqUrl, Map<String, String> params) {
        Map<String, String> retResp = getRetRespMap();

        try {
            Connection.Response conn = connect(reqUrl, params)
                    .method(Connection.Method.DELETE)
                    .execute();

            int respCode = conn.statusCode();
            retResp.put("respCode", String.valueOf(respCode));

            if (respCode >= 200 && respCode < 300){
                retResp.put("respBody", conn.parse().text());
            }

        } catch (IOException e) {
            retResp.put("respCode", e.getMessage());
            System.out.println("Error during POST request: " + e.getMessage());
        }
        return retResp;
    }

    private Map<String, String> getRetRespMap() {
        Map<String, String> retResp = new HashMap<>();
        retResp.put("respErr", null);
        retResp.put("respCode", null);
        retResp.put("respBody", null);
        return retResp;
    }
}
