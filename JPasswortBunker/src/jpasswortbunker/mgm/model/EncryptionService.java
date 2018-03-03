package jpasswortbunker.mgm.model;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptionService {


    private SecretKeySpec secretKeySpec;
    private byte[] keyByteArray;
    private byte[] geheimeDatenByteArray;
    private Cipher cipher;
    private String password;


    /**Beschreibung Methode EncryptionService()
     * Abruf des gesalzenen MD5 Passwort-Hashwertes über das Passwort-Objekt
     * MD5 Hash => 32 Hex-Zeichen => Byte-Array mit länge 32 Byte (Verwendung von 1 Byte [8 Bit] pro HexCode-Zeichen, nicht wie gewöhnlich 4 Bit)
     * Umwandeln des Passwort-Hash in Byte-Array für binäre Speicherung
     * AES-256-Algorythmus erfordert 256 Bit Schlüssel, dies entspricht 32 Byte.
     * MD5 Hash bildet die benötigte Schlüssellänge ab!
     * Erstellung eines Objektes der Klasse secretKeySpec als Container für Schlüssel
     * Erstellung eines Objektes der Klasse Chiper welches Verschlüsselungsfunktionen zur Verfügung stellt.
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     */

    public EncryptionService() throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
        this.password = PasswordObject.getInstance().getSaltPasswordHashForEncryption();
        this.keyByteArray = password.getBytes("UTF-8");
        //ByteArray händisch auf die benötigten 32 Byte anpassen:
        //this.keyByteArray = Arrays.copyOf(keyByteArray, 32);
        this.secretKeySpec = new SecretKeySpec(keyByteArray, "AES");
        this.cipher = Cipher.getInstance("AES");
    }



    public String encrypt(String geheimeDaten) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        if (geheimeDaten == null) {
            return null;
        }
        this.geheimeDatenByteArray = geheimeDaten.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, this.secretKeySpec);
        byte[] verschluesselteDatenByteArray = cipher.doFinal(geheimeDatenByteArray);
        return Base64.encodeBase64String(verschluesselteDatenByteArray);
    }


    public String decrypt(String verschluesselteDatenByteArray) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        if (verschluesselteDatenByteArray == null) {
            return null;
        }
        byte[] array = Base64.decodeBase64(verschluesselteDatenByteArray);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] entschluesselteDatenByteArray = cipher.doFinal(array);
        String geheimeDatenNachEntschluesselung = new String(entschluesselteDatenByteArray, "UTF-8");
        return geheimeDatenNachEntschluesselung;
    }


    /**ALT - kann gelöscht werden! Klassen wurden mittels Base64 auf String Ein- und Ausgabe umgestellt.
     * 3.3.2018 - Wagenhuber
     * @param geheimeDaten
     * @return
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     */

    /*public byte[] encryptByteArray(String geheimeDaten) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        this.geheimeDatenByteArray = geheimeDaten.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, this.secretKeySpec);
        byte[] verschluesselteDatenByteArray = cipher.doFinal(geheimeDatenByteArray);
        return verschluesselteDatenByteArray;
    }


    public String decryptByteArray(byte[] verschluesselteDatenByteArray) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] entschluesselteDatenByteArray = cipher.doFinal(verschluesselteDatenByteArray);
        String geheimeDatenNachEntschluesselung = new String(entschluesselteDatenByteArray, "UTF-8");
        return geheimeDatenNachEntschluesselung;
    }*/

}
