package co.edu.unipiloto.laboratorio_retrofit;

public class Entity {

    private int userId, id;
    private String title, body;

    public Entity(int userId, int id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
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

    public String getBody() {
        return body;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "userId= " + userId + "\n" +
                "id= " + id + "\n" +
                "title= '" + title + "\n" +
                "body= '" + body;
    }
}
