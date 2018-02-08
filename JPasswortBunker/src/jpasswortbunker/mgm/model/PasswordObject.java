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

    //Salt entpricht MD5-Hash des Wortes "jPasswortBunker"
    private static final String SALT = "90ada22b4f3eb13290de049b7a87ffe3";
    private HashService hashServiceForEncryption = new HashService("MD5");
    private HashService hashServiceForPasswordStore = new HashService("SHA-512");
    private String saltPasswordHashForEncryption;
    private String saltPasswordHashForPasswortStore;


    private PasswordObject() throws NoSuchAlgorithmException {

    }

    public static PasswordObject getInstance() {
        return instance;
    }


    protected String createSaltyPassword(String password) {
        String saltyPassword = password + this.SALT;
        return saltyPassword;
    }

    public void setPassword(String password) throws UnsupportedEncodingException {
        String saltyPassword = createSaltyPassword(password);
        this.saltPasswordHashForEncryption = createSaltPasswordHashForEncryption(saltyPassword);
        this.saltPasswordHashForPasswortStore = createSaltPasswordHashForPasswortStore(saltyPassword);
    }


    protected String createSaltPasswordHashForEncryption(String saltyPassword) throws UnsupportedEncodingException {
        String hash = hashServiceForEncryption.getHashValue(saltyPassword);
        return hash;
    }

    protected String createSaltPasswordHashForPasswortStore(String saltyPassword) throws UnsupportedEncodingException {
        String hash = hashServiceForPasswordStore.getHashValue(saltyPassword);
        return hash;
    }


    protected String getSaltPasswordHashForEncryption() {
        return saltPasswordHashForEncryption;
    }


    protected String getSaltPasswordHashForPasswortStore() {
        return saltPasswordHashForPasswortStore;
    }

    public Boolean checkPassword(String password) throws UnsupportedEncodingException {
        if (createSaltPasswordHashForPasswortStore(createSaltyPassword(password)).equals(this.saltPasswordHashForPasswortStore)) {
            return true;
        }
        return false;
    }
}
