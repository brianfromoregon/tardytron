package com.tardytron.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.tardytron.client.dto.EventDto;

public class CurrentEventsTableLoader {

    private EventServiceAsync eventService;
    private IconImageBundle icons;

    public CurrentEventsTableLoader(EventServiceAsync eventService, IconImageBundle icons) {
        this.eventService = eventService;
        this.icons = icons;
    }

    public void load(final AsyncCallback<Grid> loadCallback,
            final AsyncCallback<Void> deleteCallback) {
        eventService.getAllEvents(new Date().getTimezoneOffset(), new AsyncCallback<ArrayList<EventDto>>() {

            @Override
            public void onFailure(Throwable caught) {
                loadCallback.onFailure(caught);
            }

            @Override
            public void onSuccess(ArrayList<EventDto> result) {
                Collections.sort(result, new Comparator<EventDto>() {

                    @Override
                    public int compare(EventDto o1, EventDto o2) {
                        return Integer.valueOf(o1.getDaysLeft()).compareTo(
                                o2.getDaysLeft());
                    }
                });
                Grid table = new Grid(result.size() + 1, 5);
                for (int i = 0; i < 5; i++) {
                    table.getCellFormatter().addStyleName(0, i, "tableHeader");
                }
                int row = 1;
                table.setText(0, 0, "");
                table.setText(0, 1, "Days Left");
                table.setText(0, 2, "Name");
                table.setText(0, 3, "Date");
                table.setText(0, 4, "Reminders");
                for (final EventDto event : result) {
                    Image i = new Image();
                    icons.redX().applyTo(i);
                    PushButton deleteButton = new PushButton(i);
                    deleteButton.addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent clickEvent) {
                            eventService.deleteEvent(event.getId(), deleteCallback);
                        }
                    });
                    table.setWidget(row, 0, deleteButton);
                    table.setText(row, 1, "" + event.getDaysLeft());
                    table.setText(row, 2, event.getName());
                    table.setText(row, 3, Tardytron.DATE_FMT.format(Tardytron.DATE_FMT.parse(event.getMonth() + "/" + event.getDay() + "/" + event.getYear())));
                    String reminders = event.hasTwoWeekReminder() ? "2-week"
                            : "";
                    reminders += event.hasTwoDayReminder() ? ((reminders.length() > 0 ? ", " : "") + "2-day") : "";
                    table.setText(row, 4, reminders);
                    row++;
                }
                loadCallback.onSuccess(table);
            }
        });
    }
}
