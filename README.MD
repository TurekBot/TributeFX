# TributeFX

An <span title="meaning until a better, native, one is made for JavaFX"><small><small>in-the-mean-time</small></small></span> @mention library for JavaFX. A JavaFX wrapper for [Tribute.js][Tribute]

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

### Customization
Once you've cut your teeth on the basics, you can whip out some JavaScript and [get custom with it]:

    URL customConfig = DemoWithCustomConfig.class.getResource("customTributeConfiguration.js");

    TributeFX.tributifyWebView(webView, customConfig);

    WebEngine webEngine = webView.getEngine();
    ArrayList<TributeFX.SimpleMentionable> mentionables = TributeFX.createSampleMentionableList();
    
    TributeFX.addMentionables(mentionables, webEngine);
    
See [DemoWithCustomConfig] to really get jiggy with it.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Zurb** - *JavaScript @mention Engine* - [Tribute]
* **Bradley Turek** - *Initial Wrapping of Tribute*

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

[Tribute]: https://github.com/zurb/tribute
[Hunger Games Demo]: https://i.imgur.com/yLadZRo.gif
[DemoWithDefaults]: src/main/java/tech/ugma/customcomponents/tributefx/demo/DemoWithDefaults.java

[Tribute]: https://github.com/zurb/tribute
[Hunger Games Demo]: https://i.imgur.com/yLadZRo.gif
[DemoWithDefaults]: src/main/java/tech/ugma/customcomponents/tributefx/demo/DemoWithDefaults.java
[get custom with it]: https://github.com/zurb/tribute#a-collection "See Tribute's website for all the configuration options."
[DemoWithCustomConfig]: src/main/java/tech/ugma/customcomponents/tributefx/demo/DemoWithCustomConfig.java