package tech.ugma.customcomponents.tributefx.demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import tech.ugma.customcomponents.tributefx.TributeFX;
import tech.ugma.customcomponents.tributefx.TributeFX.Mentionable;

import java.net.URL;
import java.util.ArrayList;

public class DemoTributeFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        WebView webView = new WebView();

        TributeFX.turnPromptTextOn();

        ArrayList<Person> mentionables = createList();

        //Use custom configuration
        URL demoConfig = DemoTributeFX.class.getResource("demoTributeConfiguration.js");

        //Give custom style
//        TributeFX.setWebViewInternalStyleSheet(textArea, customStyleSheet);

        TributeFX.tributifyWebView(webView, demoConfig);

        Platform.runLater(() -> {
            TributeFX.addMentionables(mentionables, webView.getEngine());
        });

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(20));
        Label comment = new Label("Comment");
        Button submitButton = new Button("Submit");
        TextArea realTextArea = new TextArea();
        vBox.getChildren().addAll(comment, webView, submitButton, realTextArea);
        BorderPane root = new BorderPane(vBox);
        Scene theScene = new Scene(root);


        primaryStage.setTitle("TributeFX Demo");
        primaryStage.setScene(theScene);
        primaryStage.show();

    }

    private ArrayList<Person> createList() {
        ArrayList<Person> mentionables = new ArrayList<>();
        mentionables.add(new Person("Brad Turek", "bturek", "UgmaDevelopment@gmail.com"));
        mentionables.add(new Person("Rick Turek", "rturek", "Rick.Turek@sundance.net"));
        mentionables.add(new Person("Greg Gabbitas", "ggabbitas", "Greg.Gabbitas@sundance.net"));
        mentionables.add(new Person("Deborah Kaiser", "dkaiser", "Deborah.Kaiser@sundance.net"));
        mentionables.add(new Person("John Sample", "jsample", "John.Sample@gmail.com"));
        return mentionables;
    }


    public class Person implements Mentionable {
        String fullName;
        String userName;
        String email;

        public Person(String fullName, String userName, String email) {
            this.fullName = fullName;
            this.userName = userName;
            this.email = email;
        }

        @Override
        public String getKey() {return fullName;}

        @Override
        public String getValue() {return userName;}

        @Override
        public String toJsString() {
            return  "{key: '" + getKey() + "', value: '" + getValue() + "', email: '" + email + "'}";
        }

    }


}
