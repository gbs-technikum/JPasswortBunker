package model;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class Testklasse {

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, UnsupportedEncodingException {

        SecretKeySpec secretKeySpec;
        byte[] key;

        StringBuilder stringBuilder = new StringBuilder();
        String passwort = "geheim";
        int opmode = 1;

        //Erzeugt ein KeyObject
        key = passwort.getBytes("UTF-8");
        key = Arrays.copyOf(key, 16);
        secretKeySpec = new SecretKeySpec(key, "AES");




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

        System.out.println("________________Verschlüsselung_____________");


        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedDigest = cipher.doFinal(digest);
        System.out.println(cipher.getParameters());

        String verschlüsselt = Base64.getEncoder().encodeToString(encryptedDigest);


        System.out.println("Verschlüsselte Daten im ByteArray:");
        System.out.println(encryptedDigest);
        System.out.println("Verschlüsselte Daten als String:");
        System.out.println(verschlüsselt);

        /**
         * Entsclüsselung
         */
        System.out.println("________________Entschlüsselung_____________");

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        //byte[] decryptedDigest = cipher.doFinal(digest);
        byte[] entschlüsseltBytes = cipher.doFinal(encryptedDigest);
        String entschlüsselt = Base64.getEncoder().encodeToString(entschlüsseltBytes);

        System.out.println(entschlüsselt);
    }//ende main

}
