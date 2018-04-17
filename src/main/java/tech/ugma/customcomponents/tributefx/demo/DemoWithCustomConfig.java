package tech.ugma.customcomponents.tributefx.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import tech.ugma.customcomponents.tributefx.SimpleMentionable;
import tech.ugma.customcomponents.tributefx.TributeFX;

import java.io.InputStream;
import java.util.ArrayList;

public class DemoWithCustomConfig extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        WebView webView1 = new WebView();
        WebView webView2 = new WebView();
        BorderPane root = new BorderPane();
        root.setLeft(webView1);
        root.setRight(webView2);
        Scene scene = new Scene(root);

        // Add your own custom Tribute configuration (see https://github.com/zurb/tribute#a-collection)
        InputStream customConfig = DemoWithCustomConfig.class.getResourceAsStream("customTributeConfiguration.js");

        TributeFX.tributifyWebView(webView1, customConfig);

        // You'll need to create your stream again if you want to use it again—blame ZipFileSystems for not having resettable streams.
        customConfig = DemoWithCustomConfig.class.getResourceAsStream("customTributeConfiguration.js");

        TributeFX.tributifyWebView(webView2, customConfig);

        WebEngine webEngine1 = webView1.getEngine();
        WebEngine webEngine2 = webView2.getEngine();
        ArrayList<SimpleMentionable> mentionables = TributeFX.createSampleMentionableList();

        TributeFX.addMentionables(mentionables, webEngine1);
        TributeFX.addMentionables(mentionables, webEngine2);

        // Uncomment to add your own style
//        URL customCSS = DemoWithCustomConfig.class.getResource("customTributeStyle.css");
//        TributeFX.setWebViewInternalStyleSheet(webView, customCSS);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

//    private Object createList() {
//
//    }
}
