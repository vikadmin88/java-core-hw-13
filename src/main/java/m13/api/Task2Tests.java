package m13.api;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Task2Tests {
    private final static String URL_USER_POSTS = "https://jsonplaceholder.typicode.com/users/%d/posts";
    private final static String URL_POST_COMMENTS = "https://jsonplaceholder.typicode.com/posts/%d/comments";

    public static void main(String[] args) {
        int userId = 1;
        String postsJson = getPostsByUserId(userId);
        List<Post> posts = JsonHelper.jsonToListObjects(postsJson, Post.class);
        int postId = posts.get(posts.size()-1).getId();

        String commentsJson = getCommentsByPostId(postId);

        String fileTo = "user-"+userId+"-post-"+postId+"-comments.json";
        saveCommentsToFile(fileTo, commentsJson);

        List<Comment> comments = JsonHelper.jsonToListObjects(commentsJson, Comment.class);
        for(Comment comment : comments) {
            System.out.println("Id: " + comment.getId());
            System.out.println("Name: " + comment.getEmail());
            System.out.println("Email: = " + comment.getEmail());
            System.out.println("Body: = " + comment.getBody());
        }
    }

    private static ClientAPI getClientAPI() {
        return new ClientJSoup();
    }

    private static String getPostsByUserId(int userId) {

        ClientAPI clientAPI = getClientAPI();
        Map<String,String> apiResult = clientAPI.get(String.format(URL_USER_POSTS, userId), null, null);

        return apiResult.get("respBody");
    }

    private static String getCommentsByPostId(int postId) {
        ClientAPI clientAPI = getClientAPI();
        Map<String,String> apiResult = clientAPI.get(String.format(URL_POST_COMMENTS, postId), null, null);

        return apiResult.get("respBody");
    }

    private static void saveCommentsToFile(String fileName, String content) {
        Runnable taskSave = new Runnable() {
            public void run() {
                System.out.println("Starting " + Thread.currentThread().getName());
                System.out.println("Write to file = " + fileName);
                File file = new File(fileName);

                try (FileWriter writer = new FileWriter(file))
                {
                    writer.write(content);
                    writer.flush();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("Finished " + Thread.currentThread().getName());
            }
        };
        new Thread(taskSave).start();
    }
}
