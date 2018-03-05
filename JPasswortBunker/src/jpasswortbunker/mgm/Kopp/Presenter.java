

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.Iterator;

public final class Presenter {

    private MainInterfaceController controller;
    private SetMasterPasswordController controllerSetMasterPassword;
    private Model model;
    private StringProperty stringProperty;
    public ObservableList<Entry> entrys = FXCollections.observableArrayList();


    public Presenter(MainInterfaceController controller) {
        this.controller = controller;
        model = new Model(this);
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

    //######################################################################################################


    public Presenter getPresenter() {
        return this;
    }

    //Schreibt die Liste der Arraylist aus Model in die Observable List im Presenter
    public void writeToObservableList() {
        Iterator<Entry> iterator = model.getEntryList().iterator();
        while (iterator.hasNext()) {
            entrys.add((Entry)iterator.next());
        }
    }


    public boolean checkSetMasterpassword() {
        if (model.checkSetMasterpassword()) {

            return true;
        } else {

            return false;
        }
    }

    //Bekommt die zwei Passwörter vom SetMasterPassword Fenster
    //Gibt diese an das Model weiter
    public boolean setMasterpassword(String password1, String password2) {
        //return model.setMasterpassword(password1, password2);
        //controllerSetMasterPassword = new SetMasterPasswordController();
//
//        model.test();
        controllerSetMasterPassword.test();

//        controllerSetMasterPassword = new SetMasterPasswordController();
//        controllerSetMasterPassword.test();
        System.out.println("####" + controllerSetMasterPassword.getPassword_box());

    return true;
    }

    public void test() {
        System.out.println("testMethode Presenter");
    }

}
