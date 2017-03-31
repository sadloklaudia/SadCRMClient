package com.sad.sadcrm.form;

import javax.swing.*;

import static java.lang.String.format;
import static javax.swing.JOptionPane.showMessageDialog;

public class ApplicationWindow extends JFrame {
    private final static String DEFAULT_TITLE = "SadCRM";

    public ApplicationWindow(String version) {
        setSize(1000, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle(format("Klient SadCRM v%s", version));
    }

    public void messageBox(String message) {
        showMessageDialog(this, message);
    }

    public void messageBox(String title, String message) {
        showMessageDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    public void errorMessageBox(String message) {
        showMessageDialog(this, message, DEFAULT_TITLE, JOptionPane.ERROR_MESSAGE);
    }
}
