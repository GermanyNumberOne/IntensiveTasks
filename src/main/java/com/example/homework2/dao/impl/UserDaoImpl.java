package com.example.homework2.dao.impl;

import com.example.homework2.config.HibernateConfig;
import com.example.homework2.dao.api.UserDao;
import com.example.homework2.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private final SessionFactory sessionFactory = HibernateConfig.getSessionFactory();

    public void save(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        }catch (Exception e)
        {
            if(transaction != null)transaction.rollback();
            throw new RuntimeException(e.getMessage());
        }
    }


    public void update(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
        } catch (Exception e){
            if(transaction != null)transaction.rollback();
            throw new RuntimeException(e.getMessage());
        }
    }

    public User read(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    public List<User> getAll(){
        try (Session session = sessionFactory.openSession()){
            return session.createQuery("select u from User u", User.class).list();
        }
    }

    public void delete(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.remove(user);
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            throw new RuntimeException(e.getMessage());
        }
    }
}
