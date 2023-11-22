package m13.api;

import java.util.Objects;

public class ToDo {
/*
    {
        "userId": 1,
        "id": 1,
        "title": "delectus aut autem",
        "completed": false
    },
*/
    private final int userId;
    private final int id;
    private final String title;
    private final boolean completed;

    public ToDo(int userId, int id, String title, boolean completed) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDo todo = (ToDo) o;
        return userId == todo.userId && id == todo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, id);
    }
}
