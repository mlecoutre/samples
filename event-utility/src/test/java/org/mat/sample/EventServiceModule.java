package org.mat.sample;

import com.google.inject.AbstractModule;
import org.mat.sample.event.services.EventService;

public class EventServiceModule extends AbstractModule {
  @Override 
  protected void configure() {

     /*
      * This tells Guice that whenever it sees a dependency on a TransactionLog,
      * it should satisfy the dependency using a DatabaseTransactionLog.
      */
    bind(EventService.class).toInstance(new EventService());


  }
}