package common.dao;

import common.model.Chat;
import common.model.ChatMessage;
import common.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class ChatMessageDAO {
    public void saveMessage(ChatMessage message) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            // Load Chat and User again inside this session
            Chat chat = session.get(Chat.class, message.getChat().getId());
            User sender = session.get(User.class, message.getSender().getId());

            if (chat == null || sender == null) {
                System.out.println("Error: Chat or Sender not found in database.");
                return;
            }

            message.setChat(chat);
            message.setSender(sender);

            session.save(message);
            tx.commit();
            System.out.println("Message saved successfully: " + message);
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }


    public List<ChatMessage> getMessagesByChatId(int chatId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM ChatMessage WHERE chat.id = :chatId", ChatMessage.class)
                    .setParameter("chatId", chatId)
                    .list();
        }
    }
}