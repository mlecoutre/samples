package org.mat.sample.event.ui;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Uses Guice to inject model state. Basically you create an instance of
 * GuiceFXMLLoader supplying an Injector, and then call load. The load
 * method takes the FXML file to load, and the controller to create and
 * associate with the FXML file.
 *
 * @see @link http://fxexperience.com/2011/10/fxml-guice/
 */
public class GuiceFXMLLoader {
    private final Injector injector;

    @Inject
    public GuiceFXMLLoader(Injector injector) {
        this.injector = injector;
    }


    // Load some FXML file, using the supplied Controller, and return the
    // instance of the initialized controller...?
    public Object load(URL url, Class<?> controller) {
        Object instance = injector.getInstance(controller);
        FXMLLoader loader = new FXMLLoader();
      //  loader.getNamespace().put("controller", instance);
        loader.setController(instance);

        InputStream in = null;
        try {
            loader.setLocation(getClass().getResource("/"));
                in = url.openStream();
            return loader.load(in);
        } catch (Exception e) {
            System.err.println("ERROR GuiceFXMLLoader!");
            e.printStackTrace();
        } finally {
            if (in != null) try {
                in.close();
            } catch (Exception ee) {
            }
        }
        return null;
    }
}