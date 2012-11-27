package org.mat.sample.event.ui;

public class MyInitializer {
        @Inject MyInitializer(PersistService service) {
                service.start(); 
 
                // At this point JPA is started and ready.
        } 
}