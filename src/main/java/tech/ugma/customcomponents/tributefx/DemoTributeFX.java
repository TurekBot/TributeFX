package tech.ugma.customcomponents.tributefx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class DemoTributeFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        WebView textArea = new WebView();

        TributeFX.configureWebView(textArea);

        AnchorPane root = new AnchorPane(textArea);
        Scene theScene = new Scene(root);


        primaryStage.setTitle("TributeFX Demo");
        primaryStage.setScene(theScene);
        primaryStage.show();
    }
}
