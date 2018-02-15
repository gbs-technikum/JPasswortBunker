package jpasswortbunker.mgm.Kopp;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.Iterator;

public class Presenter {

    private MainInterfaceController controller;
    private SetMasterPasswordController controllerSetMasterPassword;
    private Model model;
    private StringProperty stringProperty;
    public ObservableList<Entry> entrys = FXCollections.observableArrayList();


    public Presenter(MainInterfaceController controller) {
        this.controller = controller;
        model = new Model(this);
        controllerSetMasterPassword.setPresenter(this);
        initStringProperty();
    }


    public void initStringProperty() {

        this.stringProperty = new SimpleStringProperty();
        model.setText(stringProperty.getValue());
        //model.updateGui();
//        this.stringProperty.addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//
//
//
//
//                System.out.println("_____________Ausgabe Variable von Logic_______________________");
//                System.out.println(logic.getText());
//            }
//        });

    }

    //######################################################################################################



    public void setStringProperty(String string) throws IOException {
        this.stringProperty.setValue(string);

    }

    public String getStringProperty() {
        return stringProperty.getValue();
    }

    public StringProperty stringPropertyProperty() {
        return stringProperty;
    }

//    public void updateGui() {
//        controller.updateGui();
//    }

    //Schreibt die Liste der Arraylist aus Model in die Observable List im Presenter
    public void writeToObservableList() {
        Iterator<Entry> iterator = model.getEntryList().iterator();
        while (iterator.hasNext()) {
            entrys.add(iterator.next());
        }
    }


    public boolean checkSetMasterpassword() {
        return model.checkSetMasterpassword();
    }

    //Bekommt die zwei Passwörter vom SetMasterPassword Fenster
    //Gibt diese an das Model weiter
    public boolean setMasterpassword() {
        return model.setMasterpassword(controllerSetMasterPassword.getPassword_box(), controllerSetMasterPassword.getRepeatPassword_box());
    }

}
