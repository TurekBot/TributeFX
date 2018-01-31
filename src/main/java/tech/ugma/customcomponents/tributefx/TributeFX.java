package tech.ugma.customcomponents.tributefx;

import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

// TODO: 1/30/2018 Add getters and setters for the configuration files
public class TributeFX {

    private static URL container = TributeFX.class.getResource("container.html");
    private static URL containerStyleSheet = TributeFX.class.getResource("container.css");

    private static URL promptTextConfiguration = TributeFX.class.getResource("configurePromptText.js");

    private static URL tributeConfiguration = TributeFX.class.getResource("configureTribute.js");
    private static URL tributeLibrary = TributeFX.class.getResource("tribute-js/tribute.js");
    private static URL tributeLibraryStylesheet = TributeFX.class.getResource("tribute-js/tribute.css");

    private static boolean showPromptText = false;
    private static String promptText = "To mention someone try, \"Hey, @John Sample, can you...\"";

    public static void configureWebView(WebView webView, ArrayList<? extends Mentionable> mentionables) {
        WebEngine webEngine = webView.getEngine();

        // Load HTML
        webEngine.load(container.toExternalForm());

        //Add Tribute files
        addTributeFiles(webEngine);

        //Configure our tribute
        configureTribute(webEngine);

        //Add mentionables to list
        addMentionables(mentionables, webEngine);

        if (showPromptText) {
            configurePromptText(webEngine);
        }

        mimicBlueGlow(webView);

    }

