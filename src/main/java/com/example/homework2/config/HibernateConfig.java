package com.example.homework2.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {
    private static final SessionFactory sessionFactory = buildSessionFactoryfromCfg();

    private static SessionFactory buildSessionFactoryfromCfg(){
        try {
            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        } catch (Exception e){
            throw new RuntimeException("SessionFactory initialization error: " + e.getMessage());
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public static void shutdown(){
        sessionFactory.close();
    }
}
