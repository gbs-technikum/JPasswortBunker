package jpasswortbunker.mgm.model;

import jpasswortbunker.mgm.sample.Model;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestklasseModelMain {

    public static void main(String[] args) throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, SQLException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {

        ModelMain modelMain = new ModelMain();

        modelMain.FillEntryListFromDb();


        //modelMain.newEntry("Microsoft","gates@microsoft.com","redmond",null,"www.microsoft.com",7);

        /*System.out.println("Liste wird neu geladen:");
        modelMain.FillEntryListFromDb();*/





        ArrayList<Entry> arrayList = modelMain.getEntryList();
        for (Entry entry : arrayList) {
            System.out.println(entry);
        }

    }




}
