package common.dao;

import common.model.UserChat;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class UserChatDAO {
    public void saveUserChat(UserChat userChat) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(userChat);
            tx.commit();
        }
    }

    public List<UserChat> getUserChatsByUserId(int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM UserChat WHERE user.id = :userId", UserChat.class)
                    .setParameter("userId", userId)
                    .list();
        }
    }
}