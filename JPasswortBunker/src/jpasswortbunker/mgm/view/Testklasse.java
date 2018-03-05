package jpasswortbunker.mgm.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Testklasse extends Application {

    private static  Stage primaryStage;

    private void setPrimaryStage(Stage stage) {
        Testklasse.primaryStage = stage;
    }

    static public Stage getPrimaryStage() {
        return Testklasse.primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        setPrimaryStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("MainInterface.fxml"));
        primaryStage.setTitle("loginScreen2");
        primaryStage.setScene(new Scene(root, 900, 625));
        //primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
