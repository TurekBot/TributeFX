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
     * The default configuration for the Tribute. You can replace it with your own using {@link TributeFX#changeDefaultTributeConfiguration(URL)}.
     * However, to just configure a certain WebView (usual use case) definitely use {@link TributeFX#configureWebView(WebView, URL)} to pass in your
     * custom configuration JavaScript file.
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

    // TODO: 1/31/2018 Go through and make sure all is well for using this method multiple times on the same webView—no duplicate stuff.
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

        executeJavaScriptCommands(webEngine, configurationFile);
    }

    /**
     * Use this if you're using the <b>default configuration</b>.
     * <p>
     * This will add your mentionables to the tribute, so that they'll show up when the user types "@"
     * <p>
     * For custom tribute configurations <small>(where you may have changed/multiple tribute variable names)</small> use {@link TributeFX#addMentionables(java.util.ArrayList, javafx.scene.web.WebEngine, java.lang.String, int)}
     */
    public static void addMentionables(ArrayList<? extends Mentionable> mentionables, WebEngine webEngine) {
        addMentionables(mentionables, webEngine, "tribute", 0);
    }

    /**
     * Use this if you have a <b>custom configuration</b>.
     * <p>
     * This will add your <code>{@link Mentionable Mentionable}</code>s to the specified tribute, so that they'll show up when the user types the trigger character.
     * <p>
     * Be sure to specify the tribute variable's name ("tribute" if you kept it the same) and the collection's index
     * (0 if you only have one).
     * <p>
     * <i>If you're using the defaults</i>, for convenience, use {@link TributeFX#addMentionables(ArrayList, WebEngine)}
     */
    public static void addMentionables(ArrayList<? extends Mentionable> mentionables, WebEngine webEngine, String tributeVariableName, int collectionIndex) {
        //Build javascript list
        StringBuilder list = new StringBuilder();
        for (int i = 0; i < mentionables.size(); i++) {
            Mentionable m = mentionables.get(i);

            list.append(m.toJsString());

            if (i < mentionables.size() - 1) {
                //If it's not the last one, add a comma
                list.append(", \n");
            }
        }

        // Build JavaScript command (a concatenation is used here because IntelliJ says it's *just as
        // efficient* in this case)
        String command = tributeVariableName + ".append(" +
                collectionIndex + ", [\n" +
                list.toString() +
                "\n]);";

        // Execute command as soon as document is loaded
        executeJavaScriptCommands(webEngine, command);
    }

    private static void configurePromptText(WebEngine webEngine) {
        String promptTextConfigurationScript = readFile(promptTextConfiguration);

        final String replace = "PROMPT_TEXT_WILL_BE_REPLACED_HERE";
        promptTextConfigurationScript = promptTextConfigurationScript.replace(replace, promptText);

        executeJavaScriptCommands(webEngine, promptTextConfigurationScript);
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

    private static void executeJavaScriptCommands(WebEngine webEngine, String commands) {
        Worker.State state = webEngine.getLoadWorker().getState();
        System.out.println("Current State: " + webEngine.getLoadWorker().getState());
        if (webEngine.getDocument() == null) {
            //We need to put anything that has to do with the container (like attaching a tribute to it)
            //in a place that will only be invoked once the container/document is loaded.
            webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, currentState) -> {
                System.out.println(webEngine.getLoadWorker().toString());
                if (currentState.equals(Worker.State.SUCCEEDED)) {
                    webEngine.executeScript(commands);
                }
            });
        } else if (state.equals(Worker.State.READY) || state.equals(Worker.State.SUCCEEDED)) {
            webEngine.executeScript(commands);
        }

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
    public static void changeDefaultTributeConfiguration(URL tributeConfiguration) {
        TributeFX.tributeConfiguration = tributeConfiguration;
    }

    /**
     * If you need to change how the tribute's menu 'n things look, try adding to this file (or better yet, just make any changes
     * to the appropriate classes in your own stylesheet.).
     * <p>
     * However, if you want to change how the text that gets left behind when you make a mention looks:
     * try configuring the <code>selectTemplate</code> to leave behind a span with a custom id and then style that id.
     * See {@link TributeFX#configureWebView(WebView, URL)}
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
     * {@link TributeFX#configureWebView(WebView, URL)})</small> to leave behind something like
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
     * <p>
     * In order to leave any of those extra attributes behind in some hidden markup, you'll need to configure
     * the <code>selectTemplate</code>. See {@link TributeFX#configureWebView(WebView, URL)}
     * <h3>Custom Mentionable Example</h3>
     * <pre>{@code
     * public class Person implements Mentionable {
     *     String fullName;
     *     String userName;
     *     String email;
     *
     *     public Person(String fullName, String userName, String email) {
     *         this.fullName = fullName;
     *         this.userName = userName;
     *         this.email = email;
     *     }
     *     @Override
     *     public String getKey() {return fullName;}
     *     @Override
     *     public String getValue() {return userName;}
     *     @Override
     *     public String toJsString() { //Includes email in the string that gets turned into a JavaScript object
     *         return  "{key: '" + getKey() + "', value: '" + getValue() + "', email: '" + email + "'}";
     *     }
     * }
     * }</pre>
     */
    public interface Mentionable {
        /**
         * This should return a string that, once turned into a JavaScript Object, should accurately represent your class
         * in JavaScript.
         * <p>
         * If you don't need to expose anything extra, you're done here. The default is already set to:
         * <p>
         * {@code "{key: '" + getKey() + "', value: '" + getValue() + "'}"; //The default if you don't override anything}
         * <p>
         * The resulting string, <code>{key: 'John Sample', value: 'jsample'}</code>, will eventually be recognized
         * as an <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Grammar_and_types#Object_literals">Object Literal</a> by the WebEngine.
         * <h3>Extra attributes</h3>
         * You should <i>override</i> this method <i>if you want to expose any extra attributes:</i>.
         * <pre><code>
         * {@literal @}Override
         * public String toJsString() {
         *     return  "{key: '" + getKey() + "', value: '" + getValue() + "', email: '" + email + "'}"; //Exposes email, too
         * }</code></pre>
         *
         * @return your object formatted as a javascript object; see examples.
         */
        default String toJsString() {
            return "{key: '" + getKey() + "', value: '" + getValue() + "'}";
        }

        /**
         * By default, this (<code>key</code>) is the thing Tribute searches for when the user starts typing something like "@John".
         * <p>
         * The attribute that Tribute uses can be configured by changing the value of <code>lookup</code> in
         * the Tribute's <a href="https://github.com/zurb/tribute#a-collection">configuration</a>.
         * <p>
         * Tributize your WebView with custom configuration using {@link TributeFX#configureWebView(WebView, URL)}
         */
        String getKey();

        /**
         * By default, this (<code>value</code>) is the thing that Tribute will leave behind after the mention is made.
         * <p>
         * In @mention systems, often, you search for the person's name, but want their <i>username</i> to be left behind.
         * <i>In this case</i>, the <i>username</i> would be the value.
         * <p>
         * HOWEVER, for more involved things, like when you want the value to include identifying markup,
         * like, {@code <span contenteditable="false" id="mention" email="username@example.com">John Sample</span>},
         * configure the <code>selectTemplate</code> function.
         *
         * @see <a href="https://github.com/zurb/tribute#a-collection">Tribute Documentation</a> for how to configure a
         * <code>selectTemplate</code>
         */
        String getValue();
    }
}
