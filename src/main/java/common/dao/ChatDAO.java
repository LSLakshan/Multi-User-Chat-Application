package common.dao;

import common.model.Chat;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class ChatDAO {
    public void saveChat(Chat chat) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(chat);
            tx.commit();
        }
    }

    public List<Chat> getAllChats() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Chat", Chat.class).list();
        }
    }

    public Chat getChatById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Chat.class, id);
        }
    }
}
