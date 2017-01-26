/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sad.sadcrm.hibernate;

import com.sad.sadcrm.model.Client;
import com.sad.sadcrm.model.ClientCnst;
import com.sad.sadcrm.model.User;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author dstachyra
 */
public class UserDAO {
    
    public static User login(String login, String password){
        
            Session session = HibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();
            
            Query query = session.createQuery("from User where login = :login AND password = :password ");
            query.setParameter("login", login);
            query.setParameter("password", password);

       
            List<User> list = query.list();
            
            session.close();
            
            if(list.size() != 1){
                return null;
            }

            return (User)list.get(0);
    }
    
    public static String insertUser(User user) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();

            Serializable s = session.save(user);
            session.getTransaction().commit();

            return ClientCnst.OK;
        } catch (HibernateException he) {
            return he.getCause().getMessage();
        }
    }

    public static List<User> searchUsersBySurnameAndName(String surname, String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from User where surname = :surname and name = :name");
        query.setParameter("surname", surname);  
        query.setParameter("name", name);  

        return query.list();
    }

    public static List<User> searchUsersBySurname(String surname) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from User where surname = :surname");
        query.setParameter("surname", surname);  

        return query.list();
    }

    public static List<User> searchUsersByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from User where name = :name");
        query.setParameter("name", name);  

        return query.list();
    }

    public static List<User> findUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from User");

        return query.list();
    }

    public static User getUserById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from User where id=:id");
        query.setParameter("id", id);               

        List list = query.list();
        if(list.size() == 0){
            // nie powinno sie nidgy wydazyc
            return null;
        }
        return (User) query.list().get(0);
    }
    
    public static String updateUser(User user){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();

            session.update(user);
            session.getTransaction().commit();
            
            session.close();

            return ClientCnst.OK;
        } catch (HibernateException he) {
            he.printStackTrace();
            return he.getCause().getMessage();
        }
    }
}
