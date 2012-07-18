/*
 * @author e010925
 */
package javafxapplication1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author E010925
 */
public class Sample implements Initializable {

    @FXML
    private Label label;

    @FXML
    private MenuItem closeMenu;


    @FXML
    private void handleClose(ActionEvent event) {
        System.out.println("Close the app!");
        System.exit(0);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @FXML
    private void doClickMe(ActionEvent event) {
        System.out.println("#doClickMe");


    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initialize the app");
        closeMenu.setAccelerator(new KeyCharacterCombination("Q", KeyCombination.CONTROL_DOWN));
    }
}
