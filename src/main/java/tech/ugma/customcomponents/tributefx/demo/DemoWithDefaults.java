package tech.ugma.customcomponents.tributefx.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import tech.ugma.customcomponents.tributefx.SimpleMentionable;
import tech.ugma.customcomponents.tributefx.TributeFX;

import java.util.ArrayList;

public class DemoWithDefaults extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        WebView webView = new WebView();
        BorderPane root = new BorderPane(webView);
        Scene scene = new Scene(root);

        TributeFX.tributifyWebView(webView);

        WebEngine webEngine = webView.getEngine();
        ArrayList<SimpleMentionable> mentionables = TributeFX.createSampleMentionableList();
        TributeFX.addMentionables(mentionables, webEngine);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

//    private Object createList() {
//
//    }
}
