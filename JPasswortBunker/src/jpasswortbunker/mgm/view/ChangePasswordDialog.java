package jpasswortbunker.mgm.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import jpasswortbunker.mgm.presenter.PresenterMain;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;

public class ChangePasswordDialog {

    public Dialog<Pair<String, String>> dialog;
    public PresenterMain presenter;
    public Label labelMessage;

    public ChangePasswordDialog(PresenterMain presenter) {
        this.presenter = presenter;
        this.dialog = new Dialog<>();
        this.labelMessage = new Label();
        this.labelMessage.setTextFill(Color.web("#ff0000"));
        //this.labelMessage.setMinWidth(100);
        test();

    }

    public void test() {
        dialog.setTitle("Change Masterpassword");
        dialog.setHeaderText("You can change your Masterpassword");

        // Aus dem Fenster die stage geben lassen
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();

        // Setzt Icon für die Stage
        stage.getIcons().add(new Image(this.getClass().getResource("images/logo.png").toString()));
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
        password.setPromptText("RepeatPassword");
        PasswordField password2 = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Password:"), 0, 0);
        grid.add(password, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password2, 1, 1);
        grid.add(labelMessage,1,2);

        // Enable/Disable ok button depending on whether a password was entered.
        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);



        // Do some validation (using the Java 8 lambda syntax).

        password2.textProperty().addListener((observable, oldValue, newValue) -> {
            //okButton.setDisable(newValue.trim().isEmpty());
            if (password.getText().equals(password2.getText())) {
                labelMessage.setText("");
                okButton.setDisable(false);
            } else {
                labelMessage.setText("Password not equals");
                okButton.setDisable(true);
            }
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
            try {
                presenter.renewMasterPassword(password.getText());
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
        });
    }
}
