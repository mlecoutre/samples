package org.mat.sample;

import com.google.inject.AbstractModule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mat.sample.event.services.EventService;

import javax.inject.Inject;

import static org.junit.Assert.assertTrue;

/**
 * User: mlecoutre
 * Date: 23/07/12
 * Time: 14:23
 */
@RunWith(GuiceTestRunner.class)
@GuiceTestRunner.GuiceModules({EventServiceModule.class})
public class EventServiceTest {

    @Inject
  EventService eventService;




    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
      //  emf = Persistence.createEntityManagerFactory("sample");
      //  em = emf.createEntityManager();
      //  Injector injector = Guice.createInjector(new JpaPersistModule("default"), new EventModule());

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {

    }

    @Test
    public void testJPAModule(){
         assertTrue("EventService should not be null", eventService != null);

    }



}
