package com.sad.sadcrm.hibernate;

import com.sad.sadcrm.HttpJsonException;
import com.sad.sadcrm.hibernate.exception.ClientInsertException;
import com.sad.sadcrm.hibernate.exception.ClientUpdateException;
import com.sad.sadcrm.model.Client;
import com.sad.sadcrm.model.User;
import org.hibernate.Query;
import org.hibernate.Session;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.sad.sadcrm.HttpJson.post;
import static java.util.Calendar.DAY_OF_MONTH;

public class ClientDAO {
    public static void insert(Client client) {
        try {
            post("/client/create", client.asParameters());
        } catch (HttpJsonException exception) {
            throw new ClientInsertException(exception.getMessage());
        }
    }

    public static void update(Client client) {
        try {
            post("/client/update", client.asParameters());
        } catch (HttpJsonException exception) {
            throw new ClientUpdateException(exception.getMessage());
        }
    }

    public static Client getClientById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Client where id=:id");
        query.setParameter("id", id);
        return (Client) query.list().get(0);
    }

    public static List<Client> searchByCreateDate(String date) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Client where creaded like :date");
        query.setParameter("date", date);
        return query.list();
    }

    public static List<Client> phonesFromDate(int days) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(DAY_OF_MONTH, -days);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(calendar.getTime());

        System.out.println("*** DATE > " + date);

        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Client where telDate > :date");
        query.setParameter("date", date);

        return query.list();
    }

    public static List<Client> searchClients() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Client");
        return query.list();
    }

    public static List<Client> searchBySurname(String surname) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Client where surname = :surname");
        query.setParameter("surname", surname);

        return query.list();
    }

    public static List<Client> searchByPesel(String pesel) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Client where pesel = :pesel");
        query.setParameter("pesel", pesel);

        return query.list();
    }

    public static List<Client> searchByUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Client where user_id = :user_id");
        query.setParameter("user_id", user.getId());
        return query.list();
    }

    public static List<Client> searchBySurnameAndPesel(String surname, String pesel) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Client where surname = :surname and pesel = :pesel");
        query.setParameter("surname", surname);
        query.setParameter("pesel", pesel);

        return query.list();
    }

    public static List<Client> searchByUserAndPesel(User user, String pesel) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Client where user_id= :user_id and pesel = :pesel");
        query.setParameter("user_id", user.getId());
        query.setParameter("pesel", pesel);

        return query.list();
    }

    public static List<Client> searchByUserSurnameAndPesel(User user, String surname, String pesel) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Client where user_id= :user_id and surname=:surname and pesel = :pesel");
        query.setParameter("user_id", user.getId());
        query.setParameter("surname", surname);
        query.setParameter("pesel", pesel);

        return query.list();
    }

    public static List<Client> searchByUserAndSurname(User user, String surname) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Client where user_id= :user_id and surname = :surname");
        query.setParameter("user_id", user.getId());
        query.setParameter("surname", surname);

        return query.list();
    }

    public static List<Client> searchBySurnameAndPeselAndHasMail(String surname, String pesel) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Client where mail!=:mail and surname=:surname and pesel = :pesel");
        query.setParameter("mail", "");
        query.setParameter("surname", surname);
        query.setParameter("pesel", pesel);

        return query.list();
    }

    public static List<Client> searchByPeselAndHasMail(String pesel) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Client where mail!=:mail and pesel = :pesel");
        query.setParameter("mail", "");
        query.setParameter("pesel", pesel);

        return query.list();
    }

    public static List<Client> searchBySurnameAndHasMail(String surname) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Client where mail!=:mail and surname = :surname");
        query.setParameter("mail", "");
        query.setParameter("surname", surname);

        return query.list();
    }

    public static List<Client> searchHasMail() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Client where mail!=:mail");
        query.setParameter("mail", "");
        return query.list();
    }

    public static List<Client> searchHasSellChance() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Client where sellChance is not null");
        return query.list();
    }
}
