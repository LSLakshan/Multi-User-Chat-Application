module com.chatapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.media;

    requires java.rmi;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.persistence; // ✅ This is the fix

    requires java.naming;

    opens common.model to org.hibernate.orm.core;
    opens controller to javafx.fxml;
    exports controller;

    opens com.chatapp to javafx.fxml;
    exports com.chatapp;
    exports common;
    exports common.model;

}
