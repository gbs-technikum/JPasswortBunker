package jpasswortbunker.mgm.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import jpasswortbunker.mgm.presenter.PresenterMain;

import java.util.Optional;

public class ChangePasswordDialog {

    public Dialog<Pair<String, String>> dialog;
    public PresenterMain presenter;

    public ChangePasswordDialog(PresenterMain presenter) {
        this.presenter = presenter;
        this.dialog = new Dialog<>();
        test();

    }

    public void test() {
        dialog.setTitle("Change Masterpassword");
        dialog.setHeaderText("You can change your Masterpassword");

// Set the icon (must be included in the project).
        dialog.setGraphic(new ImageView(this.getClass().getResource("images/lock.png").toString()));

// Set the button types.
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

// Create the passwordfields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        PasswordField password2 = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Password:"), 0, 0);
        grid.add(password, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password2, 1, 1);

        // Enable/Disable ok button depending on whether a password was entered.
        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);



        // Do some validation (using the Java 8 lambda syntax).

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the password field by default.
        Platform.runLater(() -> password.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new Pair<>(password2.getText(), password.getText());
            }
            return null;
        });


        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(passwordPassword1 -> {
            System.out.println("test");
            System.out.println("Username=" + passwordPassword1.getKey() + ", Password=" + passwordPassword1.getValue());
        });
    }
}
