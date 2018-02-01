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
    // FIXME: 1/31/2018 Change this to be configurable per WebView, not TributeFX class.
    private static URL promptTextConfiguration = TributeFX.class.getResource("configurePromptText.js");

    /**
     * The default configuration for the Tribute. You can replace it with your own using {@link TributeFX#changeDefaultTributeConfiguration(URL)}.
     * However, to just configure a certain WebView (usual use case) definitely use {@link TributeFX#tributifyWebView(WebView, URL)} to pass in your
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
     * <h2>Adds <a href="https://github.com/zurb/tribute">Tribute</a> to your webView, allowing for @mentions.</h2>
     * <h3>Configures a webView with the <i>default</i> configuration; to have {@link Mentionable mentionables} added later.</h3>
     * To add mentionables use {@link TributeFX#addMentionables(ArrayList, WebEngine)}
     * <p>
     * If you want to customize your Tribute, see {@link TributeFX#tributifyWebView(WebView, URL)}
     */
    public static void tributifyWebView(WebView webView) {
        tributifyWebView(webView, null, null);
    }

    // TODO: 1/31/2018 Go through and make sure all is well for using this method multiple times on the same webView—no duplicate stuff.
    protected static void tributifyWebView(WebView webView, ArrayList<? extends Mentionable> mentionables, URL customConfigURL) {
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
     * <h2>Adds <a href="https://github.com/zurb/tribute">Tribute</a> to your webView, allowing for @mentions.</h2>
     * <h3>Configures a webView with the default configuration and adds a list of {@link Mentionable mentionables}.</h3>
     */
    public static void tributifyWebView(WebView webView, ArrayList<? extends Mentionable> mentionables) {
        tributifyWebView(webView, mentionables, null);
    }

    /**
     * <h2>Adds <a href="https://github.com/zurb/tribute">Tribute</a> to your webView, allowing for @mentions.</h2>
     * <h3>Configures a webView with a <u>custom configuration</u>; to have {@link Mentionable mentionables} added later.</h3>
     * <p>
     * When using your own configuration keep in mind a few things
     * <ul>
     * <li>Configurations are written in JavaScript.</li>
     * <li>The <a href="https://github.com/zurb/tribute#a-collection">default Tribute</a> looks like this.</li>
     * <ul>
     * <li>Though you won't add Mentionables till later, make sure to include {@code values: [],} in your
     * Tribute's configuration, regardless—otherwise there will be nothing to add to, later.</li>
     * <li>For full details about all the configuration options, see <a href="https://github.com/zurb/tribute">Tribute's Website</a></li>
     * </ul>
     * <li>By overriding the default configuration you're responsible for
     * <a href="https://github.com/zurb/tribute#attaching-to-elements">"attaching"</a> the tribute to the right
     * element.</li>
     * <li>If you use a custom {@link Mentionable Mentionable}</li>
     * <ul>
     * <li>You'll only be able to access elements that you've specified in the {@link Mentionable#toJsString() toJString}
     * method. See toJsString()'s documentation to see how easy this is.</li>
     * </ul>
     * </ul>
     * <p>
     * To add mentionables use {@link TributeFX#addMentionables(ArrayList, WebEngine, String, int)} and specify your
     * Tribute variable's name <small>(probably <code>tribute</code>)</small> and the collection you want to add to <small>(0, if you only
     * have one configuration/collection object)</small>.
     * <p>
     *
     * @param customConfigURL a URL to your JavaScript configuration file (I'd get it with something like <code>YourClass.class.getResource("customConfiguration.js");</code>.)
     *                        <p>Here's some <a href="https://stackoverflow.com/a/3862115/5432315">advice for getting resources.</a>
     */
    public static void tributifyWebView(WebView webView, URL customConfigURL) {
        if (customConfigURL == null) {
            throw new IllegalArgumentException("The URL can't be null; this happens if you're not referencing your resource right—it happens to everyone." +
                    "\n\tHere's some advice for getting resources that helped me: https://stackoverflow.com/a/3862115/5432315");
        }

        tributifyWebView(webView, null, customConfigURL);
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


    public static void changeDefaultTributeConfiguration(URL tributeConfiguration) {
        TributeFX.tributeConfiguration = tributeConfiguration;
    }

    /**
     * If you need to change how the tribute's menu 'n things look, try adding to this file (or better yet, just make any changes
     * to the appropriate classes in your own stylesheet.).
     * <p>
     * However, if you want to change how the text that gets left behind when you make a mention looks:
     * try configuring the <code>selectTemplate</code> to leave behind a span with a custom id and then style that id.
     * See {@link TributeFX#tributifyWebView(WebView, URL)}
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
     * {@link TributeFX#tributifyWebView(WebView, URL)})</small> to leave behind something like
     * {@code <span class="mention">@jSample</span>}, and then style the <code>.mention</code> class in your stylesheet.
     * <p>
     * <u>If your applied style doesn't work</u>, try two things:
     * <ul>
     *     <li>Make sure you're getting your stylesheet right</li>
     *     <li>Try <a href="https://stackoverflow.com/a/31370047/5432315">adding "!important"</a> after your style—this
     *     will override any other style before you.</li>
     * </ul>
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
     * the <code>selectTemplate</code>. See {@link TributeFX#tributifyWebView(WebView, URL)}
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
         * Tributize your WebView with custom configuration using {@link TributeFX#tributifyWebView(WebView, URL)}
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


    public static class SimpleMentionable implements Mentionable {
        private String key;
        private String value;

        /**
         * <h3>Key:</h3>
         * <img src="https://i.imgur.com/GPzGu1N.png" />
         * <h3>Value:</h3>
         * <img src="https://i.imgur.com/ibH3OTt.png" />
         * <p>Get it?</p>
         * Use it like this: <code>new SimpleMentionable("Dr. Strange", "@strangerdanger64");</code>
         * <p>
         * See {@link Mentionable}'s documentation
         * for how to do something more elaborate.
         */
        public SimpleMentionable(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    /**
     * Names courtesy of http://listofrandomnames.com
     *
     * @return a sample list of mentionables to test with
     */
    public static ArrayList<SimpleMentionable> createSampleMentionableList() {
        ArrayList<SimpleMentionable> mentionables = new ArrayList<>();

        mentionables.add(new SimpleMentionable("Edie Warshaw", "Edie Warshaw"));
        mentionables.add(new SimpleMentionable("Dorine Scaife", "Dorine Scaife"));
        mentionables.add(new SimpleMentionable("Doreatha Houchens", "Doreatha Houchens"));
        mentionables.add(new SimpleMentionable("Warner Buttrey", "Warner Buttrey"));
        mentionables.add(new SimpleMentionable("Frida Bladen", "Frida Bladen"));
        mentionables.add(new SimpleMentionable("Heriberto Myerson", "Heriberto Myerson"));
        mentionables.add(new SimpleMentionable("Jeri Otte", "Jeri Otte"));
        mentionables.add(new SimpleMentionable("Abe Eno", "Abe Eno"));
        mentionables.add(new SimpleMentionable("Emmanuel Bryner", "Emmanuel Bryner"));
        mentionables.add(new SimpleMentionable("Cami Mclester", "Cami Mclester"));
        mentionables.add(new SimpleMentionable("Latoria Childress", "Latoria Childress"));
        mentionables.add(new SimpleMentionable("Shanti Willoughby", "Shanti Willoughby"));
        mentionables.add(new SimpleMentionable("Neal Raisor", "Neal Raisor"));
        mentionables.add(new SimpleMentionable("Hailey Lagrone", "Hailey Lagrone"));
        mentionables.add(new SimpleMentionable("Roselia Bostwick", "Roselia Bostwick"));
        mentionables.add(new SimpleMentionable("Yu Roof", "Yu Roof"));
        mentionables.add(new SimpleMentionable("Adelia Goings", "Adelia Goings"));
        mentionables.add(new SimpleMentionable("Georgeann Derosia", "Georgeann Derosia"));
        mentionables.add(new SimpleMentionable("Norma Taranto", "Norma Taranto"));
        mentionables.add(new SimpleMentionable("Clinton Rehder", "Clinton Rehder"));
        mentionables.add(new SimpleMentionable("Minda Tyus", "Minda Tyus"));
        mentionables.add(new SimpleMentionable("Jamie Frasier", "Jamie Frasier"));
        mentionables.add(new SimpleMentionable("Maricela Mohammad", "Maricela Mohammad"));
        mentionables.add(new SimpleMentionable("Iesha Fausto", "Iesha Fausto"));
        mentionables.add(new SimpleMentionable("Shu Mickle", "Shu Mickle"));
        mentionables.add(new SimpleMentionable("Darell Delgiudice", "Darell Delgiudice"));
        mentionables.add(new SimpleMentionable("Chi Kitt", "Chi Kitt"));
        mentionables.add(new SimpleMentionable("Billie Eldridge", "Billie Eldridge"));
        mentionables.add(new SimpleMentionable("Rikki Landaverde", "Rikki Landaverde"));
        mentionables.add(new SimpleMentionable("Ivory Gasaway", "Ivory Gasaway"));
        mentionables.add(new SimpleMentionable("Dara Sobol", "Dara Sobol"));
        mentionables.add(new SimpleMentionable("Charleen Raub", "Charleen Raub"));
        mentionables.add(new SimpleMentionable("Cathie Leffel", "Cathie Leffel"));
        mentionables.add(new SimpleMentionable("Pamula Pizano", "Pamula Pizano"));
        mentionables.add(new SimpleMentionable("Edda Choice", "Edda Choice"));
        mentionables.add(new SimpleMentionable("Laverna Mares", "Laverna Mares"));
        mentionables.add(new SimpleMentionable("Gregoria Nyberg", "Gregoria Nyberg"));
        mentionables.add(new SimpleMentionable("Bette Bolton", "Bette Bolton"));
        mentionables.add(new SimpleMentionable("Evonne Uhrich", "Evonne Uhrich"));
        mentionables.add(new SimpleMentionable("Viola Humbertson", "Viola Humbertson"));
        mentionables.add(new SimpleMentionable("Elbert Lynd", "Elbert Lynd"));
        mentionables.add(new SimpleMentionable("Coletta Sabala", "Coletta Sabala"));
        mentionables.add(new SimpleMentionable("Simona Desrochers", "Simona Desrochers"));
        mentionables.add(new SimpleMentionable("Agustina Collings", "Agustina Collings"));
        mentionables.add(new SimpleMentionable("Ulysses Munoz", "Ulysses Munoz"));
        mentionables.add(new SimpleMentionable("Charissa Hise", "Charissa Hise"));
        mentionables.add(new SimpleMentionable("Gloria Horgan", "Gloria Horgan"));
        mentionables.add(new SimpleMentionable("Raylene Fraley", "Raylene Fraley"));
        mentionables.add(new SimpleMentionable("Bobbi Valero", "Bobbi Valero"));
        mentionables.add(new SimpleMentionable("Salena Beverly", "Salena Beverly"));
        mentionables.add(new SimpleMentionable("Eldon Riddell", "Eldon Riddell"));
        mentionables.add(new SimpleMentionable("Lanora Boatwright", "Lanora Boatwright"));
        mentionables.add(new SimpleMentionable("Joaquin Feldt", "Joaquin Feldt"));
        mentionables.add(new SimpleMentionable("Deirdre Setton", "Deirdre Setton"));
        mentionables.add(new SimpleMentionable("Christen Adkison", "Christen Adkison"));
        mentionables.add(new SimpleMentionable("Curtis Nam", "Curtis Nam"));
        mentionables.add(new SimpleMentionable("Deon Beckmann", "Deon Beckmann"));
        mentionables.add(new SimpleMentionable("Destiny Mirza", "Destiny Mirza"));
        mentionables.add(new SimpleMentionable("Bao Hoch", "Bao Hoch"));
        mentionables.add(new SimpleMentionable("Mayme Leonetti", "Mayme Leonetti"));
        mentionables.add(new SimpleMentionable("Kayleigh Kendall", "Kayleigh Kendall"));
        mentionables.add(new SimpleMentionable("Mai Vroman", "Mai Vroman"));
        mentionables.add(new SimpleMentionable("Catalina Hardnett", "Catalina Hardnett"));
        mentionables.add(new SimpleMentionable("Emory Jaqua", "Emory Jaqua"));
        mentionables.add(new SimpleMentionable("Socorro Bramble", "Socorro Bramble"));
        mentionables.add(new SimpleMentionable("Bill Rathke", "Bill Rathke"));
        mentionables.add(new SimpleMentionable("Lanny Luedke", "Lanny Luedke"));
        mentionables.add(new SimpleMentionable("Caron Bumgardner", "Caron Bumgardner"));
        mentionables.add(new SimpleMentionable("John Falls", "John Falls"));
        mentionables.add(new SimpleMentionable("Kati Glaspie", "Kati Glaspie"));
        mentionables.add(new SimpleMentionable("Hettie Elrod", "Hettie Elrod"));
        mentionables.add(new SimpleMentionable("Merideth Queener", "Merideth Queener"));
        mentionables.add(new SimpleMentionable("Ivana Lowry", "Ivana Lowry"));
        mentionables.add(new SimpleMentionable("Kenisha Sanluis", "Kenisha Sanluis"));
        mentionables.add(new SimpleMentionable("Rochell Bunge", "Rochell Bunge"));
        mentionables.add(new SimpleMentionable("Caitlin Croker", "Caitlin Croker"));
        mentionables.add(new SimpleMentionable("Brady Besse", "Brady Besse"));
        mentionables.add(new SimpleMentionable("Lise Bauer", "Lise Bauer"));
        mentionables.add(new SimpleMentionable("Silas Bell", "Silas Bell"));
        mentionables.add(new SimpleMentionable("Janelle Kallenbach", "Janelle Kallenbach"));
        mentionables.add(new SimpleMentionable("Leland Meany", "Leland Meany"));
        mentionables.add(new SimpleMentionable("Tammera Sturtevant", "Tammera Sturtevant"));
        mentionables.add(new SimpleMentionable("Telma Costin", "Telma Costin"));
        mentionables.add(new SimpleMentionable("Emmett Valderas", "Emmett Valderas"));
        mentionables.add(new SimpleMentionable("Jaleesa Premo", "Jaleesa Premo"));
        mentionables.add(new SimpleMentionable("Cortney Statler", "Cortney Statler"));
        mentionables.add(new SimpleMentionable("Laverne Strickland", "Laverne Strickland"));
        mentionables.add(new SimpleMentionable("Cinderella Presson", "Cinderella Presson"));
        mentionables.add(new SimpleMentionable("Ivan Merrow", "Ivan Merrow"));
        mentionables.add(new SimpleMentionable("Jerrica Swartout", "Jerrica Swartout"));
        mentionables.add(new SimpleMentionable("Tiera Quill", "Tiera Quill"));
        mentionables.add(new SimpleMentionable("Billi Kroner", "Billi Kroner"));
        mentionables.add(new SimpleMentionable("Reginia Thomure", "Reginia Thomure"));
        mentionables.add(new SimpleMentionable("Marilou Eichhorn", "Marilou Eichhorn"));
        mentionables.add(new SimpleMentionable("Angelina Gehringer", "Angelina Gehringer"));
        mentionables.add(new SimpleMentionable("Jarred Hollingshead", "Jarred Hollingshead"));
        mentionables.add(new SimpleMentionable("Nelia Burt", "Nelia Burt"));
        mentionables.add(new SimpleMentionable("Saundra Mellinger", "Saundra Mellinger"));
        mentionables.add(new SimpleMentionable("Glennie Richie", "Glennie Richie"));
        mentionables.add(new SimpleMentionable("Jestine Hiller", "Jestine Hiller"));
        mentionables.add(new SimpleMentionable("Blanche Bucklin", "Blanche Bucklin"));
        mentionables.add(new SimpleMentionable("Roxy Hampton", "Roxy Hampton"));
        mentionables.add(new SimpleMentionable("Benjamin Mansir", "Benjamin Mansir"));
        mentionables.add(new SimpleMentionable("Paola Calvillo", "Paola Calvillo"));
        mentionables.add(new SimpleMentionable("Janene Macfarland", "Janene Macfarland"));
        mentionables.add(new SimpleMentionable("Carlena Fendley", "Carlena Fendley"));
        mentionables.add(new SimpleMentionable("Thao Goldner", "Thao Goldner"));
        mentionables.add(new SimpleMentionable("Susannah Florio", "Susannah Florio"));
        mentionables.add(new SimpleMentionable("Marissa Penman", "Marissa Penman"));
        mentionables.add(new SimpleMentionable("Ozie Weesner", "Ozie Weesner"));
        mentionables.add(new SimpleMentionable("Lily Felps", "Lily Felps"));
        mentionables.add(new SimpleMentionable("Gonzalo Kraushaar", "Gonzalo Kraushaar"));
        mentionables.add(new SimpleMentionable("Opal Seals", "Opal Seals"));
        mentionables.add(new SimpleMentionable("Cherish Mangione", "Cherish Mangione"));
        mentionables.add(new SimpleMentionable("Lesia Slinkard", "Lesia Slinkard"));
        mentionables.add(new SimpleMentionable("Marcelene Guss", "Marcelene Guss"));
        mentionables.add(new SimpleMentionable("Jarrod Furby", "Jarrod Furby"));
        mentionables.add(new SimpleMentionable("Dori Gaytan", "Dori Gaytan"));
        mentionables.add(new SimpleMentionable("Leanne Lapierre", "Leanne Lapierre"));
        mentionables.add(new SimpleMentionable("Charlette Pedro", "Charlette Pedro"));
        mentionables.add(new SimpleMentionable("Marco Wendt", "Marco Wendt"));
        mentionables.add(new SimpleMentionable("Santina Failla", "Santina Failla"));
        mentionables.add(new SimpleMentionable("Marylouise Tweedie", "Marylouise Tweedie"));
        mentionables.add(new SimpleMentionable("Viviana Tezeno", "Viviana Tezeno"));
        mentionables.add(new SimpleMentionable("Chassidy Maynes", "Chassidy Maynes"));
        mentionables.add(new SimpleMentionable("Particia Bower", "Particia Bower"));
        mentionables.add(new SimpleMentionable("Isis Ennals", "Isis Ennals"));
        mentionables.add(new SimpleMentionable("Arcelia Glantz", "Arcelia Glantz"));
        mentionables.add(new SimpleMentionable("Marcel Maltby", "Marcel Maltby"));
        mentionables.add(new SimpleMentionable("Carline Denton", "Carline Denton"));
        mentionables.add(new SimpleMentionable("Roosevelt Reidy", "Roosevelt Reidy"));
        mentionables.add(new SimpleMentionable("Denita Hickok", "Denita Hickok"));
        mentionables.add(new SimpleMentionable("Ingeborg Lozada", "Ingeborg Lozada"));
        mentionables.add(new SimpleMentionable("Delorse Bergman", "Delorse Bergman"));
        mentionables.add(new SimpleMentionable("Nevada Robinette", "Nevada Robinette"));
        mentionables.add(new SimpleMentionable("Jasmine Wardwell", "Jasmine Wardwell"));
        mentionables.add(new SimpleMentionable("Spencer Every", "Spencer Every"));
        mentionables.add(new SimpleMentionable("Joette Roehrig", "Joette Roehrig"));
        mentionables.add(new SimpleMentionable("Yee Vella", "Yee Vella"));
        mentionables.add(new SimpleMentionable("Freddy Dieterich", "Freddy Dieterich"));
        mentionables.add(new SimpleMentionable("Reta Denicola", "Reta Denicola"));
        mentionables.add(new SimpleMentionable("Zachariah Striegel", "Zachariah Striegel"));
        mentionables.add(new SimpleMentionable("Ching Hornak", "Ching Hornak"));
        mentionables.add(new SimpleMentionable("Doretha Baumeister", "Doretha Baumeister"));
        mentionables.add(new SimpleMentionable("Shon Even", "Shon Even"));
        mentionables.add(new SimpleMentionable("Regina Allis", "Regina Allis"));
        mentionables.add(new SimpleMentionable("Carlee Marquette", "Carlee Marquette"));
        mentionables.add(new SimpleMentionable("Gay Dollins", "Gay Dollins"));
        mentionables.add(new SimpleMentionable("Floretta Utley", "Floretta Utley"));
        mentionables.add(new SimpleMentionable("Josie Keener", "Josie Keener"));
        mentionables.add(new SimpleMentionable("John Sample", "John Sample"));

        return mentionables;
    }

}
