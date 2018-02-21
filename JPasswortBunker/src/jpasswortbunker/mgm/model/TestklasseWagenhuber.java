package jpasswortbunker.mgm.model;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public final class TestklasseWagenhuber {
    public static void main(String[] args) throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {

        PasswordObject.getInstance().setPassword("MasterKey_");


        EncryptionService encryptionService = new EncryptionService();
        System.out.println("______Salt-Passwort-Hash für Verschlüsselung__________");
        System.out.println(PasswordObject.getInstance().getSaltPasswordHashForEncryption());


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


        byte[] a = hashServiceMD5.getHashValue(PasswordObject.getInstance().getSaltPasswordHashForEncryption()).getBytes();
        System.out.println(a.length);
        byte[] b = hashServiceSHA256.getHashValue(PasswordObject.getInstance().getSaltPasswordHashForEncryption()).getBytes();
        System.out.println(b.length);


        System.out.println("____________Passwort-Hash 512 bit for Store______________");
        System.out.println(PasswordObject.getInstance().getSaltPasswordHashForPasswortStore());

        System.out.println("____________Ckeck-Passwort_____________________________");
        System.out.println(PasswordObject.getInstance().getSaltPasswordHashForPasswortStore());
        System.out.println(PasswordObject.getInstance().createSaltPasswordHashForPasswortStore(PasswordObject.getInstance().createSaltyPassword("MasterKey_")));
        System.out.println(PasswordObject.getInstance().checkPassword("MasterKey_"));

    }
}
