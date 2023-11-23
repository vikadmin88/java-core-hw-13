package m13.api;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ClientHttpURLConnectionsJava implements ClientAPI {
    private final Map<String,String> props;

    public ClientHttpURLConnectionsJava(Map<String, String> props) {
        this.props = props;
    }

    private HttpURLConnection connect(String reqUrl, Map<String, String> params) throws IOException {
        if (params != null && !params.isEmpty()) {
            reqUrl = reqUrl.contains("?") ? reqUrl + "&" : reqUrl + "?";
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();
                reqUrl = reqUrl + (key + "=" + val);
            }
        }

        HttpURLConnection conn = (HttpURLConnection) new URL(reqUrl).openConnection();
        conn.setConnectTimeout(Integer.parseInt(props.get("Timeout")));
        conn.setRequestProperty("Content-Type", props.get("Content-Type"));
        conn.setRequestProperty("Accept", props.get("Accept"));
        conn.setRequestProperty("User-Agent", props.get("User-Agent"));


        return conn;
    }
    public HttpURLConnection getConnection(String reqUrl) throws IOException {
        return  (HttpURLConnection) new URL(reqUrl).openConnection();
    }
    @Override
    public Map<String, String> create(String reqUrl, String reqBody, Map<String, String> params) {
        Map<String, String> retResp = getRetRespMap();

        try {
            HttpURLConnection conn = connect(reqUrl, params);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStream out = conn.getOutputStream();
            out.write(reqBody.getBytes());
            out.flush();
            int respCode = conn.getResponseCode();
            retResp.put("respCode", String.valueOf(respCode));

            if (respCode >= 200 && respCode < 300){
                retResp.put("respBody", getRespStr(conn));
            } else {
                retResp.put("respBody", "{}");
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
            HttpURLConnection conn = connect(reqUrl, params);
            conn.setRequestMethod("GET");
            int respCode = conn.getResponseCode();

            retResp.put("respCode", String.valueOf(respCode));
            if (respCode >= 200 && respCode < 300){
                retResp.put("respBody", getRespStr(conn));
            } else {
                retResp.put("respBody", "{}");
            }
            conn.disconnect();

        } catch (IOException e) {
            retResp.put("respErr", e.getMessage());
            System.out.println("Error during GET request: " + e.getMessage());
        }
        return retResp;
    }

    @Override
    public Map<String, String> update(String reqUrl, String reqBody, Map<String, String> params) {
        Map<String, String> retResp = getRetRespMap();

        try {
            HttpURLConnection conn = connect(reqUrl, params);
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            OutputStream out = conn.getOutputStream();
            out.write(reqBody.getBytes());
            out.flush();
            int respCode = conn.getResponseCode();
            retResp.put("respCode", String.valueOf(respCode));

            if (respCode >= 200 && respCode < 300){
                retResp.put("respBody", getRespStr(conn));
            } else {
                retResp.put("respBody", "{}");
            }

        } catch (IOException e) {
            retResp.put("respErr", e.getMessage());
            System.out.println("Error during POST request: " + e.getMessage());
        }

        return retResp;
    }

    @Override
    public Map<String, String> delete(String reqUrl, Map<String, String> params) {
        Map<String, String> retResp = getRetRespMap();

        try {
            HttpURLConnection conn = connect(reqUrl, params);
            conn.setRequestMethod("DELETE");
            conn.setDoOutput(true);

            int respCode = conn.getResponseCode();
            retResp.put("respCode", String.valueOf(respCode));

            if (respCode >= 200 && respCode < 300){
                retResp.put("respBody", getRespStr(conn));
            } else {
                retResp.put("respBody", "{}");
            }

        } catch (IOException e) {
            retResp.put("respErr", e.getMessage());
            System.out.println("Error during POST request: " + e.getMessage());
        }

        return retResp;
    }

    private String getRespStr(HttpURLConnection conn) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder sb = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            sb.append(inputLine.trim());
        }
        in.close();
        return sb.toString();
    }

    private Map<String, String> getRetRespMap() {
        Map<String, String> retResp = new HashMap<>();
        retResp.put("respErr", null);
        retResp.put("respCode", null);
        retResp.put("respBody", null);
        return retResp;
    }
}
