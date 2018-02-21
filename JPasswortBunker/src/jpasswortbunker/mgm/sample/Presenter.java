package jpasswortbunker.mgm.sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Presenter {

    private Controller controller;
    private Model logic;
    private StringProperty stringProperty;


    public Presenter(Controller controller) {
        this.controller = controller;
        logic = new Model(this);
        initStringProperty();
    }


    public void initStringProperty() {

        this.stringProperty = new SimpleStringProperty();

        this.stringProperty.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                logic.setText(stringProperty.getValue());
                logic.updateGui();


                System.out.println("_____________Ausgabe Variable von Logic_______________________");
                System.out.println(logic.getText());
            }
        });

    }


    public void setStringProperty(String string) throws IOException {
        this.stringProperty.setValue(string);

    }

    public String getStringProperty() {
        return stringProperty.getValue();
    }

    public StringProperty stringPropertyProperty() {
        return stringProperty;
    }

    public void updateGui() {
        controller.updateGui();
    }

}
