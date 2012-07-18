/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author E010925
 */
public class JavaFXApplication1 extends Application {

    public static void main(String[] args) {
        Application.launch(JavaFXApplication1.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("..\\fxml\\SampleBuilder.fxml"));

        stage.setScene(new Scene(root));
        stage.getScene().getStylesheets().add(getClass().getResource("..\\css\\SampleBuilder.css").toExternalForm());
        stage.show();
    }
}
