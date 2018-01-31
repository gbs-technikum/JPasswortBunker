package model;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashService {

    private MessageDigest messageDigest;
    private byte[] valueToHashByteArray;


    /** Konstruktor erzeugt ein Object der Klasse MessageDigest und initialsiert diesen für die Verwendung von HASH-Algorithmus SHA-265.
     *
     * @throws NoSuchAlgorithmException
     */
    public HashService() throws NoSuchAlgorithmException {
        this.messageDigest = MessageDigest.getInstance("SHA-256");
    }


    /** Erzeugt Hashwert und gibt diesen als String in HEX-Code aus.
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
