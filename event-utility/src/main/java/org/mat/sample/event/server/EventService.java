package org.mat.sample.event.server;

import org.mat.sample.event.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: mlecoutre
 * Date: 19/07/12
 * Time: 19:43
 */
public class EventService {

    public List<Event> list() {
        List<Event> Events = new ArrayList<Event>();
        Event Event = new Event("mtitle1", new Date());
        Events.add(Event);
        Event = new Event("mtitle2", new Date());
        Events.add(Event);
        // Create the client resource
        return Events;
    }

    public boolean create(Event event){


    }


}
