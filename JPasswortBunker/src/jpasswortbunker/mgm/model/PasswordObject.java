package jpasswortbunker.mgm.model;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class PasswordObject {


    private static PasswordObject instance;

    static {
        try {
            instance = new PasswordObject();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static final String SALT = "90ada22b4f3eb13290de049b7a87ffe3";
    private HashService hashServiceForEncryption = new HashService("MD5");
    private HashService hashServiceForPasswordStore = new HashService("SHA-512");
    private String saltPasswordHashForEncryption;
    private String saltPasswordHashForPasswortStore;
    private Boolean ckeckPassword;


    private PasswordObject() throws NoSuchAlgorithmException {

    }

    public static PasswordObject getInstance(){
        return instance;
    }


    private String createSaltyPassword(String password){
        String saltyPassword = password + this.SALT;
        return saltyPassword;
    }

    public void setPassword(String password) throws UnsupportedEncodingException {
        String saltyPassword = createSaltyPassword(password);
        this.saltPasswordHashForEncryption = createSaltPasswordHashForEncryption(saltyPassword);
        this.saltPasswordHashForPasswortStore = createSaltPasswordHashForPasswortStore(saltyPassword);
    }


    public String createSaltPasswordHashForEncryption(String saltyPassword) throws UnsupportedEncodingException {
         String a = hashServiceForEncryption.getHashValue(saltyPassword);

    }

    public void createSaltPasswordHashForPasswortStore(String saltyPassword) throws UnsupportedEncodingException {
        hashServiceForPasswordStore.getHashValue(saltyPassword);
    }


    public String getSaltPasswordHashForEncryption() {
        return saltPasswordHashForEncryption;
    }

    public String getSaltPasswordHashForPasswortStore() {
        return saltPasswordHashForPasswortStore;
    }

    public Boolean checkPassword(String password){
        String check = createSaltyPassword(password);
    }
}
