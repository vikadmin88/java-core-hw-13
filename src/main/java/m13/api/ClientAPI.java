package m13.api;

import java.util.Map;

public interface ClientAPI {
    /*
        GET 	/posts
        GET 	/posts/1
        GET 	/posts/1/comments
        GET 	/comments?postId=1
        POST 	/posts
        PUT 	/posts/1
        PATCH 	/posts/1
        DELETE 	/posts/1
    */
    Map<String, String> create(String reqUrl, String reqBody, Map<String, String> params);

    Map<String, String> get(String reqUrl, String reqBody, Map<String, String> params);

    Map<String, String> update(String reqUrl, String reqBody, Map<String, String> params);

    Map<String, String> delete(String reqUrl, Map<String, String> params);


}
