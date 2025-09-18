package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import nila.Nila;

/**
 * aa
 */
public class Main extends Application {

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image nilaImage = new Image(this.getClass().getResourceAsStream("/images/Nila.png"));
    private Image backgroundImage = new Image(this.getClass().getResourceAsStream("/images/background.png"));
    private Nila nila = new Nila("./data/nila.txt");

    @Override
    public void start(Stage stage) {
        initializeComponents();
        setupScene(stage);
        configureStage(stage);
        setupLayout();
        setupEventHandlers();
        setupScrollBehaviour();
        setupBackground();
    }

    private void initializeComponents() { //Setting up required components
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        userInput = new TextField();
        sendButton = new Button("Send");

        scrollPane.setContent(dialogContainer);
        // Display Nila's greeting immediately from Nila class
        dialogContainer.getChildren().add(
                DialogBox.getNilaDialog(nila.getGreeting(), nilaImage)
        );
    }

    private void setupScene(Stage stage) {
        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);
        scene = new Scene(mainLayout);
        stage.setScene(scene);
        stage.show();
    }

    private void configureStage(Stage stage) { //Formatting the window to look as expected
        stage.setTitle("Nila");
        stage.setResizable(true);
        stage.setMinHeight(700.0);
        stage.setMinWidth(500.0);
        stage.setHeight(700.0);
        stage.setWidth(500.0);
    }

    private void setupLayout() {
        AnchorPane mainLayout = (AnchorPane) scene.getRoot();
        mainLayout.setPrefSize(500.0, 700.0);

        scrollPane.setPrefSize(485, 635);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
        userInput.setPrefWidth(325.0);
        sendButton.setPrefWidth(55.0);
        setAnchorConstraints();
    }

    private void setAnchorConstraints() {
        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);
        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);
    }

    private void setupEventHandlers() { //Handling user input
        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });
        userInput.setOnAction((event) -> {
            handleUserInput();
        });
    }

    private void setupScrollBehaviour() {
        //Scroll down to the end every time dialogContainer's height changes.
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
    }

    private void setupBackground() {
        AnchorPane mainLayout = (AnchorPane) scene.getRoot();
        try {
            BackgroundImage bgImage = new BackgroundImage(backgroundImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true,
                            false, true));
            mainLayout.setBackground(new Background(bgImage));
        } catch (Exception e) {
            mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #bbdefb);");
        }
    }

    /**
     * Creates a dialog box containing user input, and appends it to
     * the dialog container. Clears the user input after processing.
     */
    private void handleUserInput() {
        String userText = userInput.getText();
        String nilaText = nila.getResponse(userInput.getText());
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getNilaDialog(nilaText, nilaImage)
        );
        userInput.clear();


        // If user typed "bye", close after showing goodbye message
        if (userText.trim().equalsIgnoreCase("bye")) {
            // Use PauseTransition for delayed closing (non-blocking)
            javafx.animation.PauseTransition delay = new
                    javafx.animation.PauseTransition(javafx.util.Duration.seconds(0.6));
            delay.setOnFinished(event -> {
                javafx.application.Platform.exit();
            });
            delay.play();
        }
    }
}
