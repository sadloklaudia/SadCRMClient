package com.sad.sadcrm;

import com.sad.sadcrm.form.SadCRMForm;

import javax.swing.*;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import static javax.swing.UIManager.getInstalledLookAndFeels;
import static javax.swing.UIManager.setLookAndFeel;

class LookAndFeelUtil {
    static void setLookAndFeelIfExists(String lookAndFeelName) {
        try {
            for (UIManager.LookAndFeelInfo info : getInstalledLookAndFeels()) {
                if (lookAndFeelName.equals(info.getName())) {
                    setLookAndFeel(info.getClassName());
                }
            }
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException ex) {
            getLogger(SadCRMForm.class.getName()).log(SEVERE, null, ex);
        }
    }
}
