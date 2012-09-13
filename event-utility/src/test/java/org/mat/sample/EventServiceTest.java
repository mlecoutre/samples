package org.mat.sample;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mat.sample.event.services.EventService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * User: mlecoutre
 * Date: 23/07/12
 * Time: 14:23
 */
@RunWith(GuiceTestRunner.class)
public class EventServiceTest {

    private static EntityManager em;

    private static EntityManagerFactory emf;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        emf = Persistence.createEntityManagerFactory("sample");
        em = emf.createEntityManager();
        Injector injector = Guice.createInjector(new JpaPersistModule("default"), new EventModule());

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        em.close();
        emf.close();
    }

    static class EventModule extends AbstractModule {
        protected void configure() {
            bind(EventService.class).toInstance(new EventService());
        }
    }

}
