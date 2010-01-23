package com.tardytron.server.jdo;

import java.util.Collection;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.joda.time.DateTime;

import com.google.appengine.api.users.User;
import com.google.common.collect.Lists;

public class MyDao {

    @SuppressWarnings("unchecked")
    public Collection<Event> loadAndUpdateEvents(User user, int tzOffsetMinutes) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            Query query = pm.newQuery(Event.class, "user == userParam");
            query.declareImports("import com.google.appengine.api.users.User");
            query.declareParameters("User userParam");
            Collection<Event> events = (Collection<Event>) query.execute(user);
            for (Event event : events) {
                event.updateTzOffset(tzOffsetMinutes);
            }

            return pm.detachCopyAll(events);
        } finally {
            pm.close();
        }
    }

    @SuppressWarnings("unchecked")
    public Collection<Event> loadEventsForAnniversary(DateTime date) {
    	date = date.withYear(2000).hourOfDay().roundFloorCopy();
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            String q = "year2000UtcDateTime >= date && year2000UtcDateTime < hourPlus";
            String p = "Date date, Date hourPlus";
            String i = "import java.util.Date;";
            Collection<Event> c = Lists.newArrayList();
            Query query = pm.newQuery(Event.class, q);
            query.declareParameters(p);
            query.declareImports(i);
            c.addAll(pm.detachCopyAll((Collection<Event>) query.execute(date.toDate(), date.plusHours(1).toDate())));
            query = pm.newQuery(Event.class, q + " && twoDayReminder == true");
            query.declareParameters(p);
            query.declareImports(i);
            DateTime twoDayDate = date.plusDays(2);
            c.addAll(pm.detachCopyAll((Collection<Event>) query.execute(twoDayDate.toDate(), twoDayDate.plusHours(1).toDate())));
            query = pm.newQuery(Event.class, q + " && twoWeekReminder == true");
            query.declareParameters(p);
            query.declareImports(i);
            DateTime twoWeekDate = date.plusWeeks(2);
            c.addAll(pm.detachCopyAll((Collection<Event>) query.execute(twoWeekDate.toDate(), twoWeekDate.plusHours(1).toDate())));
            return c;
        } finally {
            pm.close();
        }
    }

    public Event save(Event newEvent, int tzOffsetMinutes) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            newEvent.updateTzOffset(tzOffsetMinutes);
            return pm.makePersistent(newEvent);
        } finally {
            pm.close();
        }
    }

    public void delete(long id) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            Event event = pm.getObjectById(Event.class, id);
            if (event != null) {
                pm.deletePersistent(event);
            }
        } finally {
            pm.close();
        }
    }
}
