/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mat.sample.event.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author E010925
 */
public class EventUIApp extends Application {

    public static void main(String[] args) {
        Application.launch(EventUIApp.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/css/MainView.css").toExternalForm());
        primaryStage.show();
//        final Stage dialog = new Stage(StageStyle.TRANSPARENT);
//        dialog.initModality(Modality.WINDOW_MODAL);
//        dialog.initOwner(primaryStage);
//        Parent aboutPanel = FXMLLoader.load(getClass().getResource("/fxml/aboutPanel.fxml"));
    }
}
