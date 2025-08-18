package com.example.homework2.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {
    private static SessionFactory sessionFactory;

    private static void buildSessionFactoryFromCfg(){
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        } catch (Exception e){
            throw new RuntimeException("SessionFactory initialization error: " + e.getMessage());
        }
    }

    public static void init(String jdbcUrl, String username, String  password, String hdm2ddl){
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .setProperty("hibernate.connection.url", jdbcUrl)
                    .setProperty("hibernate.connection.username", username)
                    .setProperty("hibernate.connection.password", password)
                    .setProperty("hibernate.hbm2ddl.auto", hdm2ddl)
                    .buildSessionFactory();
            System.out.println("Session factory:"  + sessionFactory);
        } catch (Exception e){
            throw new RuntimeException("SessionFactory initialization error: " + e.getMessage());
        }
    }

    public static SessionFactory getSessionFactory(){
        if (sessionFactory == null) System.out.println("sessionfactory null");
        return sessionFactory;
    }

    public static void shutdown(){
        sessionFactory.close();
    }
}
