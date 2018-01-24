package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class cryptoTest {

    String geheimnis = "geheim";

  //Hashfunktion

    MessageDigest messageDigest = MessageDigest.getInstance( "SHA" );


    public cryptoTest() throws NoSuchAlgorithmException {
    }



}
