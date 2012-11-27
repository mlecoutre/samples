package org.mat.sample.event.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
  * User: mlecoutre
 * Date: 14/09/12
 * Time: 16:16
 */
public class AboutController  extends VBox implements Initializable {

    @FXML
    Button okButton;


    @FXML
    private void handleOkButton(ActionEvent actionEvent){
        Stage panelStage = (Stage) okButton.getScene().getWindow();
        panelStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
