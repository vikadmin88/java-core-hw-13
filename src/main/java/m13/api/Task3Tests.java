package m13.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task3Tests {
    private final static String URL_USER_TODOS = "https://jsonplaceholder.typicode.com/users/%d/todos";

    public static void main(String[] args) {
        int userId = 1;

        Map<String,String> params =  new HashMap<>();
        params.put("completed", "false");

        String todosJson = getToDoListByUserId(userId, params);
        List<ToDo> todos = JsonHelper.jsonToListObjects(todosJson, ToDo.class);

        todos.forEach(System.out::println);
    }

    private static ClientAPI getClientAPI() {
        return new ClientJSoup();
    }
    private static String getToDoListByUserId(int userId, Map<String,String> params) {

        ClientAPI clientAPI = getClientAPI();
        Map<String,String> apiResult = clientAPI.get(String.format(URL_USER_TODOS, userId), null, params);

        return apiResult.get("respBody");
    }
}
