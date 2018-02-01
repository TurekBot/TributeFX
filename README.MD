# TributeFX

A `@mention` engine for JavaFX.

#### Motivation
I looked high and low for a JavaFX `@mention` engine—no luck. 

I welcome the day that there *is* one, but until
then, here's a wrapper for the JavaScript, Native `@mention` engine, [Tribute.js][Tribute].

## Getting Started
* Coming Soon: Maven Central

Or

* Download/Clone the repo from here and build it for yourself with `mvn clean install`

### Demo
[![Hunger Games Demo]][Hunger Games Demo]

### Quickstart Example
To just _try it out_, make a WebView and do:

    TributeFX.tributifyWebView(webView);
    TributeFX.addMentionables(TributeFX.createSampleMentionableList(), webView.getEngine());

See the [DemoWithDefaults] for such a working example.

### Adding Mentionables
If you're keeping it simple, use, [`SimpleMentionable`]
    
    ArrayList<SimpleMentionable> mentionables = new ArrayList<>();
    mentionables.add(new SimpleMentionable("key", "value"));
    mentionables.add(new SimpleMentionable("John Sample", "jjSwample22"));
    
    TributeFX.addMentionables(webView, mentionables);

To pass in more attributes, like an email, see [`Mentionable`]'s Documentation.

### [Customization][get custom with it]
Once you've cut your teeth on the basics, you can whip out some JavaScript and [get custom with it]:

    URL customConfig = DemoWithCustomConfig.class.getResource("customTributeConfiguration.js");

    TributeFX.tributifyWebView(webView, customConfig);

    WebEngine webEngine = webView.getEngine();
    ArrayList<TributeFX.SimpleMentionable> mentionables = TributeFX.createSampleMentionableList();
    
    TributeFX.addMentionables(mentionables, webEngine);
    
See [DemoWithCustomConfig] to really get jiggy with it.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository]

## Authors

* **Zurb** - *JavaScript @mention Engine* - [Tribute]
* **Bradley Turek** - *Initial Wrapping of Tribute*

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

[Tribute]: https://github.com/zurb/tribute
[Hunger Games Demo]: https://i.imgur.com/yLadZRo.gif
[DemoWithDefaults]: src/main/java/tech/ugma/customcomponents/tributefx/demo/DemoWithDefaults.java
[get custom with it]: https://github.com/zurb/tribute#a-collection "See Tribute's website for all the configuration options."
[DemoWithCustomConfig]: src/main/java/tech/ugma/customcomponents/tributefx/demo/DemoWithCustomConfig.java
[`Mentionable`]: src/main/java/tech/ugma/customcomponents/tributefx/Mentionable.java
[`SimpleMentionable`]: src/main/java/tech/ugma/customcomponents/tributefx/SimpleMentionable.java
[tags on this repository]: https://github.com/TurekBot/TributeFX/tags