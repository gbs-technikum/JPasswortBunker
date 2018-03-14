package jpasswortbunker.mgm.sample;

import javafx.event.ActionEvent;

import java.io.IOException;

public class Controller2 {

    private Presenter presenter;


    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;

    }

    //Methoden für Klick auf Button
    public void buttonGeklickt(ActionEvent actionEvent) throws IOException {
        presenter.setStringProperty("Button Fenster 2 wurde gedrückt!");

    }

}
