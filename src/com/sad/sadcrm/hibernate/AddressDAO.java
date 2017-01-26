package com.sad.sadcrm.hibernate;

import com.sad.sadcrm.model.Address;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class AddressDAO {

    public static void insertAddress(Address address) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();
            session.save(address);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        }
    }

    public static void updateAddress(Address address) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();
            session.merge(address);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
    }
}
