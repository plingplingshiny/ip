package gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * A custom dialog box component for displaying chat messages in the Nila chatbot GUI.
 * Each dialog box contains a message text and a circular profile picture.
 * The component supports flipping the layout for user vs chatbot messages.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        setupCircularImage(img);
    }

    private void setupCircularImage(Image img) {
        displayPicture.setImage(img);
        displayPicture.setFitHeight(40);
        displayPicture.setFitWidth(40);
        displayPicture.setPreserveRatio(true);
        Circle clip = new Circle(20, 20, 20);
        displayPicture.setClip(clip);
        displayPicture.setEffect(new DropShadow(5, Color.gray(0.5)));
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_RIGHT);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        db.setAlignment(Pos.CENTER_RIGHT);
        db.dialog.setStyle("-fx-background-color: #dcf8c6; "
                + "-fx-background-radius: 15; "
                + "-fx-padding: 10; "
                + "-fx-border-radius: 15; "
                + "-fx-border-color: #b3e89c; "
                + "-fx-border-width: 1; "
                + "-fx-text-fill: #000000;");
        return db;
    }

    public static DialogBox getNilaDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.setAlignment(Pos.CENTER_LEFT);
        db.dialog.setStyle("-fx-background-color: rgba(255, 213, 128, 0.90); "
                + "-fx-background-radius: 15; "
                + "-fx-padding: 10; "
                + "-fx-border-radius: 15; "
                + "-fx-border-color: #e0e0e0; "
                + "-fx-border-width: 1; "
                + "-fx-text-fill: #000000;");
        return db;
    }
}

