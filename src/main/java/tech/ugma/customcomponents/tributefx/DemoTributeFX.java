package tech.ugma.customcomponents.tributefx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class DemoTributeFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        WebView textArea = new WebView();

        TributeFX.turnPromptTextOn();
        TributeFX.configureWebView(textArea);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(20));
        Label comment = new Label("Comment");
        Button submitButton = new Button("Submit");
        TextArea realTextArea = new TextArea();
        vBox.getChildren().addAll(comment, textArea, submitButton, realTextArea);
        BorderPane root = new BorderPane(vBox);
        Scene theScene = new Scene(root);


        primaryStage.setTitle("TributeFX Demo");
        primaryStage.setScene(theScene);
        primaryStage.show();
    }
}
