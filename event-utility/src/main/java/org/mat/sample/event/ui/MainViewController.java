/*
 * @author e010925
 */
package org.mat.sample.event.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author E010925
 */
public class MainViewController extends AnchorPane implements Initializable {

    @FXML
    private Label label;

    @FXML
    private MenuItem closeMenu;

    @FXML
    private SplitPane splitPane;

    @FXML
    AnchorPane mainPane;

    @FXML
    private void handleClose(ActionEvent event) {
        System.out.println("Close the app!");
        System.exit(0);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("got it!");
    }

    @FXML
    private void doClickMe(ActionEvent event) throws IOException {
        System.out.println("#doClickMe");
        //EventPanelController panel = new EventPanelController();
        Parent panel = FXMLLoader.load(getClass().getResource("/fxml/EventPanel.fxml"));
        splitPane.getItems().remove(mainPane);
        splitPane.getItems().add(panel);


    }

    @FXML
    private void showAbout(ActionEvent event) throws IOException {
        // AboutController controller = new AboutController();

        Stage stageTheLabelBelongs = (Stage) splitPane.getScene().getWindow();

        final Stage dialog = new Stage(StageStyle.TRANSPARENT);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(stageTheLabelBelongs);

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/aboutPanel.fxml"));
        dialog.setScene(new Scene(root));


//
        // stageTheLabelBelongs.getScene().getRoot().setEffect(new BoxBlur());
        dialog.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initialize the app");
        closeMenu.setAccelerator(new KeyCharacterCombination("Q", KeyCombination.CONTROL_DOWN));
    }
}
