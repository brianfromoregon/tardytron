package com.tardytron.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.tardytron.server.jdo.Event;
import com.tardytron.server.jdo.MyDao;
import com.tardytron.server.mail.PostalService;

@SuppressWarnings("serial")
public class ReminderService extends HttpServlet {

    MyDao dao = new MyDao();
    PostalService postalService = new PostalService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        DateTime date = new DateTime(DateTimeZone.UTC);
        resp.getWriter().append(date + "\n");
        for (Event e : dao.loadEventsForAnniversary(date)) {
            remind(e);
            resp.getWriter().append(String.format("Sent reminder to %s for event with date %s\n", e.getUser().getEmail(), e.getDateTime()));
        }
    }

    public void remind(Event event) {
        String fromEmail = "brianfromoregon@gmail.com";
        String fromName = "Tardytron";
        String toEmail = event.getUser().getEmail();
        String toName = event.getUser().getNickname();
        int daysLeft = event.daysLeft();
        String subject = "Event Reminder";
        String msgBody = String.format("Reminder, ");
        if (daysLeft == 0) {
            msgBody += "today is";
        } else if (daysLeft == 1) {
            msgBody += "tomorrow is";
        } else {
            msgBody += "" + daysLeft + " days until";
        }
        int anniversaryNum = event.nextAnniversaryAge();
        if (anniversaryNum > 0) {
            msgBody += " the " + toString(anniversaryNum) + " anniversary of";
        }
        msgBody += " the following event: \"" + event.getName() + "\"";
        msgBody += "\n\nVisit http://tardytron.appspot.com to manage your events.";
        postalService.sendMail(fromEmail, fromName, toEmail, toName, subject,
                msgBody);
    }

    String toString(int i) {
        switch (i % 10) {
            case 1:
                return i + "st";
            case 2:
                return i + "nd";
            case 3:
                return i + "rd";
            default:
                return i + "th";
        }
    }
}
