package org.mat.sample.event.services;

import org.mat.sample.event.model.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.google.inject.Inject;

import javax.persistence.EntityManager;

/**
 * User: mlecoutre
 * Date: 19/07/12
 * Time: 19:43
 */
public class EventService {

    @Inject
    EntityManager em;


    public List<Event> list() {
        List<Event> Events = new ArrayList<Event>();
        Event Event = new Event("mtitle1", new Date());
        Events.add(Event);
        Event = new Event("mtitle2", new Date());
        Events.add(Event);
        // Create the client resource
        return Events;
    }

    public boolean create(Event event) {
        em.persist(event);
        System.out.println("Create event");

        return true;
    }


}
