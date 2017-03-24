package com.sad.sadcrm.hibernate;

import com.sad.sadcrm.HttpJsonException;
import com.sad.sadcrm.Parameters;
import com.sad.sadcrm.UserLoginException;
import com.sad.sadcrm.hibernate.exception.UserInsertException;
import com.sad.sadcrm.hibernate.exception.UserUpdateException;
import com.sad.sadcrm.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.sad.sadcrm.HttpJson.post;
import static com.sad.sadcrm.Parameters.getCredentials;

public class UserDAO {
    public static User login(String login, String password) {
        try {
            Parameters.useCredentials(login, password);
            JSONObject jsonUser = post("/user/login", getCredentials());
            return User.createFromJson(jsonUser.getJSONObject("user"));
        } catch (JSONException exception) {
            throw new RuntimeException("Missing \"id\" key", exception);
        } catch (HttpJsonException exception) {
            throw new UserLoginException(exception);
        }
    }

    public static void insertUser(User user) {
        try {
            post("/user/create", user.asParameters());
        } catch (HttpJsonException exception) {
            throw new UserInsertException(exception);
        }
    }

    public static void updateUser(User user) {
        try {
            post("/user/update", user.asParameters());
        } catch (HttpJsonException exception) {
            throw new UserUpdateException(exception);
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

        return (User) query.list().get(0);
    }

}
