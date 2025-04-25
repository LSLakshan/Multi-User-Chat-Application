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

    // Getters and Setters
}
