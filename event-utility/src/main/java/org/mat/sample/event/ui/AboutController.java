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
 * Created with IntelliJ IDEA.
 * User: mlecoutre
 * Date: 14/09/12
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 */
public class AboutController  extends VBox implements Initializable {

    @FXML
    Button okButton;


    @FXML
    private void handleOkButton(ActionEvent actionEvent){
        //this.getScene().
          System.out.println("ok!");
        Stage panelStage = (Stage) okButton.getScene().getWindow();

        panelStage.close();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
