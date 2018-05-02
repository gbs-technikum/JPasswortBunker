package jpasswortbunker.mgm.model;

import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class Test {


    public void test3() {

        File file = null;
        URL url = this.getClass().getResource("/buildView.txt");
        if (url != null) {
            System.out.println("url = nicht null");
            try {
                file = new File(url.toURI());
                System.out.println("datei da");
            } catch (URISyntaxException e) {
                e.printStackTrace();
                System.out.println("Datei nicht da");
            }
        } else {
            System.out.println("url = null");
        }

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while (null != (line = bufferedReader.readLine())) {
                //Zeile auf der Konsole ausgeben
                System.out.println(line);
                //Hier kann Ihr Code stehen ...
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws URISyntaxException, NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException {

//        Test buildView = new Test();
//        buildView.test3();

        /*ModelMain modelMain = new ModelMain();
        modelMain.addEntryToList("Netflix", "Underwoo", "123", "12d3");*/

    }
}
