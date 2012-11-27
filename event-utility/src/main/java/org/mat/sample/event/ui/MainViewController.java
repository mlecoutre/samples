/*
 * @author e010925
 */
package org.mat.sample.event.ui;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
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
    /**
     * Create the Guice Injector, which is going to be used to supply
     * the Person model.
     */
    private final Injector injector = Guice.createInjector(new MyModule(), new JpaPersistModule("default"));
    private Parent currentPanel = null;

    @FXML
    private Label label;

    @FXML
    private MenuItem closeMenu;

    @FXML
    private SplitPane splitPane;

    @FXML
    Parent mainPane;

    @FXML
    private void handleClose(ActionEvent event) {
        System.out.println("Close the app!");
        System.exit(0);
    }

    @FXML
    private void showMainPanel(ActionEvent event) {
        if (currentPanel != null && currentPanel.equals(mainPane)) {
            System.out.println("Main pane already selected");
        } else if (currentPanel != null) {
            splitPane.getItems().remove(currentPanel);
            splitPane.getItems().add(mainPane);
            currentPanel = mainPane;
            splitPane.setDividerPositions(0.25f);
        } else {
            System.out.println("Current panel not defined");
        }
    }

    @FXML
    private void showEventPanel(ActionEvent event) throws IOException {
        GuiceFXMLLoader loader = new GuiceFXMLLoader(injector);
        currentPanel = (Parent) loader.load(getClass().getResource("/fxml/EventPanel.fxml"), EventPanelController.class);
        splitPane.getItems().remove(mainPane);
        splitPane.getItems().add(currentPanel);
        splitPane.setDividerPositions(0.25f);
    }

    @FXML
    private void showAbout(ActionEvent event) throws IOException {

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
        injector.getInstance(ApplicationInitializer.class);
        closeMenu.setAccelerator(new KeyCharacterCombination("Q", KeyCombination.CONTROL_DOWN));
    }

    static class ApplicationInitializer {
        @Inject
        ApplicationInitializer(PersistService service) {
            service.start();
        }
    }
}
