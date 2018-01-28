package tech.ugma.customcomponents.tributefx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DemoTributeFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        TributeFX textArea = new TributeFX();
        BorderPane root = new BorderPane(textArea);
        Scene theScene = new Scene(root);


        primaryStage.setTitle("TributeFX Demo");
        primaryStage.setScene(theScene);
        primaryStage.show();
    }
}
