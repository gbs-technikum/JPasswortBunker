package model;


import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class crypto {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {


        // Das Passwort bzw der Schluesseltext
        String keyStr = "geheim";
        // byte-Array erzeugen
        byte[] key = (keyStr).getBytes("UTF-8");
        // aus dem Array einen Hash-Wert erzeugen mit MD5 oder SHA
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);
        // nur die ersten 128 bit nutzen
        key = Arrays.copyOf(key, 16);
        // der fertige Schluessel
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");


        // der zu verschl. Text
        String text = "Das ist der Text";

        // Verschluesseln
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(text.getBytes());

        // bytes zu Base64-String konvertieren (dient der Lesbarkeit)
        BASE64Encoder myEncoder = new BASE64Encoder();
        String geheim = myEncoder.encode(encrypted);

        // Ergebnis
        System.out.println(geheim);

        // BASE64 String zu Byte-Array konvertieren
        BASE64Decoder myDecoder2 = new BASE64Decoder();
        byte[] crypted2 = myDecoder2.decodeBuffer(geheim);

        // Entschluesseln
        Cipher cipher2 = Cipher.getInstance("AES");
        cipher2.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] cipherData2 = cipher2.doFinal(crypted2);
        String erg = new String(cipherData2);

        // Klartext
        System.out.println(erg);

    }

}