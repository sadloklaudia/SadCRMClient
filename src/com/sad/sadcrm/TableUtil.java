package com.sad.sadcrm;

import com.sad.sadcrm.model.Client;
import com.sad.sadcrm.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TableUtil {
    public static void displayClients(List<Client> clients, JTable tableClients) {
        tableClients.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableClients.setFillsViewportHeight(true);

        DefaultTableModel uneditableModel = createUneditableTableModel();

        uneditableModel.setColumnIdentifiers(getColumnNames());
        if (clients == null) {
            tableClients.setModel(uneditableModel);
            return;
        }

        for (Client client : clients) {
            uneditableModel.addRow(getTableModelForClient(client));
        }

        tableClients.setModel(uneditableModel);
    }

    private static DefaultTableModel createUneditableTableModel() {
        return new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private static Object[] getTableModelForClient(Client client) {
        Object[] objects = new Object[6];
        objects[0] = client.getIdClient();
        objects[1] = client.getName();
        objects[2] = client.getSurname();
        objects[3] = client.getPesel();
        objects[4] = client.getMail();
        objects[5] = client.getUser().getName() + " " + client.getUser().getSurname();
        return objects;
    }

    private static Object[] getColumnNames() {
        Object[] columnNames = new Object[6];
        columnNames[0] = "Id";
        columnNames[1] = "Name";
        columnNames[2] = "Nazwisko";
        columnNames[3] = "Pesel";
        columnNames[4] = "Mail";
        columnNames[5] = "Opiekun";
        return columnNames;
    }

    public static void displayUsers(List<User> users, JTable tableUsers) {
        tableUsers.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUsers.setFillsViewportHeight(true);

        DefaultTableModel uneditableModel = createUneditableTableModel();

        uneditableModel.setColumnIdentifiers(getUserColumnNames());
        if (users == null) {
            tableUsers.setModel(uneditableModel);
            return;
        }

        for (User user : users) {
            uneditableModel.addRow(getTableForUser(user));
        }

        tableUsers.setModel(uneditableModel);
    }

    private static Object[] getUserColumnNames() {
        Object[] tableColumnNames = new Object[5];
        tableColumnNames[0] = "Id";
        tableColumnNames[1] = "Name";
        tableColumnNames[2] = "Nazwisko";
        tableColumnNames[3] = "Login";
        tableColumnNames[4] = "Data utworzenia";
        return tableColumnNames;
    }

    private static Object[] getTableForUser(User user) {
        Object[] objects = new Object[5];
        objects[0] = user.getId();
        objects[1] = user.getName();
        objects[2] = user.getSurname();
        objects[3] = user.getLogin();
        objects[4] = user.getCreated();
        return objects;
    }

    public static void displayUsersForManager(List<User> users, JTable tableUsers) {
        tableUsers.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUsers.setFillsViewportHeight(true);

        DefaultTableModel uneditableTableModel = createUneditableTableModel();

        uneditableTableModel.setColumnIdentifiers(createColumnsForUsers());
        if (users == null) {
            tableUsers.setModel(uneditableTableModel);
            return;
        }

        for (User user : users) {
            uneditableTableModel.addRow(createColumnsForUser(user));
        }

        tableUsers.setModel(uneditableTableModel);
    }

    private static Object[] createColumnsForUser(User user) {
        Object[] objects = new Object[3];
        objects[0] = user.getId();
        objects[1] = user.getName();
        objects[2] = user.getSurname();
        return objects;
    }

    private static Object[] createColumnsForUsers() {
        Object[] tableColumnNames = new Object[3];
        tableColumnNames[0] = "Id";
        tableColumnNames[1] = "Name";
        tableColumnNames[2] = "Nazwisko";
        return tableColumnNames;
    }
}
