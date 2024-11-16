module org.example.hsf301 {
    requires javafx.controls;
    requires javafx.fxml;

//    requires com.dlsc.formsfx;
    requires java.naming;  // Java Naming and Directory Interface (JNDI) API.
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;
    requires javax.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires javax.mail.api;
    requires org.slf4j;
    requires thymeleaf;
    requires io.github.cdimascio.dotenv.java;
    requires org.controlsfx.controls;
    requires java.desktop;
    requires java.net.http;
    requires java.management;
    requires java.transaction;

    opens org.example.hsf301 to javafx.fxml;
    exports org.example.hsf301;

    exports org.example.hsf301.dtos.request;

    opens org.example.hsf301.views to javafx.graphics;
    exports org.example.hsf301.views;

    exports org.example.hsf301.enums;

    opens org.example.hsf301.services to org.hibernate.orm.core;
    exports org.example.hsf301.services;

    opens org.example.hsf301.pojos to org.hibernate.orm.core;
    exports org.example.hsf301.pojos;

    opens org.example.hsf301.daos to org.hibernate.orm.core;
    exports org.example.hsf301.controllers;
    opens org.example.hsf301.controllers to javafx.fxml;
    exports org.example.hsf301.constants;
    opens org.example.hsf301.constants to javafx.fxml;
    exports org.example.hsf301.utils;
    opens org.example.hsf301.utils to javafx.fxml;
    exports org.example.hsf301.layouts;
    opens org.example.hsf301.layouts to javafx.fxml;
    exports org.example.hsf301.controllers.managements;
    opens org.example.hsf301.controllers.managements to javafx.fxml;
}