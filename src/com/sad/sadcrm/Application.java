package com.sad.sadcrm;

import com.sad.sadcrm.form.SadCRMForm;

import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.*;

import static java.awt.EventQueue.invokeLater;
import static java.lang.Thread.setDefaultUncaughtExceptionHandler;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import static javax.swing.UIManager.getInstalledLookAndFeels;
import static javax.swing.UIManager.setLookAndFeel;

public class Application {
    public static final String VERSION = "0.8.1";

    public static void main(String args[]) {
        setLookAndFeelIfExists("Windows");
        setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler());
        invokeLater(() -> new SadCRMForm().setVisible(true));
    }

    private static void setLookAndFeelIfExists(String lookAndFeelName) {
        try {
            for (LookAndFeelInfo info : getInstalledLookAndFeels()) {
                if (lookAndFeelName.equals(info.getName())) {
                    setLookAndFeel(info.getClassName());
                }
            }
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException ex) {
            getLogger(SadCRMForm.class.getName()).log(SEVERE, null, ex);
        }
    }
}
