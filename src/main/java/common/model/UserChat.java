package common.model;

import javax.persistence.*;

@Entity
@Table(name = "user_chat", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "chat_id"})})
public class UserChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public Chat getChatId() {
        return chat;
    }

    public void setChatId(Chat chat) {
        this.chat = chat;
    }

    public User getUserId() {
        return user;
    }

    public void setUserId(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
