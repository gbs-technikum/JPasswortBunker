package model;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Testklasse {

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {

        StringBuilder stringBuilder = new StringBuilder();
        String passwort = "geheim";
        int opmode = 1;

        //Erzeugt ein KeyObject
        Key key = new SecretKeySpec("geheim".getBytes(), "DES");




        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //Zeichen zu MD-Object hinzufügen
        md.update("abc".getBytes());

        //Inhalt MD-Object verschlüssseln und Byte[] Array zuweisen
        byte[] digest = md.digest();

        //Ausgabe des Arrays
        for (byte b : digest) {
            stringBuilder.append(b);
            //System.out.printf("%02x", b);
            System.out.println(b);
        }
        System.out.println(stringBuilder);


        /**
         * Verschlüsselung mit Cipher
         */

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedDigest = cipher.doFinal(digest);
        System.out.println(cipher.getParameters());


        /**
         * Entsclüsselung
         */
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedDigest = cipher.doFinal(digest);


    }//ende main

}
