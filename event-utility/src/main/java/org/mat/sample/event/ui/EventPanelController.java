package org.mat.sample.event.ui;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.mat.sample.event.model.Event;
import org.mat.sample.event.services.EventService;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * User: mlecoutre
 * Date: 14/09/12
 * Time: 17:24
 */
public class EventPanelController extends AnchorPane implements Initializable {

    @Inject
    EventService eventService;

    @FXML
    TextField nbEvents;

    @FXML
    ProgressBar progressBar;

    @FXML
    void generateEvents(ActionEvent ae) {

        Integer eventsNumber = new Integer(nbEvents.getText());

        for (int i = 0; i < eventsNumber; i++) {
            System.out.println("send event "+i);
            Event e = new Event("Event " + i, new Date());
            eventService.create(e);
            Float f = (1f / eventsNumber) * (i+1);
            progressBar.setProgress(f);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialize Event Panel");

    }
}
