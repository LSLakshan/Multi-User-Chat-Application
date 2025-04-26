package common.dao;

import common.model.ChatMessage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class ChatMessageDAO {
    public void saveMessage(ChatMessage message) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(message);
            tx.commit();
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