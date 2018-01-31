package model;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {

        PasswordObject.getInstance().setPassword("supergeheim");
        System.out.println(PasswordObject.getInstance().getPassword());


        EncryptionService encryptionService = new EncryptionService();

        System.out.println(encryptionService.getPassword());


        System.out.println("____________Daten verschlüsselt:____________________");
        byte[] verschlüsselt = encryptionService.encrypt("wagenhuber");
        System.out.println(verschlüsselt);


        System.out.println("____________Daten entschlüsselt:____________________");
        String entschlüselt = encryptionService.decrypt(verschlüsselt);
        System.out.println(entschlüselt);

        System.out.println("__________________Test Hashfunktion____________________");
        HashService hashService = new HashService();
        System.out.println(hashService.getAlgorithm());
        System.out.println(hashService.setValueToHash("A"));


    }
}
