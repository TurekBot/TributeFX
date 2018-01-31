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

    /**
     * For now we have to use this HTML document because of we're using relative references to the CSS files.
     */
    private static URL container = TributeFX.class.getResource("container.html");
    /**
     * This styles the actual HTML inside the WebView. It makes the editable part take up the whole WebView
     */
    private static URL containerStyleSheet = TributeFX.class.getResource("container.css");

    /**
     * Configures some prompt text that disappears when the user clicks focuses the WebView.
     * <p>
     * Change the text using {@link TributeFX#turnPromptTextOn(java.lang.String)}.
     */
    private static URL promptTextConfiguration = TributeFX.class.getResource("configurePromptText.js");

    /**
     * The default configuration for the Tribute. You can use your own with {@link TributeFX#setTributeConfiguration(URL)}.
     */
    private static URL tributeConfiguration = TributeFX.class.getResource("configureTribute.js");

    /**
     * The actual Tribute Library. Version 3.1.3. See <a href="https://github.com/zurb/tribute/releases">Tribute's Website</a>
     */
    private static URL tributeLibrary = TributeFX.class.getResource("tribute-js/tribute.js");
    /**
     * Tribute's stylesheet. Used internally by Tribute.
     */
    private static URL tributeLibraryStylesheet = TributeFX.class.getResource("tribute-js/tribute.css");

    /**
     *
     */
    private static boolean showPromptText = true;

    /**
     * The text that is shown while the WebView is not in focus. Edit it with {@link TributeFX#turnPromptTextOn(String)}.
     */
    private static String promptText = "To mention someone try, \"Hey, @John Sample, can you...\"";

    /**
     * Default config; to have mentionables added later
     * TODO: 1/31/2018 Use that one "share documentation" tag
     */
    public static void configureWebView(WebView webView) {
        configureWebView(webView, null, null);
    }

    protected static void configureWebView(WebView webView, ArrayList<? extends Mentionable> mentionables, URL customConfigURL) {
        WebEngine webEngine = webView.getEngine();

        // Load HTML
        webEngine.load(container.toExternalForm());

        //Add Tribute files
        addTributeFiles(webEngine);

        //Configure our tribute
        configureTribute(webEngine, customConfigURL);

        if (mentionables != null) {
            //Add mentionables to list
            addMentionables(mentionables, webEngine);
        }

        if (showPromptText) {
            configurePromptText(webEngine);
        }

        mimicBlueGlow(webView);

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

    private static void configureTribute(WebEngine webEngine, URL customConfigURL) {
        URL configuration;
        if (customConfigURL == null) {
            configuration = tributeConfiguration;
        } else {
            configuration = customConfigURL;
        }

        String configurationFile = readFile(configuration);

        executeLater(webEngine, configurationFile);
    }

//    /**
//     * Use this if you're using the <b>default configuration</b>.
//     * <p>
//     * This will add your mentionables to the tribute, so that they'll show up when the user types "@"
//     * <p>
//     * For custom tribute configurations <small>(where you may have changed/multiple tribute variable names)</small> use {@link TributeFX#addMentionables(ArrayList, WebEngine)}
//     */
//    appendMentionables(webEngine, mentionables)
//
//    appendMentionables(webEngine, mentionables, tributeVariableName, collectionIndex)

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

    private static void configurePromptText(WebEngine webEngine) {
        String promptTextConfigurationScript = readFile(promptTextConfiguration);

        final String replace = "PROMPT_TEXT_WILL_BE_REPLACED_HERE";
        promptTextConfigurationScript = promptTextConfigurationScript.replace(replace, promptText);

        executeLater(webEngine, promptTextConfigurationScript);
    }

    /**
     * WebViews are *not* Regions. WebViews are Parents. Regions have the necessary things for a focus glow. Parents, do not.
     * <p>
     * So we mimic that behavior here.
     */
    private static void mimicBlueGlow(WebView webView) {
        webView.getStylesheets().add(TributeFX.class.getResource("webView.css").toString());
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

    /**
     * For configuring a webView with the default configuration and adding some mentionables.
     * TODO: 1/31/2018 Use that one "share documentation" tag
     */
    public static void configureWebView(WebView webView, ArrayList<? extends Mentionable> mentionables) {
        configureWebView(webView, mentionables, null);
    }

    /**
     * For configuring a webView with a custom configuration; to have mentionables added later.
     * TODO: 1/31/2018 Use that one "share documentation" tag
     */
    public static void configureWebView(WebView webView, URL customConfigURL) {
        configureWebView(webView, null, customConfigURL);
        // TODO: 1/31/2018 Check for unwanted nulls
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

    /**
     * When using your own configuration keep in mind a few things
     * <ul>
     * <li>Configurations are written in JavaScript.</li>
     * <li>What the <a href="">default Tribute</a> looks like.</li>
     * <ul>
     * <li>For full details about all the configuration options, see <a href="">Tribute's Website</a></li>
     * </ul>
     * <li>If you use a custom {@link Mentionable Mentionable}</li>
     * <ul>
     * <li>You'll only be able to access elements that you've specified in the {link toJsString()} method. See toJsString()'s documentation to see how easy this is.</li>
     * </ul>
     * <li>We assume your tribute variable's name is <code>tribute</code>. If you have a different variable name, the append functionality won't work.</li>
     * <li>By overriding the default configuration you're responsible for "attaching" the tribute to the right element. Stick with <code>tribute.attach(document.getElementsById('tributable-container'));</code></li>
     * </ul>
     *
     * @param tributeConfiguration a URL to your JavaScript configuration file (I'd get it with something like <code>YourClass.class.getResource("customConfiguration.js");</code>.)
     *                             <p>Here's some <a href="https://stackoverflow.com/a/3862115/5432315">advice for getting resources.</a>
     */
    public static void setTributeConfiguration(URL tributeConfiguration) {
        TributeFX.tributeConfiguration = tributeConfiguration;
    }

    /**
     * If you need to change how the tribute's menu 'n things look, try adding to this file (or better yet, just make any changes
     * to the appropriate classes in your own stylesheet.).
     * <p>
     * However, if you want to change how the text that gets left behind when you make a mention looks:
     * try configuring the <code>selectTemplate</code> to leave behind a span with a custom id and then style that id.
     * See {@link TributeFX#setTributeConfiguration(java.net.URL)}
     *
     * @return
     */
    public static URL getTributeLibraryStylesheet() {
        return tributeLibraryStylesheet;
    }

    /**
     * This sets the styles <i>within</i> the WebView.
     * <p>
     * To customize how the mentions look, you'll need to configure the <code>selectTemplate</code> <small>(using
     * {@link TributeFX#setTributeConfiguration(URL)})</small> to leave behind something like
     * {@code <span class="mention">@jSample</span>}, and then style the <code>.mention</code> class in your stylesheet.
     * <p>
     * If you really feel like it, you can style the <code>tributable-container</code> <small>(but be
     * careful because if you change its size it might get ugly.)</small>
     */
    public static void setWebViewInternalStyleSheet(WebView webView, URL customStyleSheetURL) {
        webView.getEngine().setUserStyleSheetLocation(customStyleSheetURL.toString());
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
    }
}
