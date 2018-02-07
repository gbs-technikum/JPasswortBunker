package jpasswortbunker.mgm.model;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class EncryptionService {


    private SecretKeySpec secretKeySpec;
    private byte[] keyByteArray;
    private byte[] geheimeDatenByteArray;
    private Cipher cipher;
    private String password;

    public EncryptionService() throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
        this.password = PasswordObject.getInstance().getSaltPassword();
        this.keyByteArray = password.getBytes("UTF-8");
        this.keyByteArray = Arrays.copyOf(keyByteArray, 32);
        this.secretKeySpec = new SecretKeySpec(keyByteArray, "AES");
        this.cipher = Cipher.getInstance("AES");
    }




    public byte[] encrypt(String geheimeDaten) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        this.geheimeDatenByteArray = geheimeDaten.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, this.secretKeySpec);
        byte[] verschluesselteDatenByteArray = cipher.doFinal(geheimeDatenByteArray);
        return verschluesselteDatenByteArray;
    }


    public String decrypt(byte[] verschluesselteDatenByteArray) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] entschluesselteDatenByteArray = cipher.doFinal(verschluesselteDatenByteArray);
        String geheimeDatenNachEntschluesselung = new String(entschluesselteDatenByteArray, "UTF-8");
        return geheimeDatenNachEntschluesselung;
    }



}