    private static void addMentionables(ArrayList<? extends Mentionable> mentionables, WebEngine webEngine) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mentionables.size(); i++) {
            Mentionable m = mentionables.get(i);

            sb.append(m.toJsString());

            if (i < mentionables.size() - 1) {
                //If it's not the last one, add a comma
                sb.append(", ");
            }
        }
        String command = "tribute.append(0, [\n" + sb.toString() + "]);";
        executeLater(webEngine, command);
    }

    /**
     * WebViews are *not* Regions. WebViews are Parents. Regions have the necessary things for a focus glow. Parents, do not.
     * <p>
     * So we mimic that behavior here.
     */
    private static void mimicBlueGlow(WebView webView) {
        webView.getStylesheets().add(TributeFX.class.getResource("webView.css").toString());
    }

    private static void executeLater(WebEngine webEngine, String commands) {
        //We need to put anything that has to do with the container (like attaching a tribute to it)
        //in a place that will only be invoked once the container/document is loaded.
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            Worker.State state = webEngine.getLoadWorker().getState();
            if (state.equals(Worker.State.SUCCEEDED)) {
                webEngine.executeScript(commands);
            }
        });

    }

    private static void configurePromptText(WebEngine webEngine) {
        String promptTextConfigurationScript = readFile(promptTextConfiguration);

        final String replace = "PROMPT_TEXT_WILL_BE_REPLACED_HERE";
        promptTextConfigurationScript = promptTextConfigurationScript.replace(replace, promptText);

        executeLater(webEngine, promptTextConfigurationScript);
    }


    private static void configureTribute(WebEngine webEngine) {
        String configurationFile = readFile(tributeConfiguration);

        executeLater(webEngine, configurationFile);
    }

    private static void addTributeFiles(WebEngine webEngine) {
        webEngine.executeScript(readFile(tributeLibrary));
        /*The tribute stylesheet is loaded *in* the default HTML container.*/

        // TODO: 1/31/2018 support using user HTML (the problem with this is how right now we're using relative css file references, you know, `<link rel="stylesheet" href="tribute-js/tribute.css"/>`, and changing the document's location or even loading it from memory causes the document's baseURI to change and that breaks the relative references to the CSS files)
//        //If the document's base URI is not within the TributeFX package, then add the CSS inline.
//        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
//            @Override
//            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
//                if (newValue.equals(Worker.State.SUCCEEDED)) {
//                    try {
//                        //Let's check if we're dealing with our default HTML container (the one we made)
//                        if (!new URI(webEngine.getDocument().getDocumentURI()).toURL().sameFile(container)) {
//                            System.out.println("The default HTML is not being used... oh boy.");
//
//
//                            String baseURIString = webEngine.getDocument().getBaseURI();
//                            if (baseURIString != null) {
//
//                                //Now let's check if it's where it should be in order to access the relatively referenced css files
//                                URI baseURI = new URI(baseURIString);
//                                if (Paths.get(baseURI))
//                                    TributeFX.class.get
//
//                            } else {
//                                //If the URI is null it means that the HTML was loaded from a string.
//                                //This means that no relative references will work within the HTML.
//                                //Our best bet, as far as I can tell, is to now extract out all the CSS from our files and put it in
//                                //the document's <head>
//
//                            }
//
////            if ()
//
////        //Add Tribute Stylesheet if the HTML has been changed
////        if (!tributeLibraryStylesheet.sameFile(TributeFX.class.getResource("tribute-js/tribute.css"))) {
////            Document document = webEngine.getDocument();
////            Element link = document.createElement("link");
////            link.setAttribute("rel", "stylesheet");
////            link.setAttribute("href", "tribute-js/tribute.css");
////
////            document.getElementsByTagName("head").item(0).appendChild(link);
////        }
//                        } else {
//                            System.out.println("The default HTML is not being used... oh boy.");
//                        }
//                    } catch (URISyntaxException e) {
//                        e.printStackTrace();
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
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


    public static void turnPromptTextOn() {
        showPromptText = true;
    }

    public static void turnPromptTextOn(String promptText) {
        showPromptText = true;
        TributeFX.promptText = promptText;
    }

    public static void turnPromptTextOff() {
        showPromptText = false;
    }

    public static URL getTributeConfiguration() {
        return tributeConfiguration;
    }

    public static void setTributeConfiguration(URL tributeConfiguration) {
        TributeFX.tributeConfiguration = tributeConfiguration;
    }

    public static URL getTributeLibraryStylesheet() {
        return tributeLibraryStylesheet;
    }

    public static void setTributeLibraryStylesheet(URL tributeLibraryStylesheet) {
        TributeFX.tributeLibraryStylesheet = tributeLibraryStylesheet;
    }

    /**
     * Implement Mentionable in the class that you want to pass to the <a href="https://github.com/zurb/tribute">Tribute</a>.
     * By default, 'key' will be looked up and 'value' will be what's left behind after a mention is made.
     * <p>
     * Here's a simple example where you search for the person by name and their userName is left behind.
     * <pre>{@code
     * public class Person implements Mentionable {
     *     String fullName;
     *     String userName;
     *
     *     public Person(String fullName, String userName) {
     *         this.fullName = fullName;
     *         this.userName = userName;
     *     }
     *     @Override
     *     public String getKey() {return fullName;}
     *     @Override
     *     public String getValue() {return userName;}
     * }
     * }</pre>
     * <p>
     * To expose any extra attributes, you'll have to override {@link Mentionable#toJsString()} to display your object correctly.
     * In order to leave any of those extra attributes behind in some hidden markup, you'll need to configure
     * the `selectTemplate`. See <a href="https://github.com/zurb/tribute#a-collection">https://github.com/zurb/tribute#a-collection</a>
     * <pre>{@code
     * public class Person implements Mentionable {
     *     String fullName;
     *     String userName;
     *
     *     public Person(String fullName, String userName) {
     *         this.fullName = fullName;
     *         this.userName = userName;
     *     }
     *     @Override
     *     public String getKey() {return fullName;}
     *     @Override
     *     public String getValue() {return userName;}
     * }
     * }</pre>
     */
    public interface Mentionable {
        /**
         * By default this is the thing to lookup. The value that is looked up can be configured by changing
         * the value of `lookup` in the Tribute's configuration; see <a href="https://github.com/zurb/tribute#a-collection">https://github.com/zurb/tribute#a-collection</a>
         * Change the configuration using {@link TributeFX#setTributeConfiguration(java.net.URL)}
         */
        String getKey();

        /**
         * By default this is the thing that will get left behind after the mention is made.
         * HOWEVER, for more involved things, like when you want to include identifying markup,
         * like, <span contenteditable="false" id="mention" email="username@example.com">John Sample</span>,
         * configure the `selectTemplate` function; see https://github.com/zurb/tribute#a-collection
         */
        String getValue();

        /**
         * This should return your object as a JavaScript String.
         * You should override this method if you want to expose any extra attributes besides key and value.
         * <p>
         * A few examples:
         * <pre>{@code
         * "{key: '" + getKey() + "', value: '" + getValue() + "'}"; //The default if you don't override
         * "{key: '" + getKey() + "', value: '" + getValue() + "', email: '" + getEmail() +"'}";}
         * </pre>
         *
         * @return your object formatted as a javascript object; see examples.
         */
        default String toJsString() {
            return "{key: '" + getKey() + "', value: '" + getValue() + "'}";
        }
    }
}
