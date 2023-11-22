package m13.api;

import java.util.Objects;

public class Comment {
/*
    {
        "postId": 10,
        "id": 46,
        "name": "dignissimos et deleniti voluptate et quod",
        "email": "Jeremy.Harann@waino.me",
        "body": "exercitationem et id quae "
    },
*/

    private final int postId;
    private final int id;
    private final String name;
    private final String email;
    private final String body;

    public Comment(int postId, int id, String name, String email, String body) {
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public int getPostId() {
        return postId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "postId=" + postId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment that = (Comment) o;
        return postId == that.postId && id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, id);
    }
}
