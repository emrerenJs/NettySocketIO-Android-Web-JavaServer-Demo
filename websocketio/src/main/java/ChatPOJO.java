import java.io.Serializable;

public class ChatPOJO implements Serializable {

    private String username;
    private String message;

    public ChatPOJO() {
    }

    public ChatPOJO(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
