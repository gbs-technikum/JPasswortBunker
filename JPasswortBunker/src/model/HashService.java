package model;


import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

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
    public String getHashValue(String stringToHash) throws UnsupportedEncodingException {
        messageDigest.update(stringToHash.getBytes("UTF-8"));
        valueToHashByteArray = messageDigest.digest();
        /*String hashValue = DatatypeConverter.printHexBinary(valueToHashByteArray);
        return hashValue;*/

        return Base64.getEncoder().encodeToString(valueToHashByteArray);
        System.out.println(String.format("%040x", new BigInteger(1, decoded)));

    }


    /**Gibt den Namen des verwendeten Hash-Algorithmus aus.
     *
     * @return
     */
    public String getAlgorithm() {
        return messageDigest.getAlgorithm();
    }




}
