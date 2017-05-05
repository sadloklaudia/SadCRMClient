package com.sad.sadcrm;

import static javax.swing.JOptionPane.showMessageDialog;

public class DefaultExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        try {
            throw throwable;
        } catch (ServerConnectException exception) {
            showMessageDialog(null, "Nie można się połączyć z serwerem. Sprawdź swoje połączenie internetowe.");
        } catch (Throwable t) {
            throwable.printStackTrace();
            showMessageDialog(null, throwable.getClass().getSimpleName() + ": " + throwable.getMessage());
        }
    }
}
