package jpasswortbunker.mgm.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    private static  Stage primaryStage;

    private void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }

    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        setPrimaryStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("MainInterface.fxml"));
        primaryStage.setTitle("jPasswordBunker");
        primaryStage.setScene(new Scene(root, 900, 625));
        primaryStage.setResizable(false);
        //primaryStage.show();
        primaryStage.getIcons().add(new Image(String.valueOf(this.getClass().getResource("images/logo.png"))));
    }


    public static void main(String[] args) {
        launch(args);
    }
}


