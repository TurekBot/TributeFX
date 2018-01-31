package tech.ugma.customcomponents.tributefx.demo;

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

        //Change configuration
        URL demoConfig = DemoTributeFX.class.getResource("demoTributeConfiguration.js");
//        TributeFX.setTributeConfiguration(demoConfig);

        //Give custom style
//        TributeFX.setWebViewInternalStyleSheet(textArea, customStyleSheet);

        TributeFX.configureWebView(webView, demoConfig);

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
        mentionables.add(new Person("Brad", "Turek", "UgmaDevelopment@gmail.com"));
        mentionables.add(new Person("Rick", "Turek", "Rick.Turek@sundance.net"));
        mentionables.add(new Person("Greg", "Gabbitas", "Greg.Gabbitas@sundance.net"));
        mentionables.add(new Person("Deborah", "Kaiser", "Deborah.Kaiser@sundance.net"));
        mentionables.add(new Person("John", "Sample", "John.Sample@gmail.com"));
        return mentionables;
    }



    public class Person implements Mentionable {
        String firstName;
        String lastName;
        String email;

        public Person(String firstName, String lastName, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }

        @Override
        public String getKey() {
            return firstName + " " + lastName;
        }

        @Override
        public String getValue() {
            return email;
        }
    }


}
