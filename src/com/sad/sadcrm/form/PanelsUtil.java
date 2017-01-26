/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sad.sadcrm.form;

import javax.swing.JPanel;

/**
 *
 * @author dstachyra
 */
public class PanelsUtil {
     
    /*
    Włączenie panelu użytkownika
    */
    public static void enablePanel(JPanel enabledPanel, JPanel[] disabledPanels){        
        enabledPanel.setVisible(true);     
        for(int i=0;i<disabledPanels.length;i++){
            disabledPanels[i].setVisible(false);            
        }                
    }
}
