package model;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashService {

    private MessageDigest messageDigest;
    private byte[] valueToHashByteArray;


    /** Konstruktor erzeugt ein Object der Klasse MessageDigest und initialsiert diesen für die Verwendung von HASH-Algorithmus SHA-512.
     *
     * @throws NoSuchAlgorithmException
     */
    public HashService() throws NoSuchAlgorithmException {
        this.messageDigest = MessageDigest.getInstance("SHA-512");
    }


    /** Erzeugt Hashwert mittels des Algorithmus SHA-512
     *
     * @param stringToHash
     * @return String hashValue
     * @throws UnsupportedEncodingException
     */
    public String setValueToHash(String stringToHash) throws UnsupportedEncodingException {
        messageDigest.update(stringToHash.getBytes("UTF-8"));
        valueToHashByteArray = messageDigest.digest();
        String hashValue = DatatypeConverter.printHexBinary(valueToHashByteArray);
        return hashValue;
    }


    /**Gibt den Namen des verwendeten Hash-Algorithmus aus.
     *
     * @return
     */
    public String getAlgorithm() {
        return messageDigest.getAlgorithm();
    }




}
