package m13.api;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task2Tests {
    private final static String URL_USER_POSTS = "https://jsonplaceholder.typicode.com/users/%d/posts";
    private final static String URL_POST_COMMENTS = "https://jsonplaceholder.typicode.com/posts/%d/comments";

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
        String postsJson = getPostsByUserId(userId);
        List<Post> posts = JsonHelper.jsonToListObjects(postsJson, Post.class);
        if (posts == null || posts.isEmpty()) {
            System.out.println("User with id " + userId + " not found.");
            System.exit(0);
        }
        int postId = posts.get(posts.size()-1).getId();

        String commentsJson = getCommentsByPostId(postId);

        String fileToSave = String.format("user-%d-post-%d-comments.json", userId, postId);
        System.out.println("Write comments to file = " + fileToSave);
        saveCommentsToFile(fileToSave, commentsJson);

        List<Comment> comments = JsonHelper.jsonToListObjects(commentsJson, Comment.class);
        for(Comment comment : comments) {
            System.out.println("Id: " + comment.getId());
            System.out.println("Name: " + comment.getEmail());
            System.out.println("Email: = " + comment.getEmail());
            System.out.println("Body: = " + comment.getBody());
        }
    }

    private static String getPostsByUserId(int userId) {

        ClientAPI clientAPI = getClientAPI();
        Map<String,String> reqResult = clientAPI.get(String.format(URL_USER_POSTS, userId), null, null);

        return reqResult.get("respBody");
    }

    private static String getCommentsByPostId(int postId) {
        ClientAPI clientAPI = getClientAPI();
        Map<String,String> reqResult = clientAPI.get(String.format(URL_POST_COMMENTS, postId), null, null);

        return reqResult.get("respBody");
    }

    private static void saveCommentsToFile(String fileName, String content) {
        Runnable taskSave = new Runnable() {
            public void run() {
                File file = new File(fileName);
                try (FileWriter writer = new FileWriter(file))
                {
                    writer.write(content);
                    writer.flush();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        };
        new Thread(taskSave).start();
    }
}
