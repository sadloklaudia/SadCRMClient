package com.sad.sadcrm;

import static javax.swing.JOptionPane.showMessageDialog;

public class DefaultExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable exception) {
        exception.printStackTrace();
        showMessageDialog(null, exception.getMessage());
    }
}
