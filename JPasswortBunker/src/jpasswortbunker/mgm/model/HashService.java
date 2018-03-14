package jpasswortbunker.mgm.model;


import org.apache.commons.codec.binary.Hex;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class HashService {

    private MessageDigest messageDigest;
    private byte[] valueToHashByteArray;


    /** Konstruktor erzeugt ein Object der Klasse MessageDigest und initialsiert diesen für die Verwendung eines HASH-Algorithmus welcher übergeben wird
     *
     * @throws NoSuchAlgorithmException
     */
    public HashService(String hashTyp) throws NoSuchAlgorithmException {
        this.messageDigest = MessageDigest.getInstance(hashTyp);
    }


    /** Erzeugt Hashwert und gibt diesen als String in HEX-Code aus.
     * Codierung Hex-String via External Libary Apache Common Codec
     * @param stringToHash
     * @return String hashValue
     * @throws UnsupportedEncodingException
     */
    public String getHashValue(String stringToHash) throws UnsupportedEncodingException {
        messageDigest.update(stringToHash.getBytes("UTF-8"));
        valueToHashByteArray = messageDigest.digest();
        String hexCodeHashValue = Hex.encodeHexString(valueToHashByteArray);
        return hexCodeHashValue;
    }


    /**Gibt den Namen des verwendeten Hash-Algorithmus aus.
     *
     * @return
     */
    public String getAlgorithm() {
        return messageDigest.getAlgorithm();
    }




}
