package jpasswortbunker.mgm.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class NewEntryController extends Application {

    @FXML
    private SplitMenuButton categorie_menuBox;

    @FXML
    private MenuItem menuItem1, menuItem2, menuItem3, menuItem4;

    private void setCategorie_menuBox() {


    }


//    @Override
//    public void start(Stage primaryStage) throws Exception {
//
//        primaryStage.setTitle("ImageView Experiment 1");
//
//
//
//
//        MenuItem menuItem1 = new MenuItem("Finance");
//        MenuItem menuItem2 = new MenuItem("Social");
//        MenuItem menuItem3 = new MenuItem("E-Mail");
//        MenuItem menuItem4 = new MenuItem("Network");
//
//        MenuButton menuButton = new MenuButton("Categorie",null, menuItem1, menuItem2, menuItem3, menuItem4);
//
//
//        HBox hbox = new HBox(menuButton);
//
//        Scene scene = new Scene(hbox, 200, 100);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//
//        menuItem1.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                menuButton.setText("Fincane");
//            }
//        });
//
//
//
//        menuItem2.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                menuButton.setText("Social");
//            }
//        });
//
//        menuItem3.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                menuButton.setText("E-Mail");
//            }
//        });
//
//        menuItem4.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                menuButton.setText("Network");
//            }
//        });
//
//
//
//    }

    @Override public void start(Stage stage) {
        Group group = new Group();
        Scene scene = new Scene(group);

        SplitMenuButton m = new SplitMenuButton();
        m.setText("Shutdown");
        m.getItems().addAll(new MenuItem("Logout"), new MenuItem("Sleep"), new MenuItem("Hallo"), new MenuItem("Test"));
        m.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("Shutdown");
            }
        });

        group.getChildren().add(m);

        stage.setTitle("Welcome to JavaFX!");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}




