package model;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public final class Main {
    public static void main(String[] args) throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {

        PasswordObject.getInstance().setPassword("MasterKey_");


        EncryptionService encryptionService = new EncryptionService();
        System.out.println("______Passwort für Verschlüsselung__________");
        System.out.println(PasswordObject.getInstance().getSaltPassword());


        System.out.println("____________Daten verschlüsselt:____________________");
        byte[] verschlüsselt = encryptionService.encrypt("wagenhuber");
        System.out.println(verschlüsselt);


        System.out.println("____________Daten entschlüsselt:____________________");
        String entschlüselt = encryptionService.decrypt(verschlüsselt);
        System.out.println(entschlüselt);

        System.out.println("__________________Test Hashfunktion____________________");
        HashService hashServiceMD5 = new HashService("MD5");
        HashService hashServiceSHA256 = new HashService("SHA-256");

        System.out.println(hashServiceMD5.getAlgorithm());
        System.out.println(hashServiceMD5.getHashValue("jPasswortBunker"));
        System.out.println(hashServiceSHA256.getAlgorithm());
        System.out.println(hashServiceSHA256.getHashValue("wagenhuber"));

        System.out.println("____________Test Passwort Hash______________");


        byte[] a = hashServiceMD5.getHashValue(PasswordObject.getInstance().getSaltPassword()).getBytes();
        System.out.println(a.length);
        byte[] b = hashServiceSHA256.getHashValue(PasswordObject.getInstance().getSaltPassword()).getBytes();
        System.out.println(b.length);
    }
}
