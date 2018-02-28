package jpasswortbunker.mgm.model;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class Test {


    public void test3() {

        File file = null;
        URL url = this.getClass().getResource("/test.txt");
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

    public static void main(String[] args) throws URISyntaxException {

        Test test = new Test();
        test.test3();

    }
}
