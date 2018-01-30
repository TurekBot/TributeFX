package tech.ugma.customcomponents.tributefx;

import com.sun.javafx.scene.web.skin.HTMLEditorSkin;
import javafx.application.Platform;
import javafx.scene.web.HTMLEditor;

public class TributeFX extends HTMLEditor {

    public TributeFX() {
        //Set up HTML
        this.setHtmlText(
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<body>\n" +
                        "    <script>document.write(\"1\");</script>" +
                        "    <!--Load the files of the @mention library (called Tribute)-->\n" +
                        "    <link rel=\"stylesheet\" href=\"tribute-js/tribute.css\" />\n" +
                        "    <script>document.write(\"2\");</script>" +
                        "    <script src=\"tribute-js/tribute.js\"></script>\n" +
                        "    <script>document.write(\"3\");</script>" +
                        "\n" +
                        // TODO: 1/28/2018 use the body instead of a certain div
                        "    <!--This is the container that we're going to attach the jtribute to.-->\n" +
                        "    <div contenteditable=\"true\" class=\"mentionable-container\">I'm alive!</div>\n" +
                        "    <script>document.write(\"4\");</script>" +
                        "    <!--And this is the stylesheet that goes along with the container.-->\n" +
                        "    <link rel=\"stylesheet\" href=\"container.css\" />\n" +
                        "    <script>document.write(\"5\");</script>" +
                        "\n" +
                        "    <!--This script builds the Tribute object and attaches it to the container-->\n" +
                        "    <script src=\"../../../startTribute.js\"> </script>\n" +
                        "    <script>document.write(\"6\");</script>" +
                        "    <script>var href = window.location.href; document.write(href);</script>" +

                        "</body>\n" +
                        "</html>"
        );

        HTMLEditorSkin htmlEditorSkin = (HTMLEditorSkin) this.getSkin();
        Platform.runLater(() -> {
            if (getScene() == null) {
                System.out.println("Couldn't apply listener to Scene!");
            } else {
                getScene().getWindow().setOnShown(shown -> {
                    System.out.println("Scene, shown.");
                });
            }
        });


    }

    public void setTributeConfiguration(String configuration) {

    }

    public String getTributeConfiguration() {
        return null;
    }
}
