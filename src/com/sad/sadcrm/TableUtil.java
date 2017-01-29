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

        DefaultTableModel model = getUneditableTableModel();

        Object[] tableColumnNames = new Object[6];
        tableColumnNames[0] = "Id";
        tableColumnNames[1] = "Name";
        tableColumnNames[2] = "Nazwisko";
        tableColumnNames[3] = "Pesel";
        tableColumnNames[4] = "Mail";
        tableColumnNames[5] = "Opiekun";

        model.setColumnIdentifiers(tableColumnNames);
        if (clients == null) {
            tableClients.setModel(model);
            return;
        }

        for (Client client : clients) {
            model.addRow(getColumnsForClient(client));
        }

        tableClients.setModel(model);
    }

    public static void displayUsers(List<User> users, JTable tableUsers) {
        tableUsers.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUsers.setFillsViewportHeight(true);

        DefaultTableModel model = getUneditableTableModel();

        Object[] tableColumnNames = new Object[5];
        tableColumnNames[0] = "Id";
        tableColumnNames[1] = "Name";
        tableColumnNames[2] = "Nazwisko";
        tableColumnNames[3] = "Login";
        tableColumnNames[4] = "Data utworzenia";

        model.setColumnIdentifiers(tableColumnNames);
        if (users == null) {
            tableUsers.setModel(model);
            return;
        }

        for (User user : users) {

            model.addRow(getColumnsForUser(user));
        }

        tableUsers.setModel(model);
    }

    public static void displayUsersForManager(List<User> users, JTable tableUsers) {
        tableUsers.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUsers.setFillsViewportHeight(true);

        DefaultTableModel model = getUneditableTableModel();

        Object[] tableColumnNames = new Object[3];
        tableColumnNames[0] = "Id";
        tableColumnNames[1] = "Name";
        tableColumnNames[2] = "Nazwisko";

        model.setColumnIdentifiers(tableColumnNames);
        if (users == null) {
            tableUsers.setModel(model);
            return;
        }

        for (User user : users) {
            model.addRow(getColumnsForUserForManager(user));
        }

        tableUsers.setModel(model);
    }

    private static Object[] getColumnsForClient(Client client) {
        return new Object[]{
                client.getIdClient(),
                client.getName(),
                client.getSurname(),
                client.getPesel(),
                client.getMail(),
                client.getUser().getName() + " " + client.getUser().getSurname()
        };
    }

    private static Object[] getColumnsForUser(User user) {
        return new Object[]{
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getLogin(),
                user.getCreated()
        };
    }

    private static Object[] getColumnsForUserForManager(User user) {
        return new Object[]{
                user.getId(),
                user.getName(),
                user.getSurname()
        };
    }

    private static DefaultTableModel getUneditableTableModel() {
        return new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
}
