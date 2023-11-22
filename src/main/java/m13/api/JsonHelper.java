package m13.api;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JsonHelper {

    public static <T> String objToJson(T obj) {
        return new Gson().toJson(obj);
    }

    public static <T> String objToJson(Collection<T> obj) {
        return new Gson().toJson(obj);
    }

    public static <K,V> String objToJson(Map<K,V> map) {
        return new Gson().toJson(map);
    }

    public static <T> T jsonToObj(String json, Class<T> clazz) {
        return getObjectFromJsonGson(json, clazz);
    }

    private static <T> T getObjectFromJsonGson(String json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }

    public static <T> List<T> jsonToListObjects(String json, Class<T> clazz) {
        return getListObjectsFromJsonGson(json, clazz);
    }

    private static <T> List<T> getListObjectsFromJsonGson(String json, Class<T> clazz) {
        Type type = null;
        switch (clazz.getSimpleName()) {
            case "User":
                type = new TypeToken<List<User>>(){}.getType();
                break;
            case "Post":
                type = new TypeToken<List<Post>>(){}.getType();
                break;
            case "Comment":
                type = new TypeToken<List<Comment>>(){}.getType();
                break;
            case "ToDo":
                type = new TypeToken<List<ToDo>>(){}.getType();
                break;
            default:
        }

        if (type != null) {
            return new Gson().fromJson(json, type);
        }
        return null;
    }


}
