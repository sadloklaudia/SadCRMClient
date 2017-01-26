package com.sad.sadcrm.form;

import javax.swing.*;

class PanelsUtil {
    static void enablePanel(JPanel enabledPanel, JPanel[] disabledPanels) {
        enabledPanel.setVisible(true);
        for (JPanel disabledPanel : disabledPanels) {
            disabledPanel.setVisible(false);
        }
    }
}
