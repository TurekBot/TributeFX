package tech.ugma.customcomponents.tributefx.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import tech.ugma.customcomponents.tributefx.TributeFX;

import java.net.URL;
import java.util.ArrayList;

public class DemoWithCustomConfig extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        WebView webView = new WebView();
        BorderPane root = new BorderPane(webView);
        Scene scene = new Scene(root);

        //Add your own custom Tribute configuration (see https://github.com/zurb/tribute#a-collection)
        URL customConfig = DemoWithCustomConfig.class.getResource("customTributeConfiguration.js");

        TributeFX.tributifyWebView(webView, customConfig);

        WebEngine webEngine = webView.getEngine();
        ArrayList<TributeFX.SimpleMentionable> mentionables = TributeFX.createSampleMentionableList();

        TributeFX.addMentionables(mentionables, webEngine);

        //Uncomment to add your own style
//        URL customCSS = DemoWithCustomConfig.class.getResource("customTributeStyle.css");
//        TributeFX.setWebViewInternalStyleSheet(webView, customCSS);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

//    private Object createList() {
//
//    }
}
