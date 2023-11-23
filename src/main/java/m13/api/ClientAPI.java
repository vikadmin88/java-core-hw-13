package m13.api;

import java.util.Map;

public interface ClientAPI {

    Map<String, String> create(String reqUrl, String reqBody, Map<String, String> params);

    Map<String, String> get(String reqUrl, String reqBody, Map<String, String> params);

    Map<String, String> update(String reqUrl, String reqBody, Map<String, String> params);

    Map<String, String> delete(String reqUrl, Map<String, String> params);


}
