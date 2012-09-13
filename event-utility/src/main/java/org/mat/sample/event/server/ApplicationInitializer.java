package org.mat.sample.event.server;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;

public class ApplicationInitializer {
 @Inject
 ApplicationInitializer(PersistService service) {
  service.start(); 
  // At this point JPA is started and ready.
 
  // other application initializations if necessary
 }
}