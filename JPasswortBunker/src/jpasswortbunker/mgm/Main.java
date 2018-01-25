package jpasswortbunker.mgm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("gui/LoginScreen2.fxml"));
        primaryStage.setTitle("jPasswordBunker");
        primaryStage.setScene(new Scene(root, 390, 310));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
