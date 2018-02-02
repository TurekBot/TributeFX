package tech.ugma.customcomponents.tributefx;

import javafx.scene.web.WebView;

import java.net.URL;

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
 * the <code>selectTemplate</code>. See {@link TributeFX#tributifyWebView(WebView, java.io.InputStream)}
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
     * Tributize your WebView with custom configuration using {@link TributeFX#tributifyWebView(WebView, java.io.InputStream)}
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
