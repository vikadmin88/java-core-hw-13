package m13.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task3Tests {
    private final static String URL_USER_TODOS = "https://jsonplaceholder.typicode.com/users/%d/todos";

    private static ClientAPI getClientAPI() {
        Map<String,String> props = new HashMap<>();
        props.put("Timeout", "60000");
        props.put("Content-Type", "application/json");
        props.put("Accept", "*/*");
        props.put("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.4 Safari/537.36");

        ClientAPI clientAPI = new ClientHttpURLConnectionsJava(props);
//        ClientAPI clientAPI = new ClientHttpClientJava(props);
//        ClientAPI clientAPI = new ClientJSoup(props);

        System.out.println("clientAPI === " + clientAPI.getClass().getSimpleName());
        return clientAPI;
    }

    public static void main(String[] args) {
        int userId = 1;

        Map<String,String> params =  new HashMap<>();
        params.put("completed", "false");

        String todosJson = getToDoListByUserId(userId, params);
        List<ToDo> todos = JsonHelper.jsonToListObjects(todosJson, ToDo.class);

        todos.forEach(System.out::println);
        System.out.println("todos.size() = " + todos.size());
    }

    private static String getToDoListByUserId(int userId, Map<String,String> params) {

        ClientAPI clientAPI = getClientAPI();
        Map<String,String> reqResult = clientAPI.get(String.format(URL_USER_TODOS, userId), null, params);

        return reqResult.get("respBody");
    }
}
