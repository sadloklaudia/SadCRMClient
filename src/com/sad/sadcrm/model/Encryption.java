package com.sad.sadcrm.model;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

public class Encryption {
    public static String encrypt(String input, String key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(ENCRYPT_MODE, secretKey);
            byte[] crypted = cipher.doFinal(input.getBytes());
            return new String(Base64.getEncoder().encode(crypted));
        } catch (Exception exception) {
            throw new RuntimeException("Encyrption failed", exception);
        }
    }

    public static String decrypt(String input, String key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(DECRYPT_MODE, secretKey);
            byte[] output = cipher.doFinal(Base64.getDecoder().decode(input));
            return new String(output);
        } catch (Exception exception) {
            throw new RuntimeException("Decryption failed", exception);
        }
    }

    public static void main(String[] args) {
        String key = "1234567891234567";
        String data = "example";
        System.out.println(Encryption.decrypt(Encryption.encrypt(data, key), key));
        System.out.println(Encryption.encrypt(data, key));
    }
}