package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import common.model.*;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration config = new Configuration().configure(); // Loads hibernate.cfg.xml
            config.addAnnotatedClass(User.class);
            config.addAnnotatedClass(Chat.class);
            config.addAnnotatedClass(UserChat.class);
            config.addAnnotatedClass(ChatMessage.class); // If you're using ChatMessage

            sessionFactory = config.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
