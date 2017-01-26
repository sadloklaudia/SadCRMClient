/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sad.sadcrm;

import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author dstachyra
 */
public class ValidationUtil {
    
    public static void notNullValidation(){
        
    }
    
    public static String validateNotNull(Map<String,String> fields){
        for(String s : fields.keySet()){
            if(s.equalsIgnoreCase("")){
                return fields.get(s);
                
            }
        }
        
        return null;
    }
    
    public static boolean validatePassword(String password){
        if(password.length() < 4 || password.length() > 10){
            return false;
        }
        
        return true;
    }
    
    public static boolean validateNotEqual(String val1, String val2){
        if(val1.equalsIgnoreCase(val2)){
            return true;
        }
        
        return false;
    }
    
    public static boolean validatePostalCode(String postalCode) {
        // TODO - napisać walidację kodu pocztowego
        if(postalCode.length()!=6){
            return false;
        }
        return true;
    }
    
    /**
     * Walidacja nr pesel
     *
     * @param pesel
     * @return
     */
    public static boolean validatePesel(String pesel) {
        // zakładamy tablicę z wagami
        int[] wagi = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};

        // sprawdzamy długość PESEL'a, jeśli nie jest to 11 zwracamy false
        if (pesel.length() != 11) {
            return false;
        }

        // zakładamy zmienną będącą sumą kontrolną
        int suma = 0;

        // liczymy w pętli sumę kontrolną przemnażając odpowiednie
        // cyfry z PESEL'a przez odpowiednie wagi
        for (int i = 0; i < 10; i++) {
            suma += Integer.parseInt(pesel.substring(i, i + 1)) * wagi[i];
        }

        // pobieramy do zmiennej cyfraKontrolna wartość ostatniej cyfry z PESEL'a   
        int cyfraKontrolna = Integer.parseInt(pesel.substring(10, 11));

        // obliczamy cyfrę kontrolną z sumy (najpierw modulo 10 potem odejmujemy 10 i jeszcze raz modulo 10)
        suma %= 10;
        suma = 10 - suma;
        suma %= 10;

        // zwracamy wartość logiczną porównania obliczonej i pobranej cyfry kontrolnej
        return (suma == cyfraKontrolna);
    }
}
