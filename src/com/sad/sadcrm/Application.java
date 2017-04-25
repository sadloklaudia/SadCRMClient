package com.sad.sadcrm;

import com.sad.sadcrm.form.SadCRMForm;

import static com.sad.sadcrm.LookAndFeelUtil.setLookAndFeelIfExists;
import static java.awt.EventQueue.invokeLater;
import static java.lang.Thread.setDefaultUncaughtExceptionHandler;

public class Application {
    public static final String VERSION = "0.8.1";

    public static void main(String args[]) {
        setLookAndFeelIfExists("Windows");
        setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler());
        invokeLater(() -> new SadCRMForm().setVisible(true));
    }
}
