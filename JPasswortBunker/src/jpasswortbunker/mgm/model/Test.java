package jpasswortbunker.mgm.model;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class Test {


    public void testMethode() {
        System.out.println("start");
        String pfad = "resources/";
        URL url = this.getClass().getClassLoader().getResource(pfad + "test.txt");
        if (url == null) {
            System.out.println("Url ist null");
        }
    }


//    public void test2() {
//        System.out.println("Start");
//        try {
//            InputStream fileStream = getClass().getResourceAsStream("/text.txt");
//            //InputStream fileStream = new FileInputStream(new File(
//             //       "test.txt"));
//            if (fileStream == null) {
//                System.out.println("funktionierrt nicht");
//            } else {
//                System.out.println(fileStream);
//            }
//            Properties props = new Properties();
//            props.load(fileStream);
//            String myPropValue = (String) props.get("test.txt");
//            System.out.println(myPropValue);
//        } catch (FileNotFoundException e) {
//            System.out.println(e);
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//    }


    public void test3() throws URISyntaxException {
        URL url = this.getClass().getResource("/test.txt");
        File file = new File(url.toURI());
        if (file.exists()) {
            System.out.println("datei da");
        } else {
            System.out.println("Datei nicht da");
        }
    }

    public static void main(String[] args) throws URISyntaxException {

        Test test = new Test();
        test.test3();
        //test.testMethode();
        //test.test2();



//        try {
//            File file = new File( Test.class.getResource("text.txt").toURI());
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
    }
}
