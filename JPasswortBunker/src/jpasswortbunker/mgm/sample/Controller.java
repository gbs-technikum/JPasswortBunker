package jpasswortbunker.mgm.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {


    //GUI-Elemente
    @FXML
    private Button button;

    @FXML
    private TextField textField;

    @FXML
    private Label label;


    //Controller erstellt Presenter und übergibt eigene Referenz
    private Presenter presenter = new Presenter(this);
    //private Controller2 controller2 = new Controller2(presenter);


    //Methoden für Klick auf Button
    public void buttonGeklickt(ActionEvent actionEvent) throws IOException {
        presenter.setStringProperty(textField.getText());
    }


    public void buttonGeklicktFenster2buttonGeklicktFenster2(ActionEvent actionEvent) throws IOException {
        //Zweites Fenster mit Sample2.fxml erstellen:
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample2.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            //Übergabe Referenz Presenter an zweites Fenster
            Controller2 controller2 = fxmlLoader.<Controller2>getController();
            controller2.setPresenter(presenter);

            Stage stage = new Stage();
            stage.setTitle("Zweites Fenster");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




    public Presenter getPresenter() {
        return presenter;
    }


    //Aktualisierung der Gui: Aufruf der Methode durch Presenter
    public void updateGui() {
        label.setText(presenter.getStringProperty());
        textField.clear();
    }
}
