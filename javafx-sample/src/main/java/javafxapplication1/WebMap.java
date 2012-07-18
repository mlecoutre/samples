package javafxapplication1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Jasper Potts
 *         This file is taken from an example project on the fxexperience.com website.
 *         http://fxexperience.com/2011/05/maps-in-javafx-2-0/
 *         <p/>
 *         I slightly modified it to compile it in JavaFX 2.0.2 for a French article on JavaFx and Maven
 *         on my blog http://notaboekje.blogspot.com
 */
public class WebMap extends Application {

    private Timeline locationUpdateTimeline;

    static { // use system proxy settings when standalone application
        // System.setProperty("java.net.useSystemProxies", "true");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // create web engine and view
        final WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("../html/googlemap.html").toExternalForm());
        // create map type buttons
        final ToggleGroup mapTypeGroup = new ToggleGroup();
        final ToggleButton road = new ToggleButton("Road");
        road.setSelected(true);
        road.setToggleGroup(mapTypeGroup);
        final ToggleButton satellite = new ToggleButton("Satellite");
        satellite.setToggleGroup(mapTypeGroup);
        final ToggleButton hybrid = new ToggleButton("Hybrid");
        hybrid.setToggleGroup(mapTypeGroup);
        final ToggleButton terrain = new ToggleButton("Terrain");
        terrain.setToggleGroup(mapTypeGroup);
        mapTypeGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle toggle1) {
                if (road.isSelected()) {
                    webEngine.executeScript("document.setMapTypeRoad()");
                } else if (satellite.isSelected()) {
                    webEngine.executeScript("document.setMapTypeSatellite()");
                } else if (hybrid.isSelected()) {
                    webEngine.executeScript("document.setMapTypeHybrid()");
                } else if (terrain.isSelected()) {
                    webEngine.executeScript("document.setMapTypeTerrain()");
                }
            }
        });

        // add map source toggles
        ToggleGroup mapSourceGroup = new ToggleGroup();
        final ToggleButton google = new ToggleButton("Google");
        google.setSelected(true);
        google.setToggleGroup(mapSourceGroup);
        final ToggleButton yahoo = new ToggleButton("Yahoo");
        yahoo.setToggleGroup(mapSourceGroup);
        final ToggleButton bing = new ToggleButton("Bing");
        bing.setToggleGroup(mapSourceGroup);
        // listen to selected source
        mapSourceGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle toggle1) {
                terrain.setDisable(true);
                if (google.isSelected()) {
                    terrain.setDisable(false);
                    webEngine.load(getClass().getResource("../html/googlemap.html").toString());
                } else if (yahoo.isSelected()) {
                    webEngine.load(getClass().getResource("../html/yahoomap.html").toString());
                } else if (bing.isSelected()) {
                    webEngine.load(getClass().getResource("../html/bingmap.html").toString());
                }
                mapTypeGroup.selectToggle(road);
            }
        });

        // add search
        final TextField searchBox = new TextField("");
        searchBox.setPrefColumnCount(12);
        searchBox.setPromptText("Search");
        searchBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
                // delay location updates to we don't go too fast file typing
                if (locationUpdateTimeline != null) {
                    locationUpdateTimeline.stop();
                }
                locationUpdateTimeline = new Timeline();
                locationUpdateTimeline.getKeyFrames().add(
                        new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent actionEvent) {
                                webEngine.executeScript("document.goToLocation(\"" + searchBox.getText() + "\")");
                            }
                        }));
                locationUpdateTimeline.play();
            }
        });

        Button zoomIn = new Button("Zoom In");
        zoomIn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent actionEvent) {
                webEngine.executeScript("document.zoomIn()");
            }
        });
        Button zoomOut = new Button("Zoom Out");
        zoomOut.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent actionEvent) {
                webEngine.executeScript("document.zoomOut()");
            }
        });
        // create toolbar
        ToolBar toolBar = new ToolBar();
        toolBar.getStyleClass().add("map-toolbar");
        toolBar.getItems().addAll(
                road, satellite, hybrid, terrain,
                createSpacer(),
                google, yahoo, bing,
                createSpacer(),
                new Label("Location:"), searchBox, zoomIn, zoomOut);
        // create root
        BorderPane root = new BorderPane();
        root.getStyleClass().add("map");
        root.setCenter(webView);
        root.setTop(toolBar);
        // create scene
        stage.setTitle("Web Map");
        Scene scene = new Scene(root, 1000, 700, Color.web("#666970"));
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("../css/WebMap.css").toExternalForm());
        // show stage
        stage.show();
    }

    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
}