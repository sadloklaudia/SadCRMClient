/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sad.sadcrm.hibernate;

import com.sad.sadcrm.model.Address;
import com.sad.sadcrm.model.Client;
import com.sad.sadcrm.model.ClientCnst;
import com.sad.sadcrm.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author dstachyra
 */
public class AddressDAO {

    public static void insertAddress(Address address) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();

            session.save(address);
            session.getTransaction().commit();
            
            session.close();

        } catch (HibernateException he) {
            he.printStackTrace();
        }
    }
    
    public static String updateAddress(Address address){
        try {

              Session  session = HibernateUtil.getSessionFactory().openSession();
            

            session.beginTransaction();

            session.merge(address);
            session.getTransaction().commit();
            
            session.close();

            return ClientCnst.OK;
        } catch (HibernateException he) {
            he.printStackTrace();
            return he.getCause().getMessage();
        }
    }

}
