package org.mat.sample.event.ui;

import com.google.inject.AbstractModule;
import org.mat.sample.event.services.EventService;

/**
 *
 * @author Richard
 */
public class MyModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EventService.class);
    }
}