package tech.ugma.customcomponents.tributefx;

public class SimpleMentionable implements Mentionable {
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
