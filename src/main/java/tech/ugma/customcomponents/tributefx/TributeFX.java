package tech.ugma.customcomponents.tributefx;

import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TributeFX {

    private static URL container = TributeFX.class.getResource("container.html");
    private static URL containerStyleSheet = TributeFX.class.getResource("container.css");
    private static URL containerConfiguration = TributeFX.class.getResource("configureContainer.js");

    private static URL tributeConfiguration = TributeFX.class.getResource("configureTribute.js");
    private static URL tributeLibrary = TributeFX.class.getResource("tribute-js/tribute.js");
    private static URL tributeStylesheet = TributeFX.class.getResource("tribute-js/tribute.css");


    public static void configureWebView(WebView toConfigure) {
        WebEngine webEngine = toConfigure.getEngine();

        // Load HTML
        webEngine.load(container.toExternalForm());


        //Add Tribute files
        addTributeFiles(webEngine);

        //Configure our tribute
        configureTribute(webEngine);

    }


    private static void configureTribute(WebEngine webEngine) {
        final String configurationFile = readFile(tributeConfiguration);

        //We need to put anything that has to do with the container (like attaching a tribute to it)
        //in a place that will only be invoked once the container/document is loaded.
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            Worker.State state = webEngine.getLoadWorker().getState();
            if (state.equals(Worker.State.SUCCEEDED)) {
                webEngine.executeScript(configurationFile);
            }
        });

    }

    private static void addTributeFiles(WebEngine webEngine) {
        webEngine.executeScript(readFile(tributeLibrary));
        // FIXME: 1/30/2018 This will break something if someone else tries to set the userStyleSheet after/before us
        //Add Tribute Stylesheet
        webEngine.setUserStyleSheetLocation(tributeStylesheet.toExternalForm());
    }

    private static String readFile(URL file) {
        String fileContents = null;
        try {
            fileContents = new String(Files.readAllBytes(Paths.get(file.toURI())));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return fileContents;
    }
}
