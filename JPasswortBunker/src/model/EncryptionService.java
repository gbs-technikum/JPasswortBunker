package model;

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


    private String geheimeDaten;
    private SecretKeySpec secretKeySpec;
    private byte[] keyByteArray;
    private byte[] geheimeDatenByteArray;
    private Cipher cipher;
    private String password;

    public EncryptionService() throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
        this.password = PasswordObject.getInstance().getPassword();
        this.keyByteArray = password.getBytes("UTF-8");
        this.keyByteArray = Arrays.copyOf(keyByteArray, 32);
        this.secretKeySpec = new SecretKeySpec(keyByteArray, "AES");
        this.cipher = Cipher.getInstance("AES");
    }



    public String getPassword() {
        return password;
    }

    public byte[] encrypt(String geheimeDaten) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        this.geheimeDatenByteArray = geheimeDaten.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, this.secretKeySpec);
        byte[] verschluesselteDatenByteArray = cipher.doFinal(geheimeDatenByteArray);
        /*String geheimeDatenNachVerschluesselung = new String(verschluesselteDatenByteArray, "UTF-8");
        return geheimeDatenNachVerschluesselung;*/
        return verschluesselteDatenByteArray;
    }


    public String decrypt(byte[] verschluesselteDatenByteArray) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        //byte[] verschluesselteDatenByteArray = verschluesselteGeheimeDaten.getBytes();
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] entschluesselteDatenByteArray = cipher.doFinal(verschluesselteDatenByteArray);
        String geheimeDatenNachEntschluesselung = new String(entschluesselteDatenByteArray, "UTF-8");
        return geheimeDatenNachEntschluesselung;
    }







}
