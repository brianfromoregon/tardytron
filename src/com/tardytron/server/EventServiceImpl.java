package com.tardytron.server;

import java.util.ArrayList;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tardytron.client.EventService;
import com.tardytron.client.dto.EventDto;
import com.tardytron.server.jdo.Event;
import com.tardytron.server.jdo.MyDao;
import org.joda.time.LocalDate;

@SuppressWarnings("serial")
public class EventServiceImpl extends RemoteServiceServlet implements EventService {

    MyDao dao = new MyDao();

    @Override
    public ArrayList<EventDto> getAllEvents(int tzOffsetMinutes) {
        User user = UserServiceFactory.getUserService().getCurrentUser();
        ArrayList<EventDto> ret = new ArrayList<EventDto>();
        for (Event event : dao.loadAndUpdateEvents(user, tzOffsetMinutes)) {
            ret.add(toDto(event));
        }
        return ret;
    }

    @Override
    public EventDto newEvent(int tzOffsetMinutes, int year, int month, int day, boolean twoDayReminder, boolean twoWeekReminder, String name) {
        User user = UserServiceFactory.getUserService().getCurrentUser();
        return toDto(dao.save(fromData(new LocalDate(year, month, day), twoDayReminder, twoWeekReminder, name, user), tzOffsetMinutes));
    }

    @Override
    public void deleteEvent(long id) {
        dao.delete(id);
    }

    private EventDto toDto(Event from) {
        EventDto to = new EventDto();
        to.setYear(from.getYear());
        to.setMonth(from.getMonth());
        to.setDay(from.getDay());
        to.setId(from.getId());
        to.setName(from.getName());
        to.setTwoDayReminder(from.hasTwoDayReminder());
        to.setTwoWeekReminder(from.hasTwoWeekReminder());
        to.setDaysLeft(from.daysLeft());
        return to;
    }

    private Event fromData(LocalDate date, boolean twoDayReminder, boolean twoWeekReminder, String name, User user) {
        Event to = new Event();
        to.setYear(date.getYear());
        to.setMonth(date.getMonthOfYear());
        to.setDay(date.getDayOfMonth());
        to.setName(name);
        to.setTwoDayReminder(twoDayReminder);
        to.setTwoWeekReminder(twoWeekReminder);
        to.setUser(user);
        return to;
    }
}
