/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sad.sadcrm;

import com.sad.sadcrm.model.Client;
import com.sad.sadcrm.model.User;
import java.util.List;
import java.util.ListIterator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dstachyra
 */
public class TableUtil {
    
    public static void displayClients(List<Client> clients, JTable tableClients) {
        tableClients.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableClients.setFillsViewportHeight(true);

        DefaultTableModel aModel = new DefaultTableModel() {
            //setting the jtable read only

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //setting the column name
        Object[] tableColumnNames = new Object[6];
        tableColumnNames[0] = "Id";
        tableColumnNames[1] = "Name";
        tableColumnNames[2] = "Nazwisko";
        tableColumnNames[3] = "Pesel";
        tableColumnNames[4] = "Mail";
        tableColumnNames[5] = "Opiekun";

        aModel.setColumnIdentifiers(tableColumnNames);
        if (clients == null) {
            tableClients.setModel(aModel);
            return;
        }

        Object[] objects = new Object[6];
        ListIterator<Client> lstrg = clients.listIterator();
        //populating the tablemodel
        while (lstrg.hasNext()) {
            Client client = lstrg.next();
            objects[0] = client.getIdClient();
            objects[1] = client.getName();
            objects[2] = client.getSurname();
            objects[3] = client.getPesel();
            objects[4] = client.getMail();
            objects[5] = client.getUser().getName()+" "+client.getUser().getSurname();

            aModel.addRow(objects);
        }

        //binding the jtable to the model
        tableClients.setModel(aModel);
    }
    
    public static void displayUsers(List<User> users, JTable tableUsers) {
        tableUsers.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUsers.setFillsViewportHeight(true);

        DefaultTableModel aModel = new DefaultTableModel() {
            //setting the jtable read only

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
     
        //setting the column name
        Object[] tableColumnNames = new Object[5];
        tableColumnNames[0] = "Id";
        tableColumnNames[1] = "Name";
        tableColumnNames[2] = "Nazwisko";
        tableColumnNames[3] = "Login";
        tableColumnNames[4] = "Data utworzenia";

        aModel.setColumnIdentifiers(tableColumnNames);
        if (users == null) {
            tableUsers.setModel(aModel);
            return;
        }

        Object[] objects = new Object[5];
        ListIterator<User> lstrg = users.listIterator();
        //populating the tablemodel
        while (lstrg.hasNext()) {
            User user = lstrg.next();
            objects[0] = user.getId();
            objects[1] = user.getName();
            objects[2] = user.getSurname();
            objects[3] = user.getLogin();
            objects[4] = user.getCreated();

            aModel.addRow(objects);
        }

        //binding the jtable to the model
        tableUsers.setModel(aModel);
    }
    
    public static void displayUsersForManager(List<User> users, JTable tableUsers) {
        tableUsers.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUsers.setFillsViewportHeight(true);

        DefaultTableModel aModel = new DefaultTableModel() {
            //setting the jtable read only

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
     
        //setting the column name
        Object[] tableColumnNames = new Object[3];
        tableColumnNames[0] = "Id";
        tableColumnNames[1] = "Name";
        tableColumnNames[2] = "Nazwisko";

        aModel.setColumnIdentifiers(tableColumnNames);
        if (users == null) {
            tableUsers.setModel(aModel);
            return;
        }

        Object[] objects = new Object[3];
        ListIterator<User> lstrg = users.listIterator();
        //populating the tablemodel
        while (lstrg.hasNext()) {
            User user = lstrg.next();
            objects[0] = user.getId();
            objects[1] = user.getName();
            objects[2] = user.getSurname();

            aModel.addRow(objects);
        }

        //binding the jtable to the model
        tableUsers.setModel(aModel);
    }
}
