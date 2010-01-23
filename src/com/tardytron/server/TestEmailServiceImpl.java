package com.tardytron.server;

import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tardytron.client.TestEmailService;
import com.tardytron.server.jdo.Event;

@SuppressWarnings("serial")
public class TestEmailServiceImpl extends RemoteServiceServlet implements
        TestEmailService {

    ReminderService reminderService = new ReminderService();

    @Override
    public void sendTestEmail() {
        Event testEvent = new Event();
        testEvent.setYear(1966);
        testEvent.setMonth(6);
        testEvent.setDay(16);
        testEvent.setName("Jan Železný's birthday");
        testEvent.setUser(UserServiceFactory.getUserService().getCurrentUser());
        reminderService.remind(testEvent);
    }
}
