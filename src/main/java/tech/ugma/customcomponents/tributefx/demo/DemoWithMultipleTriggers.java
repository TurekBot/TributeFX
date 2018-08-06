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
import java.util.Arrays;
import java.util.List;

public class DemoWithMultipleTriggers extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        WebView webView1 = new WebView();
        BorderPane root = new BorderPane();
        root.setCenter(webView1);
        Scene scene = new Scene(root);

        // Add your own custom Tribute configuration (see https://github.com/zurb/tribute#a-collection)
        InputStream multipleTriggerConfig = DemoWithMultipleTriggers.class.getResourceAsStream("configureTributeWithMultipleTriggers.js");

        TributeFX.turnPromptTextOn("To mention someone use @. To use a hashtag, use #.");

        TributeFX.tributifyWebView(webView1, multipleTriggerConfig);


        WebEngine webEngine1 = webView1.getEngine();
        ArrayList<SimpleMentionable> mentionables = TributeFX.createSampleMentionableList();

        TributeFX.addMentionables(mentionables, webEngine1, "tribute", 0);

        List<SimpleMentionable> moreHashtags =
                Arrays.asList(new SimpleMentionable("Lame", "Lame"), new SimpleMentionable("Relatable", "Relatable"));

        TributeFX.addMentionables(moreHashtags, webEngine1, "tribute", 1);

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
