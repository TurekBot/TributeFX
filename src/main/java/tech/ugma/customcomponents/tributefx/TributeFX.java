package tech.ugma.customcomponents.tributefx;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;

public class TributeFX {

    private static String defaultHtml =
            "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "  <head>\n" +
                    "  <base/>" +
                    "  </head>\n" +
                    "  <!-- We set the body's contenteditable to false so that the user\n" +
                    "       doesn't delete any of the configuration that gets put in the body.-->\n" +
                    "  <body contenteditable=\"false\">\n" +
                    "    <!-- This is the only thing the user can edit -->\n" +
                    "    <div contenteditable=\"true\" id=\"container\"></div>\n" +
                    "\n" +
                    "    <link rel=\"stylesheet\" href=\"tribute-js/tribute.css\" />\n" +
                    "    <script src=\"tribute-js/tribute.js\"></script>\n" +
                    "\n" +
                    "    <link rel=\"stylesheet\" href=\"container.css\" />\n" +
                    "    <script src=\"configureTribute.js\"></script>\n" +
                    "    <script src=\"configureContainer.js\"></script>\n" +
                    "\n" +
                    "\n" +
                    "  </body>\n" +
                    "</html>";

    private String defaultTributeConfiguration = "";


    public void setTributeConfiguration(String configuration) {

    }

    public String getTributeConfiguration() {
        return null;
    }

    public static void configureWebView(WebView toConfigure) {
        WebEngine webEngine = toConfigure.getEngine();

        URL url = TributeFX.class.getResource("configureTribute.js");

        String baseUrl = url.toExternalForm().substring(0, url.toExternalForm().lastIndexOf("/") + 1);

        System.out.println(url.toExternalForm());
        System.out.println(baseUrl);

        defaultHtml = defaultHtml.replace("<base/>", "<base href=\"" + baseUrl + "\"/>");

        webEngine.loadContent(defaultHtml);

    }
}
