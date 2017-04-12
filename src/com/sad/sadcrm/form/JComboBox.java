package com.sad.sadcrm.form;

public class JComboBox<E> extends javax.swing.JComboBox<E> {
    @Override
    public E getSelectedItem() {
        return getItemAt(super.getSelectedIndex());
    }
}
